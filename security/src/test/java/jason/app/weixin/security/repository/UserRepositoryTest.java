package jason.app.weixin.security.repository;

import jason.app.weixin.security.BaseTestCase;
import jason.app.weixin.security.entity.UserImpl;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryTest extends BaseTestCase{
    @Autowired
    UserRepository repository;
	@Test
	public void testFindByUsername() {
		UserImpl user = repository.findByUsername("jason");
		assertNotNull(user);

	}
	@Override
	protected IDataSet getDataSet() throws Exception {
        final FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        return builder.build(this.getClass().getClassLoader().getResourceAsStream("dataset/users.xml"));

	}

}
