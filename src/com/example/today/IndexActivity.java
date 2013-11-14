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
		 //取得TabHost对象
        tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();
        /* 为TabHost添加标签 */
        //新建一个newTabSpec(newTabSpec)
        //设置其标签和图标(setIndicator)
        //设置内容(setContent)
        tabHost.addTab(tabHost.newTabSpec("tab_test1")
                .setIndicator("推荐")
                .setContent(R.id.list1));
        tabHost.addTab(tabHost.newTabSpec("tab_test2")
                .setIndicator("最新")
                .setContent(R.id.list2));
        tabHost.addTab(tabHost.newTabSpec("tab_test3")
                .setIndicator("公告")
                .setContent(R.id.list3));
         
        //设置TabHost的背景颜色
        tabHost.setBackgroundColor(Color.argb(150, 22, 70, 150));
        //设置TabHost的背景图片资源
        //tabHost.setBackgroundResource(R.drawable.bg0);
         
        //设置当前显示哪一个标签
        tabHost.setCurrentTab(0);
         
        //标签切换事件处理，setOnTabChangedListener
//        tabHost.setOnTabChangedListener(new OnTabChangeListener()
//        {
//            // TODO Auto-generated method stub
//            @Override
//            public void onTabChanged(String tabId)
//            {
//                Dialog dialog = new AlertDialog.Builder(Activity01.this)
//                        .setTitle("提示")
//                        .setMessage("当前选中："+tabId+"标签")
//                        .setPositiveButton("确定",
//                        new DialogInterface.OnClickListener()
//                        {
//                            public void onClick(DialogInterface dialog, int whichButton)
//                            {
//                                dialog.cancel();
//                            }
//                        }).create();//创建按钮
//               
//                dialog.show();
//            }           
//        });
		ListView listView = (ListView) findViewById(R.id.list1);
		// 网络请求放到了UI主线程里，这在android4.0之后是禁止的，不加上这段话会报错
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		// or .detectAll() for all detectable problems
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().penaltyLog() // 打印logcat
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
		// 存放数据的map
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
