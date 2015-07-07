package jason.app.weixin.security.service.impl;

import jason.app.weixin.security.BaseTestCase;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class CustomUserDetailsServiceImplTest extends BaseTestCase{
	@Autowired
	private UserDetailsService userDetailService;
	@Test
	public void testLoadUserByUsername() {
		UserDetails user = userDetailService.loadUserByUsername("jason");
		assertNotNull(user);
		assertEquals("jason", user.getUsername());
		assertNotNull(user.getAuthorities());
		assertEquals(1, user.getAuthorities().size());
	}
	@Override
	protected IDataSet getDataSet() throws Exception {
        final FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        return builder.build(this.getClass().getClassLoader().getResourceAsStream("dataset/users.xml"));

	}
}
