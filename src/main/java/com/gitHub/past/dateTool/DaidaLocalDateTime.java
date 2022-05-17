package com.gitHub.past.dateTool;


import com.gitHub.past.Invariable;
import com.gitHub.past.calculation.BigDecimalUtil;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class DaidaLocalDateTime {
    /**
     * 获取当前时间
     */
    private LocalDateTime getThisDate;
    /**
     * 获得getThisDate 时间的这周的数据
     */
    private DayOfWeek dayOfWeek;

    /**
     * 时间查计算类
     */
    private Duration duration;
    /**
     * 结束时间
     */
    private LocalDateTime endDate;

    /**
     * 初始化时候拿到当前时间和当前周的数据
     */
    public DaidaLocalDateTime() {
        this.getThisDate = LocalDateTime.now();
        this.dayOfWeek = getThisDate.getDayOfWeek();
    }

    /**
     * 指定时间 LocalDateTime模式 初始化
     */
    public DaidaLocalDateTime(LocalDateTime getThisDate) {
        this.getThisDate = getThisDate;
    }

    /**
     * 指定时间 Date模式 初始化
     */
    public DaidaLocalDateTime(Date date) {
        this.getThisDate = DateUtil.getLdtParseDate.apply(date);
    }

    /**
     * 设置结束时间这样就可以调用Duration（时间差计算类）
     *
     * @param endDate
     */
    public DaidaLocalDateTime setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
        //对时间计算类赋值
        duration = Duration.between(getThisDate, endDate);
        return this;
    }

    /**
     * 设置结束时间这样就可以调用Duration（时间差计算类） date模式
     */
    public DaidaLocalDateTime setEndDate(Date date) {
        this.endDate = DateUtil.getLdtParseDate.apply(date);
        //对时间计算类赋值
        duration = Duration.between(getThisDate, endDate);
        return this;
    }

    /**
     * 获取结束时间
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * 初始化的时候将开始时间和结束时间赋值
     *
     * @param getThisDate
     * @param endDate
     */
    public DaidaLocalDateTime(LocalDateTime getThisDate, LocalDateTime endDate) {
        this.getThisDate = getThisDate;
        this.endDate = endDate;
        //对时间计算类赋值
        duration = Duration.between(getThisDate, endDate);
    }

    /**
     * 一个月的第几天
     *
     * @return
     */
    public long getDayOfMonth() {
        return getThisDate.getDayOfMonth();
    }

    /**
     * 一年的第几天
     *
     * @return
     */
    public long getDayOfYear() {
        return getThisDate.getDayOfYear();
    }

    /**
     * 是哪一个月
     *
     * @return
     */
    public long getMonthValue() {
        return getThisDate.getMonthValue();
    }

    /**
     * 是哪一年
     *
     * @return
     */
    public long getYear() {
        return getThisDate.getYear();
    }

    /**
     * 源码上获得当前这个日期是周几 都进行了加1
     * 周一
     * 那么说明  真实的日期是0，1，2，3，4，5，6  这个七个数字
     * 是周几
     *
     * @return
     */
    public long getDayOfWeek() {
        return dayOfWeek.getValue();
    }

    /**
     * 检查某一年是否为闰年
     *
     * @return
     */
    public boolean isLeapYear() {
        return getThisDate.toLocalDate().isLeapYear();
    }

    /**
     * 获得当前时间的LocalDateTime
     */
    public LocalDateTime getThisDate() {
        return getThisDate;
    }

    /**
     * 获得当前时间的LocalDateTime 的周
     */
    public DayOfWeek getWeek() {
        return dayOfWeek;
    }

    /**
     * 相差的天数  计算方式24小时计算方式
     */
    public long getToDays() {
        return duration.toDays();
    }

    /**
     * 相差的小时数 计算方式24小时计算方式
     */
    public long getToHours() {
        return duration.toHours();
    }

    /**
     * 相差的分钟数 计算方式24小时计算方式
     */
    public long getToMinutes() {
        return duration.toMinutes();
    }

    /**
     * 相差毫秒数 计算方式24小时计算方式
     */
    public long getToMillis() {
        return duration.toMillis();
    }

    /**
     * 相差的纳秒数 计算方式24小时计算方式
     */
    public long getToNanos() {
        return duration.toNanos();
    }

    /**
     * 判断当前时间是否在你传入的时间段之内
     * 【startTime - endTime】
     * ⬆
     * 需要判断的时间
     */
    public boolean isTime(LocalDateTime startTime, LocalDateTime endTime, boolean equal) {
        //isAfter和isBefore 貌似在我的思想里面不太好用 不能直接判断相等情况
        Long start = DateUtil.getMillisecond.apply(startTime);
        Long end = DateUtil.getMillisecond.apply(endTime);
        Long thisTime = DateUtil.getMillisecond.apply(getThisDate);
        return ((equal && (start.equals(thisTime) || end.equals(thisTime))) || (start < thisTime && thisTime < end));
    }

    /**
     * 判断当前时间是否在你传入的时间段之内 不添加等号情况
     * 【startTime - endTime】
     * ⬆
     * 需要判断的时间
     */
    public boolean isTime(LocalDateTime startTime, LocalDateTime endTime) {
        return isTime(startTime, endTime, false);
    }

    /**
     * 根据当前getThisDate 时间来计算未来时间或过去时间
     */
    public DaidaLocalDateTime updTime(long Year, long Month, long Day, long Hour, long Minute, long Second) {
        return new DaidaLocalDateTime(DateUtil.getLdtDateTime.apply(getThisDate.toLocalDate().plusYears(Year).plusMonths(Month).plusDays(Day),
                getThisDate.toLocalTime().plusHours(Hour).plusMinutes(Minute).plusSeconds(Second)));
    }

    /**
     * @param longs long Year,long Month,long Day,long Hour,long Minute,long Second
     *              最多支持6个参数 添加顺序为 年 月 日 时 分 秒 目前不支持跨某个点传入
     */
    public DaidaLocalDateTime updTime(long... longs) {
        //数组拷贝
        long[] log = longs;
        if (longs.length != 6) log = Arrays.copyOf(longs, 6);
//        System.arraycopy(原数组名，起始下标，新数组名，起始下标，复制长度);
        return updTime(log[0], log[1], log[2], log[3], log[4], log[5]);
    }

    /**
     * @param los int Year,int Month,int Day,int Hour,int Minute,int Second
     *            最多支持6个参数 添加顺序为 年 月 日 时 分 秒 目前不支持跨某个点传入
     */
    public DaidaLocalDateTime updTime(int... los) {
        int[] ints = los;
        if (los.length != 6) ints = Arrays.copyOf(los, 6);
        return updTime(ints[0], ints[1], ints[2], ints[3], ints[4], ints[5]);
    }


    /**
     * 工具类需要把 “11:11”   和添加  某个单独时间的加减  添加进来 false 是添加  true 是减去
     * 默认处理数据“yyyy-MM-dd HH:mm:ss”
     */
    //官方原版只支持 年-月-日T十:分:秒.毫秒
    public DaidaLocalDateTime addTime(String dateString, String timeString) {
        return addTime(dateString, timeString, false);
    }

    /**
     * 工具类需要把 “11:11”   和添加  某个单独时间的加减  添加进来 false 是添加  true 是减去
     * 默认处理数据“yyyy-MM-dd HH:mm:ss”
     */
    public DaidaLocalDateTime addTime(String dateString, String timeString, boolean del) {
        int[] apply = DateUtil.getTimesInt.apply(null);
        setInts(dateString, apply, 0, del);
        setInts(timeString, apply, 3, del);

        return updTime(apply);
        //纳秒目前不需要
        //parse.getNano();
    }

    /**
     * 工具类需要把 “11:11”   和添加  某个单独时间的加减  添加进来 false 是添加  true 是减去
     * 默认处理数据“yyyy-MM-dd HH:mm:ss”
     */
    public DaidaLocalDateTime addYMD(String dateString, boolean del) {
        return addTime(dateString, null, del);
    }

    /**
     * 工具类需要把 “11:11”   和添加  某个单独时间的加减  添加进来 false 是添加  true 是减去
     * 默认处理数据“yyyy-MM-dd HH:mm:ss”
     */
    public DaidaLocalDateTime addHMS(String timeString, boolean del) {
        return addTime(null, timeString, del);
    }

    /**
     * 工具类需要把 “11:11”   和添加  某个单独时间的加减  添加进来 false 是添加  true 是减去
     * 默认处理数据“yyyy-MM-dd HH:mm:ss”
     */
    public DaidaLocalDateTime addYMD(String dateString) {
        return addYMD(dateString, false);
    }

    /**
     * 工具类需要把 “11:11”   和添加  某个单独时间的加减  添加进来 false 是添加  true 是减去
     * 默认处理数据“yyyy-MM-dd HH:mm:ss”
     */
    public DaidaLocalDateTime addHMS(String timeString) {
        return addHMS(timeString, false);
    }

    /**
     * 通用计算 将给定的 年月日 或者十分秒 根据 false 是添加  true 是减去 来返还对应的 int数组
     */
    private void setInts(String string, int[] apply, int i, boolean del) {
        if (Objects.isNull(string) || string.trim().isEmpty()) return;
        StringBuilder stringBuilder = new StringBuilder();
        int length = string.length();
        AtomicInteger setInt = new AtomicInteger(i);
        IntStream.range(0, length).forEach(e -> {
            String c = String.valueOf(string.charAt(e));
            if (Arrays.asList(Invariable.WHIPPTREE.toString(), Invariable.COLON.toString(), Invariable.SPACE.toString()).contains(c)) {
                push(stringBuilder, del, setInt, apply);
            } else {
                stringBuilder.append(c);
            }
        });

        //执行完毕在push一下
        if (stringBuilder.length() > 0) {
            push(stringBuilder, del, setInt, apply);
        }

    }

    /**
     * 将setInts 进行核心内容提出 防止写重复代码  仿照流的模式来 在最后一次push一下  吧所有数据进行更新
     */
    private void push(StringBuilder stringBuilder, boolean del, AtomicInteger setInt, int[] apply) {
        int i1 = Integer.parseInt(stringBuilder.toString());
        if (del) i1 = BigDecimalUtil.quFan.apply(i1);
        apply[setInt.get()] = i1;
        setInt.addAndGet(1);
        stringBuilder.delete(0, stringBuilder.length());
    }

    /**
     * 获取当前周的周一是哪一天
     */
    public LocalDateTime monday() {
        return DateUtil.monday.apply(getThisDate);
    }

    /**
     * 获取当前周的周日是哪一天
     */
    public LocalDateTime sunday() {
        return DateUtil.sunday.apply(getThisDate);
    }

}
