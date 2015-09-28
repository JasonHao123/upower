package jason.app.weixin.neo4j.service.impl;

import jason.app.weixin.social.model.Message;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WebCrawler {

	public static void fetch(String url, Message message) {
		// TODO Auto-generated method stub
        Document doc;
		try {
			doc = Jsoup.parse(new URL(url),5000);
		//	doc.select("script").remove();
		//	System.out.println(doc.select("title").text());
			System.out.println();
			System.out.println();
			int length = doc.body().text().length();
			Element element = checkElements(doc.body(),length);

		//	System.out.println(element.text());
			//System.out.println(doc.body().text().length());
			//System.out.println(doc.select("#artical_real").text().length());
	    //    System.out.println(doc.select("h1").first().parent());
			message.setTitle(doc.title());
			message.setContent(element.text());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static Element checkElements(Element body, int length) {
		// TODO Auto-generated method stub
		Iterator<Element> iter = body.children().iterator();
		int maxLength = 0;
		Element target = null;
		while(iter.hasNext()) {
			Element element = iter.next();
		//	System.out.println(element.text().length());
			if(element.text().length()>maxLength) {
				maxLength = element.text().length();
				target = element;
			}
		}
		if(target!=null && maxLength>=length/2) {
			return checkElements(target, maxLength);
		}else {
			return body;
		}
		
	}

}
