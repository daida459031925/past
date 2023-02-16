package com.gitHub.past.log;

import java.util.function.Supplier;

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
}
