package com.liqi.eventbushandler.base;

import com.liqi.eventbushandler.base.BaseHandler.BaseHandlerGetKey;

/**
 * BaseHandler调用方法接口
 * 
 * @author Liqi
 * 
 */
public interface BaseHandlerMethod {
	/**
	 * 把传进来的对象通过塞入软引用添加进SparseArray集合里面
	 * 
	 * @param activity
	 */
	public void addSparseArray(BaseHandlerUpDate handlerUpDate);

	/**
	 * 发送message信息个handler
	 * 
	 * @param tag
	 * @param obj
	 */
	public void putMessage(int tag, Object obj);

	/**
	 * 清除SparseArray集合中指定键的弱引用的数据 (!--Key是通过接口获取的，只需要调用此方法即可)
	 */
	public void removeKeyData();

	/**
	 * 清除SparseArray集合里面所有值
	 */
	public void removeAll();

	/**
	 * 赋值获取Key接口
	 * 
	 * @param baseHandlerGetKey
	 */
	public void setBaseHandlerGetKey(BaseHandlerGetKey baseHandlerGetKey);
}
