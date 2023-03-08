package com.gitHub.past.web;


import com.gitHub.past.annotate.initValue.InitValue;
import io.vavr.Function2;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.FutureTask;

public class Page<T> {

    private long offset = 0;
    private long limit = Long.MAX_VALUE;
    private long max = Long.MAX_VALUE;

    private Function2<Long,Long,FutureTask<T>> futTask;

    /**
     *
     * @param offset    设置从第几条数据开始查询
     * @param limit     设置每页查询多少条
     * @param max       设置最大查询数据个数
     * @param futTask   设置执行的方法 第一个参数时offset，第二个参数时limit
     *
     * @exception
     * mysql写法：
     * SELECT * FROM user2
     * LIMIT (#{pageNum} - 1) * #{pageSize}, #{pageSize}
     *
     * oracle写法：
     * SELECT * FROM user2
     * OFFSET (#{pageNum} - 1) * #{pageSize} ROWS FETCH NEXT #{pageSize} ROWS ONLY
     */
    public Page(long offset, long limit, long max,Function2<Long,Long,FutureTask<T>> futTask) {
        this.offset = offset;
        this.limit = limit;
        this.max = max;
        this.futTask = futTask;
    }

    /**
     *
     * @param pageNum
     * @param pageSize
     * @param max
     * @param futTask
     */
    public Page(@InitValue(value = "1") Long pageNum, Long pageSize, Long max, Function2<Long,Long,FutureTask<T>> futTask) {
        this.offset = (pageNum-1)*pageSize;
        this.limit = pageSize;
        this.max = max;
        this.futTask = futTask;
    }

    public List<FutureTask<T>> getListTask(){
        List<FutureTask<T>> list = new LinkedList<>();
        for (long i = offset; i < max; i+=limit) {
            list.add(futTask.apply(i,this.limit));
        }
        return list;
    }
}
