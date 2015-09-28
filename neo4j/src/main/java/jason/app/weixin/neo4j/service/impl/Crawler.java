package jason.app.weixin.neo4j.service.impl;

public class Crawler {

	public static void fetch(String url, jason.app.weixin.social.model.Message message) {
		// TODO Auto-generated method stub
		if(url.indexOf("mp.weixin.qq.com")>0) {
			WeixinCrawler.fetch(url,message);
		}else {
			WebCrawler.fetch(url,message);
		}
	}

}
