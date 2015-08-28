package jason.app.weixin.common;

import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:META-INF/test-context2.xml")
public class MessageTest extends AbstractJUnit4SpringContextTests implements MessageSourceAware{

	private MessageSource messageSource;

	@Override
	public void setMessageSource(MessageSource messageSource) {
		// TODO Auto-generated method stub
		this.messageSource = messageSource;
	}
	

    @Test
    public void testMessageSource() {
    	if(messageSource!=null) {
    		System.out.println("message: "+messageSource.getMessage("site.name", null, Locale.CHINESE));
    	}
    }
	
}
