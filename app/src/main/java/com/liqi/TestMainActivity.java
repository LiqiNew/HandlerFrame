package com.liqi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TestMainActivity extends BaseActivity implements OnClickListener {

	/**
	 * message中的what标签
	 */
	public static final int MAINACTIVITY_TAG_ONE = 0x12,
			MAINACTIVITY_TAG_TWO = 0x13;

	private Button test_button01;
	private TextView test_textview01, test_textview02;

	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case MAINACTIVITY_TAG_ONE:
			System.out.println("接受“第二页面”发送内容执行了>>>>"+msg.obj);
			test_textview01.setText(msg.obj.toString());
			break;
		case MAINACTIVITY_TAG_TWO:
			System.out.println("接受“第三页面”发送内容执行了>>>>"+msg.obj);
			test_textview02.setText(msg.obj.toString());
			break;
		}
	}

	@Override
	protected void onCreateData(Bundle savedInstanceState) {
		setContentView(R.layout.test_main_activity);
		test_button01 = (Button) findViewById(R.id.test_button01);
		test_button01.setOnClickListener(this);
		test_textview01 = (TextView) findViewById(R.id.test_textview01);
		test_textview02 = (TextView) findViewById(R.id.test_textview02);
		handler.onSubscribe(TestMainActivity.class,this);
	}

	@Override
	protected void onDestroyData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.test_button01:
			startActivity(new Intent(this, TestOneActivity.class));
			break;
		}
	}

}
