package jason.app.weixin.security.service.impl;

import java.util.Arrays;

import jason.app.weixin.security.BaseTestCase;
import jason.app.weixin.security.exception.UserAlreadyExistException;
import jason.app.weixin.security.service.ISecurityService;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SecurityServiceImplTest extends BaseTestCase{

	@Autowired
	private ISecurityService service;
	@Test
	public void testCreateUser() {
		try {
			service.createUser("hello", "cde34rfv", Arrays.asList(new String[]{"ROLE_USER"}));
		} catch (UserAlreadyExistException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
        final FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        return builder.build(this.getClass().getClassLoader().getResourceAsStream("dataset/users.xml"));

	}
}
