package com.example.today;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.info.FetchInfo;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class IndexActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView listView = (ListView)findViewById(R.id.list);
		
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.mylistview, new String[] { "title", "info" },
				new int[] { R.id.title, R.id.info });
		listView.setAdapter(adapter);
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		FetchInfo fetchInfo = new FetchInfo();
		List<String> info = new ArrayList<String>();
		info = fetchInfo.webRecommend("web");

		for (int i = 0; i < info.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			i++;
			map.put("info", info.get(i));
			i += 2;
			map.put("title", info.get(i));
			list.add(map);
		}

		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
