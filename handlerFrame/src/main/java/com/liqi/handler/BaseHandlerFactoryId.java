package com.liqi.handler;

import java.util.Hashtable;
import java.util.Map;

/**
 * 专门生产BaseHandler存储键ID对象
 *
 * @author Liqi
 */
class BaseHandlerFactoryId {
    private static BaseHandlerFactoryId baseHandlerIdObj;
    // 初始化键ID值
    private int idIni = 0X8;
    // 工厂存储键ID值容器
    private final Hashtable<Class<?>, Integer> hIds;

    private BaseHandlerFactoryId() {
        hIds =  new Hashtable<>();
    }

    synchronized static BaseHandlerFactoryId getBaseHandlerFactoryId() {
        return baseHandlerIdObj = null == baseHandlerIdObj ? new BaseHandlerFactoryId()
                : baseHandlerIdObj;
    }

    /**
     * 把订阅ID从容器里面移除
     *
     * @param subscribeId 订阅ID
     */
    void removeSubscribeId(int subscribeId) {
        Class<?> subscribeClass = null;
        if (!hIds.isEmpty()) {
            for (Map.Entry<Class<?>, Integer> entry : hIds.entrySet()) {
                Integer value = entry.getValue();
                if (subscribeId == value) {
                    subscribeClass = entry.getKey();
                    break;
                }
            }

            if (null != subscribeClass) {
                hIds.remove(subscribeClass);
            }
        }
    }

    /**
     * 移除容器里面所有的订阅ID
     */
    void removeAllSubscribeId() {
        hIds.clear();
    }

    /**
     * 获取class对应的ID值
     *
     * @param clazzKey 获取ID值的class
     * @return
     */
    int getFactoryId(Class<?> clazzKey) {
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
