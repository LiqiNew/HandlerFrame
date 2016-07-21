package com.liqi.eventbushandler.base;

import java.lang.ref.SoftReference;

import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;

/**
 * 用集合存储软引用调用的handler(核心类)
 * 
 * @author Liqi
 * 
 */
public class BaseHandler extends Handler implements BaseHandlerMethod {
	private static BaseHandler baseHandler;
	private BaseHandlerGetKey baseHandlerGetKey;
	private SparseArray<SoftReference<BaseHandlerUpDate>> arrayReference;

	/**
	 * 获取handler对象
	 * 
	 * @param activity
	 * @return
	 */
	public static BaseHandler getBaseHandler() {
		synchronized (BaseHandler.class.getName()) {
			if (baseHandler == null) {
				baseHandler = new BaseHandler();
			}
		}
		return baseHandler;
	}

	private BaseHandler() {
		if (null == arrayReference)
			arrayReference = new SparseArray<SoftReference<BaseHandlerUpDate>>();
	}

	@Override
	public void handleMessage(Message msg) {
		if (null != arrayReference && arrayReference.size() > 0) {
			if (null != baseHandlerGetKey) {
				SoftReference<BaseHandlerUpDate> reference = arrayReference
						.get(baseHandlerGetKey.handlerGetKey());
				if (null != reference) {
					BaseHandlerUpDate baseActivity = reference.get();
					if (null != baseActivity)
						baseActivity.handleMessage(msg);
				}
			} else
				System.out.println("handleMessage>>>>handler获取Key接口为空");
		}
	}

	/**
	 * 把传进来的对象通过塞入软引用添加进SparseArray集合里面
	 * 
	 * @param activity
	 */
	@Override
	public void addSparseArray(BaseHandlerUpDate activity) {
		if (null != baseHandlerGetKey) {
			arrayReference.put(baseHandlerGetKey.handlerGetKey(),
					new SoftReference<BaseHandlerUpDate>(activity));
		} else
			System.out.println("addSparseArray>>>handler获取Key接口为空");
	}

	/**
	 * 发送message信息个handler
	 * 
	 * @param tag
	 * @param obj
	 */
	@Override
	public void putMessage(int tag, Object obj) {
		Message message = this.obtainMessage();
		message.what = tag;
		message.obj = obj;
		this.sendMessage(message);
	}

	/**
	 * 清除SparseArray集合中指定键的弱引用的数据 (!--Key是通过接口获取的，只需要调用此方法即可)
	 */
	@Override
	public void removeKeyData() {
		if (null != arrayReference && arrayReference.size() > 0) {
			if (null != baseHandlerGetKey) {
				SoftReference<BaseHandlerUpDate> reference = arrayReference
						.get(baseHandlerGetKey.handlerGetKey());
				if (null != reference) {
					reference.clear();
					arrayReference.remove(baseHandlerGetKey.handlerGetKey());
				}
			} else
				System.out.println("removeKeyData>>>handler获取Key接口为空");
		}
	}

	/**
	 * 清除SparseArray集合里面所有值
	 */
	@Override
	public void removeAll() {
		if (null != arrayReference && arrayReference.size() > 0) {
			for (int i = 0; i < arrayReference.size(); i++) {
				arrayReference.valueAt(i).clear();
			}
			arrayReference.clear();
		}
	}

	@Override
	public void setBaseHandlerGetKey(BaseHandlerGetKey baseHandlerGetKey) {
		this.baseHandlerGetKey = baseHandlerGetKey;
	}

	/**
	 * Handler中SparseArray集合Key设置接口
	 * 
	 * @author Liqi
	 * 
	 */
	public interface BaseHandlerGetKey {
		/**
		 * 设置SparseArray集合存入的Key
		 * 
		 * @return
		 */
		public int handlerGetKey();
	}
}
