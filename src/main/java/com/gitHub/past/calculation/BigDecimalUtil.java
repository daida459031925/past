package com.gitHub.past.calculation;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.Function;

/***
 * 由于BigDecimal在使用中小数最好使用的都是字符串类型的所以这边只支持字符串
 * 和整型
 */
public class BigDecimalUtil {

    public static Function<String,BigDecimal> getBig = BigDecimal::new;

    //加法
    public static BiFunction<String,String,BigDecimal> add = (String s1, String s2) -> getBig.apply(s1).add(getBig.apply(s2));

    //减法
    public static BiFunction<String,String,BigDecimal> subtract = (String s1, String s2) -> getBig.apply(s1).subtract(getBig.apply(s2));

    //乘法
    public static BiFunction<String,String,BigDecimal> multiply = (String s1, String s2) -> getBig.apply(s1).multiply(getBig.apply(s2));

    //除法
    public static BiFunction<String,String,BigDecimal> divide = (String s1, String s2) -> getBig.apply(s1).divide(getBig.apply(s2));

    //取整 向上
    public static Function<String,BigDecimal> round_up = (String s1) -> getBig.apply(s1).setScale(0,BigDecimal.ROUND_UP);

    //取整 向下
    public static Function<String,BigDecimal> round_down = (String s1) -> getBig.apply(s1).setScale(0,BigDecimal.ROUND_DOWN);

}
