package com.gitHub.past.log;

import com.gitHub.past.stringTool.StringUtil;
import com.gitHub.past.web.Result;

import java.util.function.Consumer;

public class Log {

    private static cn.hutool.log.Log log = cn.hutool.log.Log.get();

    public static void info(String str){
        log.info(str);
    }

    public static void err(String str){
        log.error(str);
    }

    public static void debug(String str){
        log.debug(str);
    }

    public static void info(String str, Object... arguments){
        log.info(str,arguments);
    }

    public static void err(String str, Object... arguments){
        log.error(str,arguments);
    }

    public static void debug(String str, Object... arguments){
        log.debug(str,arguments);
    }

    public static void info(Exception e){
        log.info(e);
    }

    public static void err(Exception e){
        log.error(e);
    }

    public static void debug(Exception e){
        log.debug(e);
    }

    //一个前置方法 一个后置方法一个执行方法打印执行时间
    private static String time(Consumer<Object> consumer){
        Result<Long> exec = Result.OK(0L).setFunction((obj) -> {
            consumer.accept("");
            return "共耗时：" + (System.currentTimeMillis() - Long.parseLong(String.valueOf(obj))) ;
        },"执行异常").exec(System.currentTimeMillis());
        if (exec.getState().equals(Result.OK)){
            return StringUtil.getTrimString(exec.getData()).toString();
        }
        return exec.getMsg();
    }

    public static void info(Consumer<Object> consumer){
        log.info(time(consumer));
    }

    public static void err(Consumer<Object> consumer){
        log.error(time(consumer));
    }

    public static void debug(Consumer<Object> consumer){
        log.debug(time(consumer));
    }
}
