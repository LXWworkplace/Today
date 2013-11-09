package com.example.info;

import java.util.LinkedList;
import java.util.List;

//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.HttpStatus;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class FetchInfo {
	private Document doc;

	public FetchInfo() {
		doc = null;
	}
//	public static String getHtmlByUrl(String url){  
//        String html = null;  
//        HttpClient httpClient = new DefaultHttpClient();//创建httpClient对象  
//        HttpGet httpget = new HttpGet(url);//以get方式请求该URL  
//        try {  
//            HttpResponse responce = httpClient.execute(httpget);//得到responce对象  
//            int resStatu = responce.getStatusLine().getStatusCode();//返回码  
//            if (resStatu==HttpStatus.SC_OK) {//200正常  其他就不对  
//                //获得相应实体  
//                HttpEntity entity = responce.getEntity();  
//                if (entity!=null) {  
//                    html = EntityUtils.toString(entity);//获得html源代码  
//                }  
//            }  
//        } catch (Exception e) {  
//            System.out.println("访问【"+url+"】出现异常!");  
//            e.printStackTrace();  
//        } finally {  
//            httpClient.getConnectionManager().shutdown();  
//        }  
//        return html;  
//    }  

	private Document getDoc(String url) {
		Document tmp = null;
		try {
			tmp = Jsoup.connect(url).timeout(60000).get();
		} catch (Exception e) {
			System.out.println("url cann't open");
			return null;
		}
		return tmp;
	}

	public List<String> webRecommend(String name) {
		if ((doc = getDoc("http://today.hit.edu.cn")) != null) {
			String divName = null;
			if (name == "web")
				divName = "div.sidelist";
			else
				divName = "div.sidelist4";

			Elements divs = doc.select(divName);
			List<String> info = new LinkedList<String>();
			if (divs != null) {
				for (Element div : divs) {
					Elements links = div.select("a[href]");
					if (links != null) {
						for (Element link : links) {
							info.add(link.attr("abs:href"));
							info.add(link.text());
						}
					}
				}
			}
			return info;
		}
		return null;
	}

	public StringBuffer getText(String url) {
		if ((doc = getDoc(url)) != null) {
			String divName = "div.articletext";
			Elements divs = doc.select(divName);
			StringBuffer text = new StringBuffer();
			if (divs != null) {
				for (Element div : divs) {
					Elements links = div.select("P");
					if (links != null) {
						for (Element link : links) {
							text.append(link.text() + "\n");
						}
					}
				}
			}
		}
		return null;
	}
}