package com.gitHub.past.stringTool;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class StringUtil {

    /**
     * 传入一个字符串查看是否为null 或者为"" 但是不判断是否存在“ ”这个情况
     */
    public static Predicate<String> isEmpty = (String string) -> Optional.ofNullable(string).orElse("").trim().length()>0;
    /**
     * 传入一个字符串查看是否为null 或者为"" 判断是否存在“ ”这个情况 将“ ”左右两边的进行过滤
     * 但是“A B”不算
     */
    public static Predicate<String> isEmptyLRtrim = (String string) -> Optional.ofNullable(string).map(e->e.trim()).orElse("").trim().length()>0;

    /**
     * 将对象使用String.valueOf 防止对象出现null的情况 如果传入的为null 那么返还"null"
     */
    private Function<Object,String> toString = (Object obj)->String.valueOf(obj);

    //根据传入的对象返还一个线程安全的对象 根据java8的lambda生成
//    public static <T> T getSafetyBean(T t){
//        return ThreadLocal.withInitial(()-> t).get();
//    }

    public static void main(String[] args) {
        String s = null;
        s.isEmpty();
    }
}
