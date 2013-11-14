package com.example.today;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.info.FetchInfo;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;

public class IndexActivity extends Activity {

	TabHost tabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 //ȡ��TabHost����
        tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();
        /* ΪTabHost��ӱ�ǩ */
        //�½�һ��newTabSpec(newTabSpec)
        //�������ǩ��ͼ��(setIndicator)
        //��������(setContent)
        tabHost.addTab(tabHost.newTabSpec("tab_test1")
                .setIndicator("�Ƽ�")
                .setContent(R.id.list1));
        tabHost.addTab(tabHost.newTabSpec("tab_test2")
                .setIndicator("����")
                .setContent(R.id.list2));
        tabHost.addTab(tabHost.newTabSpec("tab_test3")
                .setIndicator("����")
                .setContent(R.id.list3));
         
        //����TabHost�ı�����ɫ
        tabHost.setBackgroundColor(Color.argb(150, 22, 70, 150));
        //����TabHost�ı���ͼƬ��Դ
        //tabHost.setBackgroundResource(R.drawable.bg0);
         
        //���õ�ǰ��ʾ��һ����ǩ
        tabHost.setCurrentTab(0);
         
        //��ǩ�л��¼�����setOnTabChangedListener
//        tabHost.setOnTabChangedListener(new OnTabChangeListener()
//        {
//            // TODO Auto-generated method stub
//            @Override
//            public void onTabChanged(String tabId)
//            {
//                Dialog dialog = new AlertDialog.Builder(Activity01.this)
//                        .setTitle("��ʾ")
//                        .setMessage("��ǰѡ�У�"+tabId+"��ǩ")
//                        .setPositiveButton("ȷ��",
//                        new DialogInterface.OnClickListener()
//                        {
//                            public void onClick(DialogInterface dialog, int whichButton)
//                            {
//                                dialog.cancel();
//                            }
//                        }).create();//������ť
//               
//                dialog.show();
//            }           
//        });
		ListView listView = (ListView) findViewById(R.id.list1);
		// ��������ŵ���UI���߳������android4.0֮���ǽ�ֹ�ģ���������λ��ᱨ��
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		// or .detectAll() for all detectable problems
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().penaltyLog() // ��ӡlogcat
				.penaltyDeath().build());
		// *********************************************************

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
		// ������ݵ�map
		Map<String, Object> map;

		for (int i = 0; i < info.size() - 3; i++) {
			map = new HashMap<String, Object>();
			i++;
			map.put("info", info.get(i));
			i += 2;
			map.put("title", info.get(i));
			list.add(map);
		}
		info = fetchInfo.webRecommend("reader");
		for (int i = 0; i < info.size() - 1; i++) {
			map = new HashMap<String, Object>();
			map.put("info", info.get(i));
			i += 1;
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
