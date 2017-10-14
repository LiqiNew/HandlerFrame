package com.liqi.handler;

import java.util.ArrayList;


/**
 * BaseHandler操作对象（核心）
 *
 * @author Liqi
 */
public class BaseHandlerOperate implements BaseHandler.OnBaseHandlerGetKeyListener,
        OnFactoryOperateListener, OnBaseHandlerOperateListener {
    private static BaseHandlerOperate handlerOperate;
    // 订阅对象临时存储集合
    private final ArrayList<Class<?>> subscribeList;

    private OnBaseHandlerMethodListener handler;
    private BaseHandlerFactoryId factoryId;

    private BaseHandlerOperate() {
        handler = BaseHandler.getBaseHandler();
        handler.setOnFactoryOperateListener(this);
        handler.setOnBaseHandlerGetKeyListener(this);
        factoryId = BaseHandlerFactoryId.getBaseHandlerFactoryId();
        subscribeList = new ArrayList<>();
    }

    /**
     * 获取BaseHandler操作对象暴露接口
     *
     * @return
     */
    public synchronized static OnBaseHandlerOperateListener getBaseHandlerOperate() {
        synchronized (BaseHandlerOperate.class.getName()) {
            if (null == handlerOperate) {
                handlerOperate = new BaseHandlerOperate();
            }
        }
        return handlerOperate;
    }

    /**
     * 把OnBaseHandlerUpDateListener接口实现对象注册到指定对象中
     *
     * @param clazz         BaseHandler-要存储对象的calss
     * @param handlerUpDate Handler信息接受回调更新接口
     */
    @Override
    public void onSubscribe(Class<?> clazz,
                            OnBaseHandlerUpDateListener handlerUpDate) {
        if (null != clazz) {
            this.subscribeList.add(clazz);
            handler.addSparseArray(handlerUpDate);
        }
    }

    /**
     * 给指定的handler发送message
     *
     * @param clazz 接受信息的对象
     * @param tag   Message标识
     * @param obj   MessageObj数据源
     */
    @Override
    public void sendMessage(Class<?> clazz, int tag, Object obj) {
        if (null != clazz) {
            this.subscribeList.add(clazz);
            handler.putMessage(tag, obj);
        }
    }

    /**
     * 移除指定订阅对象
     *
     * @param clazz 指定订阅对象class
     */
    @Override
    public void removeSubscribe(Class<?> clazz) {
        if (null != clazz) {
            this.subscribeList.add(clazz);
            handler.removeKeyData();
        }
    }

    /**
     * 移除所有订阅对象
     */
    @Override
    public void removeSubscribe() {
        handler.removeAll();
    }

    @Override
    public synchronized int handlerGetKey() {
        synchronized (this) {
            int key = -1;
            if (!subscribeList.isEmpty()) {
                // 取出对象存储集合的第一个，
                Class<?> clazz = subscribeList.get(0);
                // 根据集合第一个数据产生取出对应的ID
                key = factoryId.getFactoryId(clazz);
                // 移除集合第一个对象
                subscribeList.remove(0);
            }
            return key;
        }
    }

    @Override
    public void removeFactoryKeyData(int subscribeId) {
        factoryId.removeSubscribeId(subscribeId);
    }

    @Override
    public void removeAllFactoryData() {
        factoryId.removeAllSubscribeId();
    }
}
