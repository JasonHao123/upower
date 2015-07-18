package jason.app.weixin.social.repository;

import jason.app.weixin.social.BaseTestCase;
import jason.app.weixin.social.entity.SocialUserImpl;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SocialUserRepositoryTest extends BaseTestCase {

	@Autowired
	private SocialUserRepository repo;
	@Test
	public void test() {
		SocialUserImpl user = repo.findOne(1L);
		assertNotNull(user);
	}

    @Override
    protected IDataSet getDataSet() throws Exception {
        // TODO Auto-generated method stub
        final FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        return builder.build(this.getClass().getClassLoader().getResourceAsStream("dataset/friendship.xml"));

    }

}
