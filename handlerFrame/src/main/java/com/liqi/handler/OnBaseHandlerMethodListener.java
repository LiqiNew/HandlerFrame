package com.liqi.handler;


/**
 * BaseHandler调用方法接口（核心）
 * 
 * @author Liqi
 * 
 */
interface OnBaseHandlerMethodListener {
	/**
	 * 把传进来的对象通过塞入软引用添加进SparseArray集合里面
	 * 
	 * @param handlerUpDate handler回调更新接口
	 */
	void addSparseArray(OnBaseHandlerUpDateListener handlerUpDate);

	/**
	 * 发送message信息个handler
	 * 
	 * @param tag
	 * @param obj
	 */
	void putMessage(int tag, Object obj);

	/**
	 * 清除SparseArray集合中指定键的软引用的数据 (!--Key是通过接口获取的，只需要调用此方法即可)
	 */
	void removeKeyData();

	/**
	 * 清除SparseArray集合里面所有值
	 */
	void removeAll();

	/**
	 * 赋值获取Key接口
	 * 
	 * @param onBaseHandlerGetKeyListener
	 */
	void setOnBaseHandlerGetKeyListener(BaseHandler.OnBaseHandlerGetKeyListener onBaseHandlerGetKeyListener);

	/**
	 * 赋值移除订阅ID接口
	 * @param onFactoryOperateListener 移除订阅ID接口
     */
	void setOnFactoryOperateListener(OnFactoryOperateListener onFactoryOperateListener);
}
