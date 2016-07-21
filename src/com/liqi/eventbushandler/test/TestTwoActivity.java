package com.liqi.eventbushandler.test;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.liqi.eventbushandler.R;

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
	}

	@Override
	protected void onDestroyData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.test_button01:
			handler.putMessageKey(TestMainActivity.MAINACTIVITY_KEY,
					TestMainActivity.MAINACTIVITY_TAG_TWO,
					"第三个界面||TestTwoActivity发送内容给TestMainActivity");
			break;
		case R.id.test_button02:
			handler.putMessageKey(TestOneActivity.ONEACTIVITY_KEY,
					TestOneActivity.ONEACTIVITY_TAG,
					"第三个界面||TestTwoActivity发送内容给TestOneActivity||第二界面");
			break;
		}
	}

}
