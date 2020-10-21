package com.gitHub.past.executors;

import java.util.Map;
import java.util.UUID;

public class ThreadResult<T> {

    //线程返回值中为一标识
    private String key;

    //返回结果集合
    private T t;

    private Map<String,ThreadResult<T>> msf;

    {
        key = UUID.randomUUID().toString() + System.currentTimeMillis();
        System.out.println("给key赋值");
    }

    public ThreadResult(T t, Map<String, ThreadResult<T>> msf) {
        this.t = t;
        this.msf = msf;
    }

    public String getKey() {
        return key;
    }

    public T getFuture() {
        return future;
    }

    //当对象被回收时候触发的方法
    public void finalize() {
        msf.remove(key);
        System.out.println("对象销毁时候删除对应的值");
    }
}
