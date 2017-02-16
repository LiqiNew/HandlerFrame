package com.liqi.eventbushandler.handler;

import java.util.ArrayList;

import com.liqi.eventbushandler.handler.BaseHandler.BaseHandlerGetKey;

/**
 * BaseHandler操作对象（核心）
 * 
 * @author Liqi
 * 
 */
public class BaseHandlerOperate implements BaseHandlerGetKey,
		FactoryOperateInterface {
	// 当前操作的对象class
	protected ArrayList<Class<?>> clazzList;
	private BaseHandlerMethod handler;
	private static BaseHandlerOperate handlerOperate;
	private BaseHandlerFactoryId factoryId;

	/**
	 * 获取hBaseHandler操作对象
	 * 
	 * @return
	 */
	public synchronized static BaseHandlerOperate getBaseHandlerOperate() {
		synchronized (BaseHandlerOperate.class.getName()) {
			if (null == handlerOperate) {
				handlerOperate = new BaseHandlerOperate();
			}
		}
		return handlerOperate;
	}

	private BaseHandlerOperate() {
		handler = BaseHandler.getBaseHandler();
		handler.setBaseHandlerGetKey(this);
		factoryId = BaseHandlerFactoryId.getBaseHandlerFactoryId();

		if (null == clazzList)
			clazzList = new ArrayList<Class<?>>();
	}

	/**
	 * 把当前对象对象添加到指定键里面
	 * 
	 * @param handlerKey
	 *            BaseHandler-要存储对象的calss
	 */
	public BaseHandlerOperate addKeyHandler(Class<?> clazz,
			BaseHandlerUpDate handlerUpDate) {
		if (null != clazz) {
			this.clazzList.add(clazz);
			handler.addSparseArray(handlerUpDate);
		}
		return this;
	}

	/**
	 * 给指定的handler发送message
	 * 
	 * @param handlerKey
	 *            BaseHandler-取出对象的class
	 * @param tag
	 *            Message标识
	 * @param obj
	 *            MessageObj数据源
	 */
	public BaseHandlerOperate putMessageKey(Class<?> clazz, int tag, Object obj) {
		if (null != clazz) {
			this.clazzList.add(clazz);
			handler.putMessage(tag, obj);
		}
		return this;
	}

	/**
	 * 移除BaseHandler里面的指定key对象
	 * 
	 * @param handlerKey
	 *            BaseHandler-移除对象的class
	 */
	public BaseHandlerOperate removeKeyData(Class<?> clazz) {
		if (null != clazz) {
			this.clazzList.add(clazz);
			handler.removeKeyData();
		}
		return this;
	}

	/**
	 * 移除所有的handler里面的对象
	 */
	public BaseHandlerOperate removeAll() {
		handler.removeAll();
		return this;
	}

	public BaseHandlerMethod getBaseHandler() {
		return handler;
	}

	@Override
	public int handlerGetKey() {
		int key = -1;
		if (!clazzList.isEmpty()) {
			// 取出对象存储集合的第一个，
			Class<?> clazz = clazzList.get(0);
			// 根据集合第一个数据产生取出对应的ID
			key = factoryId.getFactoryId(clazz);
			// 移除集合第一个对象
			clazzList.remove(0);
		}
		return key;
	}

	@Override
	public void removeFactoryKeyData() {
		if (!clazzList.isEmpty()) {
			// 取出对象存储集合的第一个，
			Class<?> clazz = clazzList.get(0);
			// 移除对象对应的ID
			factoryId.removeKeyData(clazz);
			// 移除集合第一个对象
			clazzList.remove(0);
		}
	}

	@Override
	public void removeAllFactoryData() {
		factoryId.removeAll();
	}
}
