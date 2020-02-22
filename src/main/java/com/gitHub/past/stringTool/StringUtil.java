package com.gitHub.past.stringTool;

import java.util.Optional;
import java.util.function.Predicate;

public class StringUtil {

    public static Predicate<String> StringLengthNull = (x) -> Optional.ofNullable(x).orElse("").trim().length()>0;

    //根据传入的对象返还一个线程安全的对象 根据java8的lambda生成
//    public static <T> T getSafetyBean(T t){
//        return ThreadLocal.withInitial(()-> t).get();
//    }
}
