package jason.app.weixin.web.oauth;

import org.scribe.builder.api.DefaultApi20;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.extractors.JsonTokenExtractor;
import org.scribe.model.OAuthConfig;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuth20ServiceImpl;
import org.scribe.oauth.OAuthService;
import org.scribe.utils.OAuthEncoder;

public class WeixinApi  extends DefaultApi20{
	  private static final String BASE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code";
	  private static final String WEBCHAT_REDIRECT = "#wechat_redirect";
	  private static final String AUTHORIZE_URL = BASE_URL +WEBCHAT_REDIRECT;
	  private static final String SCOPED_AUTHORIZE_URL = BASE_URL + "&scope=%s"+WEBCHAT_REDIRECT;

	  @Override
	  public Verb getAccessTokenVerb()
	  {
	    return Verb.POST;
	  }

	  @Override
	  public AccessTokenExtractor getAccessTokenExtractor()
	  {
	    return new JsonTokenExtractor();
	  }

	  @Override
	  public String getAccessTokenEndpoint()
	  {
	    return "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code";
	  }

	  @Override
	  public String getAuthorizationUrl(OAuthConfig config)
	  {
	    // Append scope if present
	    if (config.hasScope())
	    {
	      return String.format(SCOPED_AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()), OAuthEncoder.encode(config.getScope()));
	    }
	    else
	    {
	      return String.format(AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
	    }
	  }
	  
	  @Override
	public OAuthService createService(OAuthConfig config) {
		// TODO Auto-generated method stub
		  return new WeixinServiceImpl(this, config);
	}
	}