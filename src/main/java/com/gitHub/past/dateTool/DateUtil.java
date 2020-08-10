package com.gitHub.past.dateTool;

import com.gitHub.past.Invariable;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateUtil {

    public static Function<String,DateTimeFormatter> dtf = DateTimeFormatter::ofPattern;

//    public static void  = (date1,date2)->{
//        String str1="2015-02-08 20:20:20";
//        String str2="2015-01-08 10:10:10";
//        int res=str1.compareTo(str2);
//        if(res>0)
//            System.out.println("str1>str2");
//        else if(res==0)
//            System.out.println("str1=str2");
//        else
//            System.out.println("str1<str2");
//        }
//    };



    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();

        Logger.getAnonymousLogger().log(Level.INFO,LocalDate.now().toString());
        Logger.getAnonymousLogger().log(Level.INFO,LocalDateTime.now().toString());
        Logger.getAnonymousLogger().log(Level.INFO,LocalTime.now().toString());
        Logger.getAnonymousLogger().log(Level.INFO,Instant.now().toString());
        Logger.getAnonymousLogger().log(Level.INFO,ZonedDateTime.now().toString());
//        System.out.println(localDate.getDayOfMonth());//一个月的第几天
//        System.out.println(localDate.getDayOfYear());//一年的第几天
//        System.out.println(localDate.getMonthValue());//是哪一个月
//        System.out.println(localDate.getYear());//是哪一年
//        System.out.println(localDate.getDayOfWeek());//是周几
    }
}
