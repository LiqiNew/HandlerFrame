package com.liqi.handler;

import android.os.Message;

/**
 * Handler信息接受回调更新接口（核心）
 * 
 * @author Liqi
 * 
 */
public interface OnBaseHandlerUpDateListener {
	/**
	 * handler回调接口
	 * @param msg
	 */
	void handleMessage(Message msg);
}
