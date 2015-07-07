package jason.app.weixin.security.service.impl;

import jason.app.weixin.security.entity.AclClassImpl;
import jason.app.weixin.security.entity.AclEntryImpl;
import jason.app.weixin.security.entity.AclObjectIdentityImpl;
import jason.app.weixin.security.entity.AclSidImpl;
import jason.app.weixin.security.model.AclInfo;
import jason.app.weixin.security.service.IAclDao;
import jason.app.weixin.security.translator.AclInfoTranslator;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("aclDao")
public class AclDaoImpl implements IAclDao {
    
    public final static String DEFAULT_SELECT_CLAUSE = "select aoi.objIdIdentity, "
            + "ae.aceOrder as ace_order,  "
            + "aoi.id as acl_id, "
            + "parent.id as parent_object, "
            + "aoi.entriesInheriting as entries_inheriting, "
            + "ae.id as ace_id, "
            + "ae.mask,  "
            + "ae.granting,  "
            + "ae.auditSuccess as audit_success, "
            + "ae.auditFailure as audit_failure,  "
            + "as2.principal as ace_principal, "
            + "as2.sid as ace_sid,  "
            + "as1.principal as acl_principal, "
            + "as1.sid as acl_sid, "
            + "ac.clazz "
            + "from AclObjectIdentityImpl aoi "
            + "left join aoi.parentObject parent "
            + "left join aoi.owner as1 "
            + "left join aoi.objIdClass ac  "
            + "left join aoi.aclEntries ae "
            + "left join ae.sid as2 "
            + "where  ";
        private final static String DEFAULT_LOOKUP_KEYS_WHERE_CLAUSE = " ( aoi.id = ? ) ";

        private final static String DEFAULT_LOOKUP_IDENTITIES_WHERE_CLAUSE = " ( aoi.objIdIdentity = ? and ac.clazz = ? ) ";

        public final static String DEFAULT_ORDER_BY_CLAUSE = " order by aoi.objIdIdentity"
                + " asc, ae.aceOrder asc";
        
        // SQL Customization fields
        private String selectClause = DEFAULT_SELECT_CLAUSE;
        private String lookupPrimaryKeysWhereClause = DEFAULT_LOOKUP_KEYS_WHERE_CLAUSE;
        private String lookupObjectIdentitiesWhereClause = DEFAULT_LOOKUP_IDENTITIES_WHERE_CLAUSE;
        private String orderByClause = DEFAULT_ORDER_BY_CLAUSE;
        

        private String computeRepeatingSql(String repeatingSql, int requiredRepetitions) {
            assert requiredRepetitions > 0 : "requiredRepetitions must be > 0";

            final String startSql = selectClause + (requiredRepetitions==1?"":"(");
            
            final String endSql = orderByClause+(requiredRepetitions==1?"":")");

            StringBuilder sqlStringBldr =
                new StringBuilder(startSql.length() + endSql.length() + requiredRepetitions * (repeatingSql.length() + 4));
            sqlStringBldr.append(startSql);

            for (int i = 0; i < requiredRepetitions; i++) {
            	String sql = repeatingSql.replaceFirst("\\?", ":arg0"+i);
            	 sql = sql.replaceFirst("\\?", ":arg1"+i);
            	
                sqlStringBldr.append(sql);
            //    sqlStringBldr.append(repeatingSql.replaceFirst("\\?", ":arg1"+i));
                if (i != requiredRepetitions-1) {
                    sqlStringBldr.append(" or ");
                }
            }

            sqlStringBldr.append(endSql);

            return sqlStringBldr.toString();
        }

        @PersistenceContext(unitName="SecurityPU")
    private EntityManager em;


	@Override
    public List<AclObjectIdentityImpl> findChildren(Serializable identifier, String type) {
        Query query = em.createQuery("select aoi from AclObjectIdentityImpl aoi, AclObjectIdentityImpl parent, AclClassImpl aclClass where aoi.parentObject = parent and aoi.objIdClass = aclClass and parent.objIdIdentity = :objIdIdentity and parent.objIdClass = (select acl FROM AclClassImpl acl where acl.clazz = :clazz)");
        query.setParameter("objIdIdentity", identifier);
        query.setParameter("clazz", type);
       
        return query.getResultList();
    }

    @Override
    public AclObjectIdentityImpl getObjectIdentity(String type, Serializable identifier) {
        Query query = em.createQuery("select aoi from AclObjectIdentityImpl aoi, AclClassImpl aclClass where  aoi.objIdIdentity = :objIdIdentity and aoi.objIdClass = aclClass and aclClass.clazz = :clazz");
        query.setParameter("objIdIdentity", identifier);
        query.setParameter("clazz", type);

        return  (AclObjectIdentityImpl) query.getSingleResult();
    }

    @Override
    public void createObjectIdentity(AclObjectIdentityImpl identity) {
        // TODO Auto-generated method stub
        em.persist(identity);
        em.flush();
    }

    @Override
    public List<AclSidImpl> findAclSidList(Boolean principal, String sidName) {
        Query query = em.createQuery("select sid from AclSidImpl sid where sid.principal=:principal and sid.sid=:sid");
        query.setParameter("principal", principal);
        query.setParameter("sid", sidName);
        return query.getResultList();
    }

    @Override
    @Transactional
    public AclSidImpl createAclSid(AclSidImpl sid) {
      
        em.persist(sid);
        em.flush();
        return sid;
    }

    @Override
    public List<AclClassImpl> findAclClassList(String type) {
        Query query = em.createQuery("select clazz from AclClassImpl clazz where clazz.clazz=:clazz");

        query.setParameter("clazz", type);
        return query.getResultList();
    }

    @Override
    
    public AclClassImpl createAclClass(AclClassImpl acl) {
        
        em.persist(acl);
        em.flush();
        return acl;
    }

    @Override
    
    public void deleteEntries(AclObjectIdentityImpl objectIdentity) {
        AclObjectIdentityImpl objectIdentity2 = em.find(AclObjectIdentityImpl.class, objectIdentity.getId());
        if(objectIdentity2.getEntries()!=null) {
            for(AclEntryImpl entry:objectIdentity2.getEntries()) {
                em.remove(entry);
            }
        }
      
    }

    @Override
    public void deleteObjectIdentity(AclObjectIdentityImpl oidPrimaryKey) {
        // TODO Auto-generated method stub
        em.remove(oidPrimaryKey);
    }

    @Override
    
    public void createEntries(List<AclEntryImpl> entries) {
        for(AclEntryImpl entry:entries) {
            em.persist(entry);
        }
        
    }

    @Override
    public boolean updateObjectIdentity(AclObjectIdentityImpl aclObject) {
        // TODO Auto-generated method stub
         em.merge(aclObject);
         return true;
    }

    @Override
    public AclSidImpl findAclSid(String principal) {
        Query query = em.createQuery("select sid from AclSidImpl sid where sid.sid=:sid");

        query.setParameter("sid", principal);
        List<AclSidImpl> results = query.getResultList();
        if(results.size()>0) {
            return results.get(0);
        }
        return null;
    }

    @Override
    public List<AclInfo> findParentsToLookup(Set<Long> findNow) {
        // TODO Auto-generated method stub
        String sql = computeRepeatingSql(lookupPrimaryKeysWhereClause, findNow.size());
      //  Query query = em.createNativeQuery(sql);
        Query query = em.createQuery(sql);
        int i = 0;
        for (Long toFind : findNow) {
            i++;
            query.setParameter("arg0"+i, toFind);
        }
        List<Object[]> result = query.getResultList();
        return AclInfoTranslator.toAclInfoList(result);
    }

    @Override
    public List<AclInfo> findParentsToLookup(Collection<ObjectIdentity> objectIdentities) {
        String sql = computeRepeatingSql(lookupObjectIdentitiesWhereClause, objectIdentities.size());
        
       // Query query = em.createNativeQuery(sql);
        Query query = em.createQuery(sql);
        int i = 0;
        for (ObjectIdentity oid : objectIdentities) {
            // Determine prepared statement values for this iteration
            String type = oid.getType();

            // No need to check for nulls, as guaranteed non-null by ObjectIdentity.getIdentifier() interface contract
            String identifier = oid.getIdentifier().toString();
            long id = (Long.valueOf(identifier)).longValue();

            // Inject values
            query.setParameter("arg0"+i, id);
            query.setParameter("arg1"+i, type);
            i++;
        }
        List<Object[]> result = query.getResultList();
        return AclInfoTranslator.toAclInfoList(result);
    }

}
