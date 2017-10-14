package com.liqi.handler;

/**BaseHandler操作对象对外暴露接口
 * Created by LiQi on 2017/10/14.
 */

public interface OnBaseHandlerOperateListener {

    void onSubscribe(Class<?> clazz,
                                   OnBaseHandlerUpDateListener handlerUpDate);
    void sendMessage(Class<?> clazz, int tag, Object obj);

    void removeSubscribe(Class<?> clazz);

    void removeSubscribe();
}
