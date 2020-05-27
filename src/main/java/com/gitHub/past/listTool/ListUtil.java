package com.gitHub.past.listTool;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

//普通这样写是没有任何问题的但是对象的化如何对比
public class ListUtil<T> {


    //交集
    public BiFunction<List<T>,List<T>,List<T>> jiaoJi = (List<T> i1, List<T> i2)->i1.stream().filter(num -> i2.contains(num))
            .collect(Collectors.toList());;

    //差集
    public BiFunction<List<T>,List<T>,List<T>> aJianb = (List<T> i1, List<T> i2)->i1.stream().filter(num -> !i2.contains(num))
             .collect(Collectors.toList());

    //并集  不去重
    public BiFunction<List<T>,List<T>,List<T>> bingJiNoQuchong = (List<T> i1, List<T> i2)->{i1.addAll(i2);return i1;};

    //并集  去重
    public BiFunction<List<T>,List<T>,List<T>> bingJiQuchong = (List<T> i1, List<T> i2)->{
        i1.addAll(i2);
        List<T> collect4 = i1.stream().distinct().collect(Collectors.toList());
        return collect4;
    };

}
