package com.example.today;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.info.FetchInfo;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;

public class IndexActivity extends Activity {

	private TabHost tabHost;
	private ListView listView;
	private List<Map<String, Object>> list;
	private List<String> info;
	private FetchInfo fetchInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ��������ŵ���UI���߳������android4.0֮���ǽ�ֹ�ģ���������λ��ᱨ��
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		// or .detectAll() for all detectable problems
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().penaltyLog() // ��ӡlogcat
				.penaltyDeath().build());

		fetchInfo = new FetchInfo();

		// *************************����TabHost***********************//
		// ȡ��TabHost����
		tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup();
		/* ΪTabHost��ӱ�ǩ */
		// �½�һ��newTabSpec(newTabSpec)
		// �������ǩ��ͼ��(setIndicator)
		// ��������(setContent)
		tabHost.addTab(tabHost.newTabSpec("tab_test1").setIndicator("�Ƽ�")
				.setContent(R.id.list1));
		tabHost.addTab(tabHost.newTabSpec("tab_test2").setIndicator("����")
				.setContent(R.id.list2));
		tabHost.addTab(tabHost.newTabSpec("tab_test3").setIndicator("����")
				.setContent(R.id.list3));
		// ����TabHost�ı�����ɫ
		tabHost.setBackgroundColor(Color.argb(150, 22, 70, 150));
		// ����TabHost�ı���ͼƬ��Դ
		// tabHost.setBackgroundResource(R.drawable.bg0);
		// ���õ�ǰ��ʾ��һ����ǩ
		tabHost.setCurrentTab(0);

		listView = (ListView) findViewById(R.id.list1);

		// *******************���listView�ļ�������������*************************
		getData();

		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.mylistview, new String[] { "title", "info" },
				new int[] { R.id.title, R.id.info });
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {

				Intent intent = new Intent();
				Map<String, Object> val = list.get(position);
				intent.putExtra("title", val.get("title").toString());
				intent.putExtra("info", val.get("info").toString());
				intent.setClass(IndexActivity.this, NewsGUI.class);
				IndexActivity.this.startActivity(intent);
			}
		});
	}

	// ********************Ϊlistview�������********************************
	private void getData() {
		list = new ArrayList<Map<String, Object>>();
		info = new ArrayList<String>();
		info = fetchInfo.webRecommend("web");
		info.addAll(fetchInfo.webRecommend("reader"));

		for (int i = 0; i < info.size() - 1; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("info", info.get(i));
			i += 1;
			map.put("title", info.get(i));
			list.add(map);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
