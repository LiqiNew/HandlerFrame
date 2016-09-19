package com.liqi.eventbushandler.handler;

/**
 * 调用工厂方法接口
 * 
 * @author Liqi
 * 
 */
public interface FactoryOperateInterface {
	/**
	 * 删除指定键数据
	 */
	public void removeFactoryKeyData();

	/**
	 * 删除全部数据
	 */
	public void removeAllFactoryData();
}
