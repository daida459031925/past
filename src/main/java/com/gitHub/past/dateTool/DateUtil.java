package com.gitHub.past.dateTool;

import com.gitHub.past.Invariable;
import com.gitHub.past.calculation.BigDecimalUtil;
import com.gitHub.past.common.BiPredicateDouble;
import com.gitHub.past.common.CiFunction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateUtil {

    /**
     * 自定义格式类型定义
     */
    public static Function<String, DateTimeFormatter> dtf = DateTimeFormatter::ofPattern;
    public static Function<String, SimpleDateFormat> sdf = SimpleDateFormat::new;

    /**
     * 格式类型定义
     */
    public static DateTimeFormatter dtfY_M_D_H_M_S = dtf.apply(Invariable.YYYY_MM_DD_HH_MM_SS.toString());
    public static DateTimeFormatter dtfY_M_D = dtf.apply(Invariable.YYYY_MM_DD.toString());
    public static DateTimeFormatter dtfYMDHMS = dtf.apply(Invariable.YYYYMMDDHHMMSS.toString());
    public static DateTimeFormatter dtfYMD = dtf.apply(Invariable.YYYYMMDD.toString());
    public static DateTimeFormatter dtfH_M_S = dtf.apply(Invariable.HH_MM_SS.toString());
    public static DateTimeFormatter dtfH_M = dtf.apply(Invariable.HH_MM.toString());

    public static SimpleDateFormat sdfY_M_D_H_M_S = sdf.apply(Invariable.YYYY_MM_DD_HH_MM_SS.toString());
    public static SimpleDateFormat sdfY_M_D = sdf.apply(Invariable.YYYY_MM_DD.toString());
    public static SimpleDateFormat sdfYMDHMS = sdf.apply(Invariable.YYYYMMDDHHMMSS.toString());
    public static SimpleDateFormat sdfYMD = sdf.apply(Invariable.YYYYMMDD.toString());
    public static SimpleDateFormat sdfH_M_S = sdf.apply(Invariable.HH_MM_SS.toString());
    public static SimpleDateFormat sdfH_M = sdf.apply(Invariable.HH_MM.toString());

    /**
     * 获取当前时间
     */
    public static DaidaLocalDateTime getDaidaDate = new DaidaLocalDateTime();
    public static LocalDateTime getThisDate = getDaidaDate.getThisDate();

    /**
     * 获取当前时间字符串
     */
    public static Function<String, String> getNowTime = (string) -> getThisDate.format(dtf.apply(string));

    /**
     * 指定时间字符串换成LocalDateTime
     */
    //只能把他用作dtfY_M_D_H_M_S
    private static Function<String, LocalDateTime> getLdtParseStr = (string) -> LocalDateTime.parse(string, dtfY_M_D_H_M_S);
    public static Function<Date, LocalDateTime> getLdtParseDate = (date) -> getSdfParseldt(sdfY_M_D_H_M_S.format(date), sdfY_M_D_H_M_S);
    public static BiFunction<LocalDate, LocalTime, LocalDateTime> getLdtDateTime = LocalDateTime::of;

    /**
     * 通用时间解决方案
     */
    public static LocalDateTime getSdfParseldt(String string, SimpleDateFormat sdf) {
        try {
            return getLdtParseStr.apply(sdfY_M_D_H_M_S.format(sdf.parse(string)));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getSdfParseDate(String string, SimpleDateFormat sdf) {
        try {
            return sdf.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获得指定时间的毫秒数
     */
    public static BiFunction<LocalDateTime, Integer, Long> getMillisecondAndInt = (localDateTime, integer) -> localDateTime.toInstant(ZoneOffset.ofHours(integer)).toEpochMilli();
    public static Function<LocalDateTime, Long> getMillisecond = (localDateTime) -> getMillisecondAndInt.apply(localDateTime, 8);


    /**
     * 判断两个时间是否存在传入的时间交集
     * 【startTime - endTime】
     * ⬆
     * 【startTime - endTime】
     */
    public static BiPredicateDouble<LocalDateTime, LocalDateTime, LocalDateTime, LocalDateTime, Boolean> isTime = (startTime1, endTime1, startTime2, endTime2, equal) -> {
        //isAfter和isBefore 貌似在我的思想里面不太好用 不能直接判断相等情况
        Long start1 = DateUtil.getMillisecond.apply(startTime1);
        Long end1 = DateUtil.getMillisecond.apply(endTime1);
        Long start2 = DateUtil.getMillisecond.apply(startTime2);
        Long end2 = DateUtil.getMillisecond.apply(endTime2);
        //实际上就是开始时间小于结束时间并且是第一个的开始和第二个的结束adn第二个的开始和第一个的结束
        //数据库中也可以使用这种时间计算
        //                                                                  08:00   20:00    19:00    18:00
        //                                                                  08:00   20:00    17:00    18:00
        return ((equal && (start1.equals(end2) || start2.equals(end1))) || (start1 < end2 && start2 < end1));
    };

    public static boolean isTime(LocalDateTime startTime1, LocalDateTime endTime1, LocalDateTime startTime2, LocalDateTime endTime2) {
        return isTime.test(startTime1, endTime1, startTime2, endTime2, false);
    }

    /**
     * 根据LocalDateTime 来返还需要添加的年月日十分秒数组
     */
    public static Function<LocalDateTime, int[]> getTimesInt = (ldt) -> {
        int[] ints = new int[6];
        Optional.ofNullable(ldt).ifPresent(e -> {
            ints[0] = e.getYear();//年
            ints[1] = e.getMonthValue();//月
            ints[2] = e.getDayOfMonth();//日
            ints[3] = e.getHour();//时
            ints[4] = e.getMinute();//分
            ints[5] = e.getSecond();//秒
        });
        return ints;
    };

    /**
     * 这两个计算出来 不包含头或者尾
     * 举例  2020-01-01  到 2020-01-02   返还数据 为 1  根据具体情况是否调用getAllDateMax
     * 直接获取两个日期相差的天数， 内部代码需要优化   需要解决内存溢出问题
     */
    public static BiFunction<LocalDate, LocalDate, Long> getDateLessDay = (startLd, endLd) -> endLd.toEpochDay() - startLd.toEpochDay();

    /**
     * 这两个计算出来 不包含头或者尾
     * 举例  2020-01-01  到 2020-01-02   返还数据 为 2
     * 直接获取两个日期相差的天数， 内部代码需要优化   需要解决内存溢出问题
     */
    public static BiFunction<LocalDate, LocalDate, Long> getAllDateMax = (startLd, endLd) -> getDateLessDay.apply(startLd,endLd) + 1;
    /**
     * 获取开始日期和结束日期之间间隔的每一天
     * 含头含尾
     * 在数据量大时候会出现内存溢出
     */
    public static BiFunction<LocalDate, LocalDate, Set<LocalDate>> getAllDate = (startLd, endLd) -> {
        Set<LocalDate> localDateSet = new LinkedHashSet<>();
        boolean tf = true;
        int i = 0;
        do {
            localDateSet.add(startLd.plusDays(i));
            if (startLd.equals(endLd)) {
                tf = false;
            } else {
                i++;
            }
        } while (tf);

        return localDateSet;
    };

    /**
     * 获取开始时间和结束时间之间间隔的每个时间段
     * 含头含尾    不足舍弃
     * int 按照多少分钟分开
     *
     * 在数据量大时候会出现内存溢出
     */
    public static CiFunction<LocalTime, LocalTime, Integer, Set<LocalTime>> getAllTime = (startLt, endLt, interval) -> {
        Set<LocalTime> localTimeSet = new LinkedHashSet<>();
        boolean tf = true;

        if (interval == null || interval == 0) {
            //直接返还，无法切割
            return localTimeSet;
        }

        if (interval < 0) {
            interval = BigDecimalUtil.abs.apply(interval.toString()).intValue();
        }

        LocalDateTime of = LocalDateTime.of(LocalDate.now(), startLt);

        localTimeSet.add(startLt);

        do {
            LocalDateTime end = LocalDateTime.of(LocalDate.now(), endLt);

            of = of.plusMinutes(interval);

            if (of.compareTo(end) > 0) {
                tf = false;
            } else {
                localTimeSet.add(of.toLocalTime());
            }
        } while (tf);

        return localTimeSet;
    };

    /**
     * 获取开始时间和结束时间之间间隔的每个时间段
     * 含头含尾    不足舍弃
     * int 按照多少秒分开
     *
     * 在数据量大时候会出现内存溢出
     */
    public static CiFunction<LocalDateTime, LocalDateTime, Integer, Set<LocalDateTime>> getAllDateTime = (startLt, endLt, interval) -> {
        Set<LocalDateTime> localTimeSet = new LinkedHashSet<>();
        boolean tf = true;

        if (interval == null || interval == 0) {
            //直接返还，无法切割
            return localTimeSet;
        }

        if (interval < 0) {
            interval = BigDecimalUtil.abs.apply(interval.toString()).intValue();
        }

        localTimeSet.add(startLt);

        do {

            startLt = startLt.plusSeconds(interval);

            if (startLt.compareTo(endLt) > 0) {
                tf = false;
            } else {
                localTimeSet.add(startLt);
            }
        } while (tf);

        return localTimeSet;
    };

    /**
     * 获取两个时间之间相差的时间
     * 1秒等于   1000毫秒
     * 1毫秒等于 1000微秒
     * 1微秒等于 1000纳秒
     * 在这个Duration中 存储的是 秒和纳秒
     * toString 需要格式化 PT8802H39M34.2496708S
     */
    public static BiFunction<LocalDateTime, LocalDateTime, Duration> duration = Duration::between;

//    public static BiFunction<String,DateTimeFormatter,LocalDateTime> parse1 = (s1,s2)->;


//    public static Function<String,String> getDayOfMonth = LocalDateTime.now().getDayOfMonth();

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

    /**
     * 获取当前周的周一是哪一天
     */
    public static Function<LocalDateTime, LocalDateTime> monday = (todayOfLastWeek) ->
            todayOfLastWeek.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(1);

    /**
     * 获取当前周的周日是哪一天
     */
    public static Function<LocalDateTime, LocalDateTime> sunday = (todayOfLastWeek) ->
            todayOfLastWeek.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).minusDays(1);

    /**
     * 获取当前时间转换成 dd HH:mm:ss
     */
    public static String toTime(Long l,String... string){
        string = Optional.ofNullable(string).orElse(new String[]{"d", "h", "m", "s"});
        for(int i =0;i < string.length;i++){
            String d = "";
            switch (i){
                case 0: d = "d";break;
                case 1: d = "h";break;
                case 2: d = "m";break;
                case 3: d = "s";break;
            }
            String finalD = d;
            string[i] = Optional.ofNullable(string[i]).map(e->{
                if(e.isEmpty()){
                    return finalD;
                }
                return e;
            }).orElse(d);
        }

        long s = l/1000;
        //天数计算
        long days = (s)/(24*3600);
        //小时计算
        long hours = (s)%(24*3600)/3600;
        //分钟计算
        long minutes = (s)%3600/60;
        //秒计算
        long second = (s)%60;
        StringBuilder times = new StringBuilder();
        if(days != 0){
            times.append(days).append("d");
        }
        times.append(hours).append("h").append(minutes).append("m").append(second).append("s");
        return times.toString();
    }

    /**
     * 传入自己设计的显示时间字符串 返还毫秒数
     */
    public static Long toTime(String string){
        String s = string.replaceAll("d", ":").replaceAll("h", ":")
                .replaceAll("m", ":").replaceAll("s", ":");
        String[] split = s.split(":");
        int length = split.length;
        long d = 0l;

        for(int i = length;i > 0 ;i--){
            if(length == i){
                //计算秒
                d += Integer.parseInt(split[i])*1000;
            }else if(length -1 == i){
                //计算分钟
                d += Integer.parseInt(split[i])*60*1000;
            }else if(length -2 == i){
                //计算小时
                d += Integer.parseInt(split[i])*60*60*1000;
            }else if(length -3 == i){
                //计算天
                d += Integer.parseInt(split[i])*24*60*60*1000;
            }

        }

        return d;
    }


    //获取本周周一和周日
//    LocalDate today = LocalDate.now();
//    LocalDate monday = today.with(TemporalAdjusters.previousOrSame( DayOfWeek.MONDAY));
//    LocalDate sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY ));

    public static void main(String[] args) {
//        getAllTime.apply(LocalTime.of(0,0,0),LocalTime.of(23,59,59),60);
        getAllDateTime.apply(LocalDateTime.of(2020, 10, 14, 0, 0, 0),
                LocalDateTime.of(2020, 10, 15, 23, 59, 59), 600);


        LocalDateTime now = LocalDateTime.now();
        DaidaLocalDateTime s = new DaidaLocalDateTime(now);
        s.setEndDate(LocalDateTime.of(2021, 1, 3, 0, 0, 0));
        s.addTime("4000", "20", false);
        s.updTime(1, 2, 3, 4, 5, 6, 7);
        Logger.getAnonymousLogger().log(Level.INFO, LocalDate.now().toString());
        Logger.getAnonymousLogger().log(Level.INFO, LocalDateTime.now().toString());
        Logger.getAnonymousLogger().log(Level.INFO, LocalTime.now().toString());
        Logger.getAnonymousLogger().log(Level.INFO, Instant.now().toString());
        Logger.getAnonymousLogger().log(Level.INFO, ZonedDateTime.now().toString());

//        LocalDateTime now = LocalDateTime.now();
        System.out.println("计算两个时间的差：");
        LocalDateTime end = LocalDateTime.of(2023,03,19,03,03,03);
        Duration duration = Duration.between(now, end);
        long days = duration.toDays(); //相差的天数
        long hours = duration.toHours();//相差的小时数
        long minutes = duration.toMinutes();//相差的分钟数
        long millis = duration.toMillis();//相差毫秒数
        long nanos = duration.toNanos();//相差的纳秒数
        System.out.println(now);
        System.out.println(end);
        System.out.println(days);
        System.out.println(hours);
        System.out.println(minutes);
        System.out.println(millis);
        System.out.println(nanos);
        System.out.println(DateUtil.duration.apply(now,end));
    }
}
