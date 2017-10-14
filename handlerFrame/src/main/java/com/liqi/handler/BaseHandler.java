package com.liqi.handler;

import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;

import java.lang.ref.SoftReference;

/**
 * 用集合存储软引用调用的handler(核心)
 *
 * @author Liqi
 */
class BaseHandler extends Handler implements OnBaseHandlerMethodListener {
    private static BaseHandler baseHandler;
    private final SparseArray<SoftReference<OnBaseHandlerUpDateListener>> arrayReference;
    private OnBaseHandlerGetKeyListener mOnBaseHandlerGetKeyListener;
    private OnFactoryOperateListener mOnFactoryOperateListener;

    private BaseHandler() {
        arrayReference = new SparseArray<>();
    }

    /**
     * 获取handler对象
     *
     * @return
     */
    synchronized static BaseHandler getBaseHandler() {
        synchronized (BaseHandler.class.getName()) {
            if (baseHandler == null) {
                baseHandler = new BaseHandler();
            }
        }
        return baseHandler;
    }

    @Override
    public void setOnFactoryOperateListener(
            OnFactoryOperateListener onFactoryOperateListener) {
        this.mOnFactoryOperateListener = onFactoryOperateListener;
    }

    @Override
    public void handleMessage(Message msg) {
        if (arrayReference.size() > 0) {
            if (null != mOnBaseHandlerGetKeyListener) {
                SoftReference<OnBaseHandlerUpDateListener> reference = arrayReference
                        .get(mOnBaseHandlerGetKeyListener.handlerGetKey());
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
        if (null != mOnBaseHandlerGetKeyListener) {
            arrayReference.put(mOnBaseHandlerGetKeyListener.handlerGetKey(),
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
        if (arrayReference.size() > 0) {
            if (null != mOnBaseHandlerGetKeyListener) {
                int handlerGetKey = mOnBaseHandlerGetKeyListener.handlerGetKey();
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
        if (arrayReference.size() > 0) {
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
    public void setOnBaseHandlerGetKeyListener(OnBaseHandlerGetKeyListener onBaseHandlerGetKeyListener) {
        this.mOnBaseHandlerGetKeyListener = onBaseHandlerGetKeyListener;
    }

    /**
     * Handler中SparseArray集合Key设置接口
     *
     * @author Liqi
     */
    interface OnBaseHandlerGetKeyListener {
        /**
         * 设置SparseArray集合存入的Key
         *
         * @return
         */
        int handlerGetKey();
    }
}
