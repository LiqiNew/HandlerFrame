package com.liqi.eventbushandler.base;

import com.liqi.eventbushandler.base.BaseHandler.BaseHandlerGetKey;

/**
 * BaseHandler操作对象（核心）
 * 
 * @author Liqi
 * 
 */
public class BaseHandlerOperate implements BaseHandlerGetKey {
	// handler往集合里面增加对象的key
	protected int handlerKey;
	private BaseHandlerMethod handler;
	private static BaseHandlerOperate handlerOperate;

	/**
	 * 获取hBaseHandler操作对象
	 * 
	 * @return
	 */
	public static BaseHandlerOperate getBaseHandlerOperate() {
		synchronized (BaseHandlerOperate.class.getName()) {
			if (handlerOperate == null) {
				handlerOperate = new BaseHandlerOperate();
			}
		}
		return handlerOperate;
	}

	private BaseHandlerOperate() {
		handler = BaseHandler.getBaseHandler();
		handler.setBaseHandlerGetKey(this);
	}

	/**
	 * 把当前对象对象添加到指定键里面
	 * 
	 * @param handlerKey
	 *            BaseHandler-存储对象的Key
	 */
	public BaseHandlerOperate addKeyHandler(int handlerKey, BaseHandlerUpDate handlerUpDate) {
		this.handlerKey = handlerKey;
		handler.addSparseArray(handlerUpDate);
		return this;
	}

	/**
	 * 给指定的handler发送message
	 * 
	 * @param handlerKey
	 *            BaseHandler-取出对象的Key
	 * @param tag
	 *            Message标识
	 * @param obj
	 *            MessageObj数据源
	 */
	public BaseHandlerOperate putMessageKey(int handlerKey, int tag, Object obj) {
		this.handlerKey = handlerKey;
		handler.putMessage(tag, obj);
		return this;
	}

	/**
	 * 移除BaseHandler里面的指定key对象
	 * 
	 * @param handlerKey
	 *            BaseHandler-取出对象的Key
	 */
	public BaseHandlerOperate removeKeyData(int handlerKey) {
		this.handlerKey = handlerKey;
		handler.removeKeyData();
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
		return handlerKey;
	}
}
