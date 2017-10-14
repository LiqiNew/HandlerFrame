package com.liqi.handler;

/**
 * 调用工厂方法接口
 * 
 * @author Liqi
 * 
 */
interface OnFactoryOperateListener {
	/**
	 * 把订阅ID从容器里面移除
	 */
	void removeFactoryKeyData(int subscribeId);

	/**
	 * 移除所有的订阅ID
	 */
	void removeAllFactoryData();
}
