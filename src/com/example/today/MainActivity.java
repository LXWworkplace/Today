package com.example.today;

import com.example.info.FetchInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index);
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new myButtonListener());
	}

	class myButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, IndexActivity.class);
			MainActivity.this.startActivity(intent);
		}
	}

}
