package com.liqi;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestTwoActivity extends BaseActivity implements OnClickListener {

	private Button test_button01, test_button02;

	@Override
	public void handleMessage(Message msg) {

	}

	@Override
	protected void onCreateData(Bundle savedInstanceState) {
		setContentView(R.layout.test_two_activity);
		test_button01 = (Button) findViewById(R.id.test_button01);
		test_button01.setOnClickListener(this);
		test_button02 = (Button) findViewById(R.id.test_button02);
		test_button02.setOnClickListener(this);
		findViewById(R.id.test_button03).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				handler.removeSubscribe(TestOneActivity.class);
			}
		});
	}

	@Override
	protected void onDestroyData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.test_button01:
			handler.sendMessage(TestMainActivity.class,
					TestMainActivity.MAINACTIVITY_TAG_TWO,
					"“第三个界面”同时发送内容给“首界面”");
			handler.sendMessage(TestOneActivity.class,
					TestOneActivity.ONEACTIVITY_TAG,
					"“第三个界面”同时发送内容给“第二界面”");
			break;
		case R.id.test_button02:
			handler.sendMessage(TestOneActivity.class,
					TestOneActivity.ONEACTIVITY_TAG,
					"“第三个界面”发送内容给“第二界面”");
			break;
		}
	}

}
