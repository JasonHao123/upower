package jason.app.weixin.security.service.impl;

import jason.app.weixin.security.BaseTestCase;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

public class MutableAclServiceImplTest extends BaseTestCase{
    @Autowired
    private AuthenticationProvider authenticationProvider;
	@Autowired
	private MutableAclService aclService;
	
	 @Autowired
	    private PermissionEvaluator permissionEvaluator;

	@Test
	@Transactional
	public void testCreateAcl() {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("jason", "cde34rfv");
 
        Authentication authentication = authenticationProvider.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final ObjectIdentity objectIdentity = new ObjectIdentityImpl(Company.class, 1L);
		MutableAcl acl = aclService.createAcl(objectIdentity);
		assertNotNull(acl);
		 acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, new PrincipalSid("jason"), true);


		 boolean result = permissionEvaluator.hasPermission(authentication, new Company(1L) , BasePermission.ADMINISTRATION);
		 assertEquals(true, result);
	}
	
	@Override
	protected IDataSet getDataSet() throws Exception {
        final FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        return builder.build(this.getClass().getClassLoader().getResourceAsStream("dataset/acl.xml"));

	}

}
