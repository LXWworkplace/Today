package com.example.info;

import java.util.LinkedList;
import java.util.List;

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
			// System.out.println("url cann't open");
			// return null;
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