package jason.app.weixin.social.repository;

import jason.app.weixin.social.BaseTestCase;
import jason.app.weixin.social.entity.FriendRelationshipImpl;

import java.util.List;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FriendRelationshipRepositoryTest extends BaseTestCase{
	@Autowired
	private FriendRelationshipRepository relationRepo;
	@Test
	public void testFindByFrom_Id() {
		List<FriendRelationshipImpl> result =relationRepo.findByFrom_Id(1L);
		assertNotNull(result);
	}

    @Override
    protected IDataSet getDataSet() throws Exception {
        // TODO Auto-generated method stub
        final FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        return builder.build(this.getClass().getClassLoader().getResourceAsStream("dataset/friendship.xml"));

    }

}
