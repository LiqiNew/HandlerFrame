package com.liqi.handler;

import java.lang.ref.SoftReference;

import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;

/**
 * 用集合存储软引用调用的handler(核心)
 * 
 * @author Liqi
 * 
 */
public class BaseHandler extends Handler implements OnBaseHandlerMethodListener {
	private static BaseHandler baseHandler;
	private BaseHandlerGetKey baseHandlerGetKey;
	private SparseArray<SoftReference<OnBaseHandlerUpDateListener>> arrayReference;
	private OnFactoryOperateListener mOnFactoryOperateListener;

	@Override
	public void setOnFactoryOperateListener(
			OnFactoryOperateListener onFactoryOperateListener) {
		this.mOnFactoryOperateListener = onFactoryOperateListener;
	}

	/**
	 * 获取handler对象
	 * 
	 * @return
	 */
	public synchronized static BaseHandler getBaseHandler() {
		synchronized (BaseHandler.class.getName()) {
			if (baseHandler == null) {
				baseHandler = new BaseHandler();
			}
		}
		return baseHandler;
	}

	private BaseHandler() {
		if (null == arrayReference) {
			arrayReference = new SparseArray<>();
		}
	}

	@Override
	public void handleMessage(Message msg) {
		if (null != arrayReference && arrayReference.size() > 0) {
			if (null != baseHandlerGetKey) {
				SoftReference<OnBaseHandlerUpDateListener> reference = arrayReference
						.get(baseHandlerGetKey.handlerGetKey());
				if (null != reference) {
					OnBaseHandlerUpDateListener baseActivity = reference.get();
					if (null != baseActivity) {
						baseActivity.handleMessage(msg);
					}
				}
			} else {
				System.out.println("handleMessage>>>>handler获取Key接口为空");
			}
		}
	}

	/**
	 * 把传进来的对象通过塞入软引用添加进SparseArray集合里面
	 * 
	 * @param activity
	 */
	@Override
	public void addSparseArray(OnBaseHandlerUpDateListener activity) {
		if (null != baseHandlerGetKey) {
			arrayReference.put(baseHandlerGetKey.handlerGetKey(),
					new SoftReference<>(activity));
		} else {
			System.out.println("addSparseArray>>>handler获取Key接口为空");
		}
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
	 * 清除SparseArray集合中指定键的软引用的数据 (!--Key是通过接口获取的，只需要调用此方法即可)
	 */
	@Override
	public void removeKeyData() {
		if (null != arrayReference && arrayReference.size() > 0) {
			if (null != baseHandlerGetKey) {
				int handlerGetKey = baseHandlerGetKey.handlerGetKey();
				SoftReference<OnBaseHandlerUpDateListener> reference = arrayReference
						.get(handlerGetKey);
				if (null != reference) {
					reference.clear();
					arrayReference.remove(handlerGetKey);
					if (mOnFactoryOperateListener != null) {
						mOnFactoryOperateListener.removeFactoryKeyData(handlerGetKey);
					}
				}
			} else {
				System.out.println("removeKeyData>>>handler获取Key接口为空");
			}
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
			if (mOnFactoryOperateListener != null) {
				mOnFactoryOperateListener.removeAllFactoryData();
			}
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
		int handlerGetKey();
	}
}
