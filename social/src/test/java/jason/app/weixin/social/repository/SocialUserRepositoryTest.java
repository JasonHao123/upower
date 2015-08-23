package jason.app.weixin.social.repository;

import jason.app.weixin.social.BaseTestCase;
import jason.app.weixin.social.entity.SocialUserImpl;
import jason.app.weixin.social.model.SocialUser;
import jason.app.weixin.social.service.ISocialService;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SocialUserRepositoryTest extends BaseTestCase {

	@Autowired
	private ISocialService socialService;
	
	@Autowired
	private SocialUserRepository repo;
	@Test
	public void test() {
		SocialUserImpl user = repo.findOne(1L);
		assertNotNull(user);
	}
	
	@Test
	public void testSaveProfile() {
		SocialUser socialUser = new SocialUser();
        socialUser.setNickname("test123");
        socialUser.setSex(1);
        socialUser.setCountry("China");
        socialUser.setProvince("Tianjin");
        socialUser.setCity("Tianjin");
        socialUser.setHeadimgurl("http://asfsadf");
        socialUser.setOpenid("test2342342");
		socialUser.setId(999L);
		socialService.saveProfile(socialUser);
		
		socialUser = socialService.loadProfile(999L);
		assertNotNull(socialUser);
	}

    @Override
    protected IDataSet getDataSet() throws Exception {
        // TODO Auto-generated method stub
        final FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        return builder.build(this.getClass().getClassLoader().getResourceAsStream("dataset/friendship.xml"));

    }

}
