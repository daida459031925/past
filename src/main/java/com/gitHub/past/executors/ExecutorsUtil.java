package com.gitHub.past.executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
//@Scope("singleton") //spring boot 中定义单例模式

public class ExecutorsUtil {

    //newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
    public static Supplier<ExecutorService> newCachedThreadPool = Executors::newCachedThreadPool;
    //newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
    public static Function<Integer,ExecutorService> newFixedThreadPool = Executors::newFixedThreadPool;
    //newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
    public static Function<Integer,ExecutorService> newScheduledThreadPool = Executors::newScheduledThreadPool;
    //newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
    public static Supplier<ExecutorService> newSingleThreadExecutor = Executors::newSingleThreadExecutor;

    //spring中Constructor、@Autowired、@PostConstruct的顺序
    //Constructor >> @Autowired >> @PostConstruct
    //@PreDestroy是通过org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor.
    // postProcessBeforeDestruction(Object, String)被调用（InitDestroyAnnotationBeanPostProcessor实现了该接口），该方法的说明如下：
    //Apply this BeanPostProcessor to the given bean instance before its destruction. Can invoke custom destruction callbacks.
    //Like DisposableBean's destroy and a custom destroy method, this callback just applies to singleton beans in the factory (including inner beans).
    @PostConstruct//项目启动时候加载一次
    void init(){

    }

    @PreDestroy //结束时候执行一次
    //是先调用DestructionAwareBeanPostProcessor的postProcessBeforeDestruction(@PreDestroy标记的方法被调用)，再是DisposableBean的destory方法，最后是自定义销毁方法；
    void exit(){

    }
}
