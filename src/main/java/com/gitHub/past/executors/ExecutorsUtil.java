package com.gitHub.past.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;

public class ExecutorsUtil {

    //newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
    public static Supplier<ExecutorService> newCachedThreadPool = Executors::newCachedThreadPool;
    //newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
    public static Function<Integer,ExecutorService> newFixedThreadPool = Executors::newFixedThreadPool;
    //newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
    public static Function<Integer,ExecutorService> newScheduledThreadPool = Executors::newScheduledThreadPool;
    //newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
    public static Supplier<ExecutorService> newSingleThreadExecutor = Executors::newSingleThreadExecutor;

}
