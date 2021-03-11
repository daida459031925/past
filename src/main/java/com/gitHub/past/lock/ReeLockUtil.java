package com.gitHub.past.lock;

import com.gitHub.past.common.SysFun;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * new FairSync()     公平锁
 * new NonfairSync()  非公平锁
 *
 *   方法名	描述
 *   protected boolean isHeldExclusively()	该线程是否正在独占资源。只有用到Condition才需要去实现它。
 *   protected boolean tryAcquire(int arg)	独占方式。arg为获取锁的次数，尝试获取资源，成功则返回True，失败则返回False。
 *   protected boolean tryRelease(int arg)	独占方式。arg为释放锁的次数，尝试释放资源，成功则返回True，失败则返回False。
 *   protected int tryAcquireShared(int arg)	共享方式。arg为获取锁的次数，尝试获取资源。负数表示失败；0表示成功，但没有剩余可用资源；正数表示成功，且有剩余资源。
 *   protected boolean tryReleaseShared(int arg)	共享方式。arg为释放锁的次数，尝试释放资源，如果释放后允许唤醒后续等待结点返回True，否则返回False。
 *
 *
 *   同步工具	同步工具与AQS的关联
 *   ReentrantLock	使用AQS保存锁重复持有的次数。当一个线程获取锁时，ReentrantLock记录当前获得锁的线程标识，用于检测是否重复获取，以及错误线程试图解锁操作时异常情况的处理。
 *   Semaphore	使用AQS同步状态来保存信号量的当前计数。tryRelease会增加计数，acquireShared会减少计数。
 *   CountDownLatch	使用AQS同步状态来表示计数。计数为0时，所有的Acquire操作（CountDownLatch的await方法）才可以通过。
 *   ReentrantReadWriteLock	使用AQS同步状态中的16位保存写锁持有的次数，剩下的16位用于保存读锁的持有次数。
 *   ThreadPoolExecutor	Worker利用AQS同步状态实现对独占线程变量的设置（tryAcquire和tryRelease）。
 */
public class ReeLockUtil extends ReentrantLock {

    /**
     * Creates an instance of {@code ReentrantLock}.
     * This is equivalent to using {@code ReentrantLock(false)}.
     */
    public ReeLockUtil() {
    }

    /**
     * Creates an instance of {@code ReentrantLock} with the
     * given fairness policy.
     *
     * @param fair {@code true} if this lock should use a fair ordering policy
     */
    public ReeLockUtil(boolean fair) {
        super(fair);
    }

    public <T> T tryRun(Supplier<T> supplier){
        T t = null;
        try {
            this.lock();
            t = supplier.get();
        } finally {
            this.unlock();
        }
        return t;
    }


    public static void main(String[] args) {
        ReeLockUtil reeLockUtil = new ReeLockUtil(true);
        SysFun.loginfo.accept(reeLockUtil.tryRun(()->"r"));
    }

}
