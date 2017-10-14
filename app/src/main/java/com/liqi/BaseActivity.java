package com.liqi;



import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;

import com.liqi.handler.BaseHandlerOperate;
import com.liqi.handler.OnBaseHandlerOperateListener;
import com.liqi.handler.OnBaseHandlerUpDateListener;

/**
 * 处理handler的activity基类
 * 
 * @author Liqi
 * 
 */
public abstract class BaseActivity extends FragmentActivity implements
		OnBaseHandlerUpDateListener {
	protected OnBaseHandlerOperateListener handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler=BaseHandlerOperate.getBaseHandlerOperate();
		onCreateData(savedInstanceState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		onDestroyData();
	}

	/**
	 * 界面创建方法
	 */
	protected abstract void onCreateData(Bundle savedInstanceState);

	/**
	 * 界面结束，数据释放的抽象方法
	 */
	protected abstract void onDestroyData();

	/**
	 * 处理handler返回的message信息
	 * 为什么我要重写这方法了！因为有些界面可能不需要handler通讯，所以就让这个抽象对象实现这个方法。谁需要谁重写就可以了
	 * 
	 * @param msg
	 */
	@Override
	public void handleMessage(Message msg) {

	}

}
