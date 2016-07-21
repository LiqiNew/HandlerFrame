package com.liqi.eventbushandler.base;

import android.os.Message;

/**
 * handler回调更新接口
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
