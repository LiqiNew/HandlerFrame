package com.liqi.eventbushandler.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.liqi.eventbushandler.R;

public class TestOneActivity extends BaseActivity implements OnClickListener {
	// message中的what标签
	public static final int ONEACTIVITY_TAG = 0X23;
	private Button test_button01, test_button02;
	
	private TextView test_textview_new;

	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case ONEACTIVITY_TAG:
			System.out.println("第二页面回调执行了>>>>"+msg.obj);
			test_textview_new.setText(msg.obj.toString());
			break;
		}
	}

	@Override
	protected void onCreateData(Bundle savedInstanceState) {
		setContentView(R.layout.test_one_activity);
		test_button01 = (Button) findViewById(R.id.test_button01);
		test_button01.setOnClickListener(this);
		test_button02 = (Button) findViewById(R.id.test_button02);
		test_button02.setOnClickListener(this);
		test_textview_new = (TextView) findViewById(R.id.test_textview_new);
		handler.addKeyHandler(TestOneActivity.class,this);
	}

	@Override
	protected void onDestroyData() {
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.test_button01:
			startActivity(new Intent(this, TestTwoActivity.class));
			break;
		case R.id.test_button02:
			handler.putMessageKey(TestMainActivity.class, TestMainActivity.MAINACTIVITY_TAG_ONE, "TestOneActivity发送内容给TestMainActivity||第二个界面");
			break;
		}
	}

}
