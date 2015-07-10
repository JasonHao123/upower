package jason.app.weixin.social.repository;

import jason.app.weixin.social.BaseTestCase;
import jason.app.weixin.social.entity.AddFriendLinkImpl;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class AddFriendLinkRepositoryTest extends BaseTestCase{
    private static final Logger logger = LoggerFactory
            .getLogger(AddFriendLinkRepositoryTest.class);
	@Autowired
	private AddFriendLinkRepository repo;
	@Test
	@Transactional
	public void test() {
		AddFriendLinkImpl link = new AddFriendLinkImpl();
		repo.save(link);
		logger.info(link.getId());
		assertNotNull(link.getId());
	}

    @Override
    protected IDataSet getDataSet() throws Exception {
        // TODO Auto-generated method stub
        final FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        return builder.build(this.getClass().getClassLoader().getResourceAsStream("dataset/users.xml"));

    }

}
