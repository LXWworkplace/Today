package com.example.today;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.info.FetchInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

public class NewsGUI extends Activity {

	private static final String TAG = "ASYNC_TASK";
	private TextView textViewTitle, textViewTxt;
	private MyTask mTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newgui);

		// 网络请求放到了UI主线程里，这在android4.0之后是禁止的，不加上这段话会报错
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		// or .detectAll() for all detectable problems
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().penaltyLog() // 打印logcat
				.penaltyDeath().build());

		Intent intent = this.getIntent();
		String info = intent.getStringExtra("info");
		Log.i(TAG, info);
		String title = intent.getStringExtra("title");

		textViewTitle = (TextView) findViewById(R.id.newstitle);
		textViewTxt = (TextView) findViewById(R.id.newstxt);

		textViewTitle.setText(title);

		mTask = new MyTask();
		mTask.execute(info);
	}

	private class MyTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			Document tmp = null;
			try {
				tmp = Jsoup.connect(params[0]).timeout(60000).get();
			} catch (Exception e) {
				return "document error";
				// return null;
			}

			StringBuffer text = null;
			try {
				if (tmp != null) {
					String divName = "div.articletext";
					Elements divs = tmp.select(divName);
					text = new StringBuffer();
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
			} catch (Exception e) {
				return "divided error";
			}
			String result = text.toString();
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			textViewTxt.setText(result);
		}

	}
}