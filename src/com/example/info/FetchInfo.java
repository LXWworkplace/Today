package com.example.info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class FetchInfo {
	private Document doc;

	public FetchInfo() {
		doc = null;
	}

	private Document getDoc(String url) {

		Document tmp = null;
		try {
			tmp = Jsoup.connect(url).timeout(60000).get();
		} catch (Exception e) {
			System.out.println("url cann't open");
			// return null;
		}
		return tmp;
	}

	public ArrayList<Map<String, Object>> webRecommend(String name) {
		if ((doc = getDoc("http://today.hit.edu.cn")) != null) {
			String divName = null;
			if (name == "web")
				divName = "div.sidelist";
			else if(name == "reader")
				divName = "div.sidelist4";
			else if(name == "newest")
				divName = "div.sidelist5";

			Elements divs = doc.select(divName);
			ArrayList<Map<String, Object>> info = new  ArrayList<Map<String, Object>>();
			if (divs != null) {
				for (Element div : divs) {
					Elements links = div.select("a[href]");
					if (links != null) {
						for (Element link : links) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("info", link.attr("abs:href"));
							map.put("title", link.text());
							info.add(map);
						}
					}
				}
			}
			if (name == "web") {
				for (int i = info.size() - 2; i > 0; i -= 2)
					info.remove(i);
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