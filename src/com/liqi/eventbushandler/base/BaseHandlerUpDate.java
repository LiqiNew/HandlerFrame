package com.liqi.eventbushandler.handler;

import android.os.Message;

/**
 * handler回调更新接口（核心）
 * 
 * @author Liqi
 * 
 */
public interface BaseHandlerUpDate {
	/**
	 * handler回调接口
	 * @param msg
	 */
	public void handleMessage(Message msg);
}
