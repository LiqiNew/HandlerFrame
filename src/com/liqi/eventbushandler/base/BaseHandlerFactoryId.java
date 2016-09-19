package com.liqi.eventbushandler.handler;

import java.util.Hashtable;

/**
 * 专门生产BaseHandler存储键ID对象
 * 
 * @author Liqi
 * 
 */
public class BaseHandlerFactoryId {
	// 初始化键ID值
	private int idIni = 0X8;
	// 工厂存储键ID值容器
	private Hashtable<Class<?>, Integer> hIds;
	private static BaseHandlerFactoryId baseHandlerIdObj;

	public synchronized static BaseHandlerFactoryId getBaseHandlerFactoryId() {
		return null == baseHandlerIdObj ? new BaseHandlerFactoryId()
				: baseHandlerIdObj;
	}

	private BaseHandlerFactoryId() {
		hIds = null == hIds ? new Hashtable<Class<?>, Integer>() : hIds;
	}

	/**
	 * 把指点key从容器里面移除
	 * 
	 * @param clazzKey
	 */
	public void removeKeyData(Class<?> clazzKey) {
		hIds.remove(clazzKey);
	}

	/**
	 * 清空存储容器
	 */
	public void removeAll() {
		hIds.clear();
		hIds = null;
	}

	/**
	 * 获取class对应的ID值
	 * 
	 * @param clazzKey
	 *            获取ID值的class
	 * @return
	 */
	public int getFactoryId(Class<?> clazzKey) {
		if (hIds.containsKey(clazzKey)) {
			return hIds.get(clazzKey);
		} else {
			return addClassID(clazzKey);
		}
	}

	/**
	 * 把对应的class的ID值存储进容器里面
	 * 
	 * @param clazzKey
	 * @return
	 */
	private synchronized int addClassID(Class<?> clazzKey) {
		int values = getValues();
		synchronized (this) {
			hIds.put(clazzKey, values);
		}
		return values;
	}

	/**
	 * 保证容器里面的ID值唯一性
	 */
	private synchronized int getValues() {
		if (hIds.containsValue(++idIni)) {
			getValues();
		}
		return idIni;
	}
}
