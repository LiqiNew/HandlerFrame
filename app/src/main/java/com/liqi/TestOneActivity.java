package com.liqi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TestOneActivity extends BaseActivity implements OnClickListener {
	// message中的what标签
	public static final int ONEACTIVITY_TAG = 0X23;
	private Button test_button01, test_button02;
	
	private TextView test_textview_new;

	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case ONEACTIVITY_TAG:
			System.out.println("接受“第三页面”发送内容执行了>>>>"+msg.obj);
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
		handler.onSubscribe(TestOneActivity.class,this);
		findViewById(R.id.test_button03).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				handler.onSubscribe(TestOneActivity.class,TestOneActivity.this);
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
			startActivity(new Intent(this, TestTwoActivity.class));
			break;
		case R.id.test_button02:
			handler.sendMessage(TestMainActivity.class, TestMainActivity.MAINACTIVITY_TAG_ONE, "“第二个界面”发送内容给“首页面”");
			break;
		}
	}

}
