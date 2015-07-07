package jason.app.weixin.security.service.impl;


import jason.app.weixin.security.entity.AclClassImpl;
import jason.app.weixin.security.entity.AclEntryImpl;
import jason.app.weixin.security.entity.AclObjectIdentityImpl;
import jason.app.weixin.security.entity.AclSidImpl;
import jason.app.weixin.security.service.IAclDao;
import jason.app.weixin.security.translator.AclObjectIdentityTranslator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.AccessControlEntryImpl;
import org.springframework.security.acls.domain.AclImpl;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.AlreadyExistsException;
import org.springframework.security.acls.model.ChildrenExistException;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service("aclService")
public class MutableAclServiceImpl implements MutableAclService {
    private boolean foreignKeysInDatabase = true;
    
    
    @Autowired
    private LookupStrategy lookupStrategy;
    
    public LookupStrategy getLookupStrategy() {
        return lookupStrategy;
    }

    public void setLookupStrategy(LookupStrategy lookupStrategy) {
        this.lookupStrategy = lookupStrategy;
    }

    public AclCache getAclCache() {
        return aclCache;
    }

    public void setAclCache(AclCache aclCache) {
        this.aclCache = aclCache;
    }


    @Autowired
    private AclCache aclCache;
    
    @Autowired
    private IAclDao aclDao;

    @Override
    public List<ObjectIdentity> findChildren(ObjectIdentity parentIdentity) {
        
        List<AclObjectIdentityImpl> objects = aclDao.findChildren(parentIdentity.getIdentifier(), parentIdentity.getType());

        if (objects.size() == 0) {
            return null;
        }else {
        
        	return AclObjectIdentityTranslator.toDTO(objects);
        }
    }

    @Override
    public Acl readAclById(ObjectIdentity object) throws NotFoundException {
        return readAclById(object, null);
    }

    @Override
    public Acl readAclById(ObjectIdentity object, List<Sid> sids) throws NotFoundException {
        Map<ObjectIdentity, Acl> map = readAclsById(Arrays.asList(object), sids);
        Assert.isTrue(map.containsKey(object), "There should have been an Acl entry for ObjectIdentity " + object);

        return (Acl) map.get(object);
    }

    @Override
    public Map<ObjectIdentity, Acl> readAclsById(List<ObjectIdentity> objects) throws NotFoundException {
        return readAclsById(objects, null);
    }

    @Override
    public Map<ObjectIdentity, Acl> readAclsById(List<ObjectIdentity> objects, List<Sid> sids) throws NotFoundException {
        Map<ObjectIdentity, Acl> result = lookupStrategy.readAclsById(objects, sids);

        // Check every requested object identity was found (throw NotFoundException if needed)
        for (ObjectIdentity oid : objects) {
            if (!result.containsKey(oid)) {
                throw new NotFoundException("Unable to find ACL information for object identity '" + oid + "'");
            }
        }

        return result;
    }

    @Override
    @Transactional
    public MutableAcl createAcl(ObjectIdentity objectIdentity) throws AlreadyExistsException {
        Assert.notNull(objectIdentity, "Object Identity required");

        // Check this object identity hasn't already been persisted
        if (retrieveObjectIdentityPrimaryKey(objectIdentity) != null) {
            throw new AlreadyExistsException("Object identity '" + objectIdentity + "' already exists");
        }

        // Need to retrieve the current principal, in order to know who "owns" this ACL (can be changed later on)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalSid sid = new PrincipalSid(auth);

        // Create the acl_object_identity row
        createObjectIdentity(objectIdentity, sid);

        // Retrieve the ACL via superclass (ensures cache registration, proper retrieval etc)
        
        Acl acl = readAclById(objectIdentity);
        Assert.isInstanceOf(MutableAcl.class, acl, "MutableAcl should be been returned");
       
        return (MutableAcl) acl;

    }
    
    /**
     * Retrieves the primary key from the acl_object_identity table for the passed ObjectIdentity. Unlike some
     * other methods in this implementation, this method will NOT create a row (use {@link
     * #createObjectIdentity(ObjectIdentity, Sid)} instead).
     *
     * @param oid to find
     *
     * @return the object identity or null if not found
     */
    protected AclObjectIdentityImpl retrieveObjectIdentityPrimaryKey(ObjectIdentity oid) {
        try {
            return aclDao.getObjectIdentity(oid.getType(), oid.getIdentifier());
        } catch (NoResultException notFound) {
            return null;
        }
    }

    /**
     * Creates an entry in the acl_object_identity table for the passed ObjectIdentity. The Sid is also
     * necessary, as acl_object_identity has defined the sid column as non-null.
     *
     * @param object to represent an acl_object_identity for
     * @param owner for the SID column (will be created if there is no acl_sid entry for this particular Sid already)
     * @return 
     */
    protected void createObjectIdentity(ObjectIdentity object, Sid owner) {
        AclSidImpl sid = createOrRetrieveSidPrimaryKey(owner, true);
        AclClassImpl clazz = createOrRetrieveClassPrimaryKey(object.getType(), true);
        AclObjectIdentityImpl identity = new AclObjectIdentityImpl();
        identity.setObjIdClass(clazz);
        identity.setObjIdIdentity((Long) object.getIdentifier());
        identity.setOwner(sid);
        identity.setEntriesInheriting(Boolean.TRUE);
        aclDao.createObjectIdentity(identity);
        
    }
    
    /**
     * Retrieves the primary key from acl_sid, creating a new row if needed and the allowCreate property is
     * true.
     *
     * @param sid to find or create
     * @param allowCreate true if creation is permitted if not found
     *
     * @return the primary key or null if not found
     *
     * @throws IllegalArgumentException if the <tt>Sid</tt> is not a recognized implementation.
     */
    protected AclSidImpl createOrRetrieveSidPrimaryKey(Sid sid, boolean allowCreate) {
        Assert.notNull(sid, "Sid required");

        String sidName;
        boolean sidIsPrincipal = true;

        if (sid instanceof PrincipalSid) {
            sidName = ((PrincipalSid) sid).getPrincipal();
        } else if (sid instanceof GrantedAuthoritySid) {
            sidName = ((GrantedAuthoritySid) sid).getGrantedAuthority();
            sidIsPrincipal = false;
        } else {
            throw new IllegalArgumentException("Unsupported implementation of Sid");
        }

        List<AclSidImpl> sidIds = aclDao.findAclSidList(Boolean.valueOf(sidIsPrincipal), sidName);

        if (!sidIds.isEmpty()) {
            return sidIds.get(0);
        }

        if (allowCreate) {
            AclSidImpl sid2 = new AclSidImpl();
            sid2.setSid(sidName);
            sid2.setPrincipal(Boolean.valueOf(sidIsPrincipal));
            return aclDao.createAclSid(sid2);
        }

        return null;
    }
    
    /**
     * Retrieves the primary key from {@code acl_class}, creating a new row if needed and the
     * {@code allowCreate} property is {@code true}.
     *
     * @param type to find or create an entry for (often the fully-qualified class name)
     * @param allowCreate true if creation is permitted if not found
     *
     * @return the primary key or null if not found
     */
    protected AclClassImpl createOrRetrieveClassPrimaryKey(String type, boolean allowCreate) {
        List<AclClassImpl> classIds = aclDao.findAclClassList(type);

        if (!classIds.isEmpty()) {
            return classIds.get(0);
        }

        if (allowCreate) {
            AclClassImpl clazz = new AclClassImpl();
            clazz.setClazz(type);
            
//            Assert.isTrue(TransactionSynchronizationManager.isSynchronizationActive(),
//                    "Transaction must be running");
            return aclDao.createAclClass(clazz);
        }

        return null;
    }
    
    
    @Override
    @Transactional
    public void deleteAcl(ObjectIdentity objectIdentity, boolean deleteChildren) throws ChildrenExistException {
        Assert.notNull(objectIdentity, "Object Identity required");
        Assert.notNull(objectIdentity.getIdentifier(), "Object Identity doesn't provide an identifier");

        if (deleteChildren) {
            List<ObjectIdentity> children = findChildren(objectIdentity);
            if (children != null) {
                for (ObjectIdentity child : children) {
                    deleteAcl(child, true);
                }
            }
        } else {
            if (!foreignKeysInDatabase) {
                // We need to perform a manual verification for what a FK would normally do
                // We generally don't do this, in the interests of deadlock management
                List<ObjectIdentity> children = findChildren(objectIdentity);
                if (children != null) {
                    throw new ChildrenExistException("Cannot delete '" + objectIdentity + "' (has " + children.size()
                            + " children)");
                }
            }
        }

        AclObjectIdentityImpl oidPrimaryKey = retrieveObjectIdentityPrimaryKey(objectIdentity);

        // Delete this ACL's ACEs in the acl_entry table
        aclDao.deleteEntries(oidPrimaryKey);

        // Delete this ACL's acl_object_identity row
        aclDao.deleteObjectIdentity(oidPrimaryKey);

        // Clear the cache
        aclCache.evictFromCache(objectIdentity);
    }

    @Override
    /**
     * This implementation will simply delete all ACEs in the database and recreate them on each invocation of
     * this method. A more comprehensive implementation might use dirty state checking, or more likely use ORM
     * capabilities for create, update and delete operations of {@link MutableAcl}.
     */
    @Transactional
    public MutableAcl updateAcl(MutableAcl acl) throws NotFoundException {
        Assert.notNull(acl.getId(), "Object Identity doesn't provide an identifier");

        // Delete this ACL's ACEs in the acl_entry table
        aclDao.deleteEntries(retrieveObjectIdentityPrimaryKey(acl.getObjectIdentity()));

        // Create this ACL's ACEs in the acl_entry table
        createEntries(acl);

        // Change the mutable columns in acl_object_identity
        updateObjectIdentity(acl);

        // Clear the cache, including children
        clearCacheIncludingChildren(acl.getObjectIdentity());

        // Retrieve the ACL via superclass (ensures cache registration, proper retrieval etc)
        return (MutableAcl) readAclById(acl.getObjectIdentity());
    }
    
    
    /**
     * Creates a new row in acl_entry for every ACE defined in the passed MutableAcl object.
     *
     * @param acl containing the ACEs to insert
     */
    protected void createEntries(final MutableAcl acl) {
        if(acl.getEntries().isEmpty()) {
            return;
        }
        AclImpl aclImpl = (AclImpl)acl;
        ObjectIdentityImpl objIdentity = (ObjectIdentityImpl) aclImpl.getObjectIdentity();
        List<AclEntryImpl> entries = new ArrayList<AclEntryImpl>();
        for(int i=0;i<acl.getEntries().size();i++) {
            AccessControlEntryImpl entry = (AccessControlEntryImpl) acl.getEntries().get(i);
            AclEntryImpl aclEntry = new AclEntryImpl();
            aclEntry.setAclObjectIdentity(aclDao.getObjectIdentity(objIdentity.getType(), objIdentity.getIdentifier()));
            aclEntry.setAceOrder(i);
            PrincipalSid sid = (PrincipalSid) entry.getSid();
            AclSidImpl aclSid = aclDao.findAclSid(sid.getPrincipal());
            if(aclSid==null) {
                AclSidImpl aclSid2 = new AclSidImpl();
                aclSid2.setSid(sid.getPrincipal());
                aclSid2.setPrincipal(true);
                aclSid= aclDao.createAclSid(aclSid2);
            }
            aclEntry.setSid(aclSid);
            aclEntry.setMask(entry.getPermission().getMask());
            aclEntry.setGranting(entry.isGranting());
            aclEntry.setAuditSuccess(entry.isAuditSuccess());
            aclEntry.setAuditFailure(entry.isAuditFailure());
            entries.add(aclEntry);
        }
        aclDao.createEntries(entries);
      
    }

    
    /**
     * Updates an existing acl_object_identity row, with new information presented in the passed MutableAcl
     * object. Also will create an acl_sid entry if needed for the Sid that owns the MutableAcl.
     *
     * @param acl to modify (a row must already exist in acl_object_identity)
     *
     * @throws NotFoundException if the ACL could not be found to update.
     */
    protected void updateObjectIdentity(MutableAcl acl) {
        AclObjectIdentityImpl parentId = null;

        if (acl.getParentAcl() != null) {
            Assert.isInstanceOf(ObjectIdentityImpl.class, acl.getParentAcl().getObjectIdentity(),
                "Implementation only supports ObjectIdentityImpl");

            AclObjectIdentityImpl oii = (AclObjectIdentityImpl) acl.getParentAcl().getObjectIdentity();
            parentId = retrieveObjectIdentityPrimaryKey(oii);
        }

        Assert.notNull(acl.getOwner(), "Owner is required in this implementation");

        AclSidImpl ownerSid = createOrRetrieveSidPrimaryKey(acl.getOwner(), true);
//        aclObject.setParentObject(parentId);
//        aclObject.setOwner(ownerSid);
//        aclObject.setEntriesInheriting(Boolean.valueOf(acl.isEntriesInheriting()));
//        
//        boolean update = aclDao.updateObjectIdentity(aclObject);

//        if (!update) {
//            throw new NotFoundException("Unable to locate ACL to update");
//        }
    }
    
    
    private void clearCacheIncludingChildren(ObjectIdentity objectIdentity) {
        Assert.notNull(objectIdentity, "ObjectIdentity required");
        List<ObjectIdentity> children = findChildren(objectIdentity);
        if (children != null) {
            for (ObjectIdentity child : children) {
                clearCacheIncludingChildren(child);
            }
        }
        aclCache.evictFromCache(objectIdentity);
    }
}
