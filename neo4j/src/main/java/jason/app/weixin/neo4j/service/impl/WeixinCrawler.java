package jason.app.weixin.neo4j.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import jason.app.weixin.social.model.Message;

public class WeixinCrawler {

	public static void fetch(String url, Message message) {
		// TODO Auto-generated method stub
        Document doc;
		try {
			doc = Jsoup.parse(new URL(url),5000);
			doc.select(".qr_code_pc_outer").remove();
			doc.select("script").remove();
			doc.select(".rich_media_area_extra").remove();
			doc.select(".rich_media_tool").remove();
			doc.select(".reward_area").remove();
			
			Iterator<Element> iter = doc.select("img[data-src]").iterator();
			while(iter.hasNext()) {
				Element element = iter.next();
				String value = element.attr("data-src");
				element.removeAttr("data-src");
				element.attr("src",value);
				element.attr("style","width:100%");
			}
			Element content = doc.select("#page-content").first();
			
	       // System.out.println(content.toString());
	        message.setContent(content.toString());
	        message.setTitle(doc.title());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
