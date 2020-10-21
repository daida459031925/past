package com.gitHub.past.executors;

import com.gitHub.past.common.SysFun;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 1.singleton单例模式：全局有且仅有一个实例
 * 2.prototype原型模式：每次获取Bean的时候会有一个新的实例
 * 3.request：request表示该针对每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前HTTP request内有效，
 * 4.session：session作用域表示该针对每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前HTTP session内有效
 * 5.global session：global session作用域类似于标准的HTTP Session作用域，不过它仅仅在基于portlet的web应用中才有意义。
 * Portlet规范定义了全局Session的概念，它被所有构成某个 portlet web应用的各种不同的portlet所共享。在global session作用域中
 * 定义的bean被限定于全局portlet Session的生命周期范围内。如果你在web中使用global session作用域来标识bean，那么web会自动当成session类型来使用。
 */

/**@Scope("singleton") //spring boot 中定义单例模式
 * */
public abstract class ExecutorsUtil {

    //newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
    //空闲可回收这个尽量不要用
    private static Supplier<ExecutorService> newCachedThreadPool = Executors::newCachedThreadPool;
    //newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
    //适用于异步无任何先后完成情况牵连的
    private static Function<Integer,ExecutorService> newFixedThreadPool = Executors::newFixedThreadPool;
    //newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
    //适用于定时任务
    private static Function<Integer,ExecutorService> newScheduledThreadPool = Executors::newScheduledThreadPool;
    //newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
    //适用于数据的添加以及更新操作，此类操作具有顺序性不能乱，
    private static Supplier<ExecutorService> newSingleThreadExecutor = Executors::newSingleThreadExecutor;

    private Map<String, Future> msf = new HashMap<>();

    public static ExecutorService cachedThreadPool;
    public static ExecutorService fixedThreadPool;
    public static ExecutorService scheduledThreadPool;
    public static ExecutorService singleThreadExecutor;

    //spring中Constructor、@Autowired、@PostConstruct的顺序
    //Constructor >> @Autowired >> @PostConstruct
    //@PreDestroy是通过org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor.
    // postProcessBeforeDestruction(Object, String)被调用（InitDestroyAnnotationBeanPostProcessor实现了该接口），该方法的说明如下：
    //Apply this BeanPostProcessor to the given bean instance before its destruction. Can invoke custom destruction callbacks.
    //Like DisposableBean's destroy and a custom destroy method, this callback just applies to singleton beans in the factory (including inner beans).
    @PostConstruct//项目启动时候加载一次
    protected void init(){

    }

    @PreDestroy //结束时候执行一次
    //是先调用DestructionAwareBeanPostProcessor的postProcessBeforeDestruction(@PreDestroy标记的方法被调用)，再是DisposableBean的destory方法，最后是自定义销毁方法；
    protected void exit(){
        //执行此函数后线程池不再接收新任务，并等待所有任务执行完毕后销毁线程。此函数不会等待销毁完毕
        //executor.shutdown();
        //立即结束所有线程，不管是否正在运行，返回未执行完毕的任务列表
        //executor.shutdownNow();
        cachedThreadPool.shutdown();
        fixedThreadPool.shutdown();
        scheduledThreadPool.shutdown();
        singleThreadExecutor.shutdown();
    }

    //当对象被回收时候触发的方法
    public void finalize() {
        msf.remove(key);
        System.out.println("对象销毁时候删除对应的值");
    }

    public static void setNewCachedThreadPool() {
        cachedThreadPool = newCachedThreadPool.get();
    }

    public static void setNewFixedThreadPool(int i) {
        fixedThreadPool = newFixedThreadPool.apply(i);
    }

    public static void setNewScheduledThreadPool(int i) {
        scheduledThreadPool = newScheduledThreadPool.apply(i);
    }

    public static void setNewSingleThreadExecutor() {
        singleThreadExecutor = newSingleThreadExecutor.get();
    }

    public <T> String setCallable(Callable<T> callable) {
        String uuid = null;
        if(Objects.nonNull(callable)){
            uuid = UUID.randomUUID().toString() + System.currentTimeMillis();
            Future<T> submit = scheduledThreadPool.submit(callable);
            msf.put(uuid,submit);
        }
        return uuid;
    }

    public void setRunnable(Runnable runnable) {
        if(Objects.nonNull(runnable)){ scheduledThreadPool.execute(runnable); return;}
        SysFun.loginfo.accept("添加无返回值的数据值失败");
    }


}
