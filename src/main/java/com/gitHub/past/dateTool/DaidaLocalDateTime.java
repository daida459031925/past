package com.gitHub.past.dateTool;

import com.gitHub.past.common.DefOptional;

import java.time.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class DaidaLocalDateTime {
    /**
     * 获取当前时间
     */
    private LocalDateTime getThisDate;

    private DayOfWeek dayOfWeek;

    /**
     * 时间查计算类
     */
    private Duration duration;
    /**
     * 结束时间
     */
    private LocalDateTime endDate;

    public DaidaLocalDateTime() {
        this.getThisDate = LocalDateTime.now();
        this.dayOfWeek = getThisDate.getDayOfWeek();
    }

    public DaidaLocalDateTime(LocalDateTime getThisDate) {
        this.getThisDate = getThisDate;
    }

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

    public DaidaLocalDateTime setEndDate(Date date) {
        this.endDate = DateUtil.getLdtParseDate.apply(date);
        //对时间计算类赋值
        duration = Duration.between(getThisDate, endDate);
        return this;
    }

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

    public LocalDateTime getThisDate() {
        return getThisDate;
    }

    public DayOfWeek getWeek() {
        return dayOfWeek;
    }

    /**
     * 相差的天数
     */
    public long getToDays() {
        return duration.toDays();
    }

    /**
     * 相差的小时数
     */
    public long getToHours() {
        return duration.toHours();
    }

    /**
     * 相差的分钟数
     */
    public long getToMinutes() {
        return duration.toMinutes();
    }

    /**
     * 相差毫秒数
     */
    public long getToMillis() {
        return duration.toMillis();
    }

    /**
     * 相差的纳秒数
     */
    public long getToNanos() {
        return duration.toNanos();
    }

    /**
     * 判断当前时间是否在你传入的时间段之内
     * 【startTime - endTime】
     *            ⬆
     *      需要判断的时间
     */
    public boolean isTime(LocalDateTime startTime, LocalDateTime endTime, boolean equal) {
        //isAfter和isBefore 貌似在我的思想里面不太好用 不能直接判断相等情况
        Long start = DateUtil.getMillisecond.apply(startTime);
        Long end = DateUtil.getMillisecond.apply(endTime);
        Long thisTime = DateUtil.getMillisecond.apply(getThisDate);
        return ((equal && (start.equals(thisTime) || end.equals(thisTime))) || (start < thisTime && thisTime < end));
    }

    public boolean isTime(LocalDateTime startTime, LocalDateTime endTime) {
        return isTime(startTime, endTime, false);
    }

    /**
     * 根据当前getThisDate 时间来计算未来时间或过去时间
     */
    public DaidaLocalDateTime updTime(long Year,long Month,long Day,long Hour,long Minute,long Second) {
        return new DaidaLocalDateTime(DateUtil.getLdtDateTime.apply(getThisDate.toLocalDate().plusYears(Year).plusMonths(Month).plusDays(Day),
                getThisDate.toLocalTime().plusHours(Hour).plusMinutes(Minute).plusSeconds(Second)));
    }

    /**
     *
     * @param longs long Year,long Month,long Day,long Hour,long Minute,long Second
     * 最多支持6个参数 添加顺序为 年 月 日 时 分 秒 目前不支持跨某个点传入
     */
    public DaidaLocalDateTime updTime(long... longs) {
        //数组拷贝
        long[] log = longs;
        if(longs.length!=6) log = Arrays.copyOf(longs,6);
//        System.arraycopy(原数组名，起始下标，新数组名，起始下标，复制长度);
        return updTime(log[0],log[1],log[2],log[3],log[4],log[5]);
    }

    public DaidaLocalDateTime updTime(int... los) {
        int[] ints = los;
        if(los.length!=6) ints = Arrays.copyOf(los,6);
        return updTime(ints[0],ints[1],ints[2],ints[3],ints[4],ints[5]);
    }
}
