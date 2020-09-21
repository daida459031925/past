package com.gitHub.past.calculation;

import com.gitHub.past.common.SysFun;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/***
 * 由于BigDecimal在使用中小数最好使用的都是字符串类型的所以这边只支持字符串
 * 和整型
 */
public class BigDecimalUtil {

    /**
     * 示例 ：
     * "0.00":
     * "#.00": #.00 表示两位小数 #.0000四位小数
     * "%.2f": %.2f %. 表示 小数点前任意位数   2 表示两位小数 格式后的结果为f 表示浮点型
     *
     * style = "0.0";          定义要显示的数字的格式（这种方式会四舍五入）
     * style = "00000.000 kg"; 在格式后添加诸如单位等字符
     * style = "##000.000 kg"; 模式中的"#"表示如果该位存在字符，则显示字符，如果不存在，则不显示。
     * style = "-000.000";     模式中的"-"表示输出为负数，要放在最前面
     * style = "-0,000.0#";    模式中的","在数字中添加逗号，方便读数字
     * style = "0.00E000";     模式中的"E"表示输出为指数，"E"之前的字符串是底数的格式，E"之后的是字符串是指数的格式
     * style = "0.00%";        模式中的"%"表示乘以100并显示为百分数，要放在最后。
     * style = "0.00\u2030";   模式中的"\u2030"表示乘以1000并显示为千分数，要放在最后。
     *
     */
    public static Function<String, DecimalFormat> df = DecimalFormat::new;

    public static Function<String, BigDecimal> getBig = BigDecimal::new;

    //"3.40256010353E11"   =>  340256010353
    //有些项目可能会涉及到从Excel导入数据，但如果Excel里单元格类型为数值，但内容数据太长时（如银行账号）
    // ，导入时，会默认读取为科学计数法，用以下代码便轻松解决
    public static Function<BigDecimal, String> toPlainString = BigDecimal::toPlainString;

    //加法
    public static BiFunction<String, String, BigDecimal> add = (s1, s2) -> getBig.apply(s1).add(getBig.apply(s2));

    //减法
    public static BiFunction<String, String, BigDecimal> subtract = (s1, s2) -> getBig.apply(s1).subtract(getBig.apply(s2));

    //乘法
    public static BiFunction<String, String, BigDecimal> multiply = (s1, s2) -> getBig.apply(s1).multiply(getBig.apply(s2));

    //除法 我用一个BigDecimal对象除以divisor后的结果，并且要求这个结果保留有scale个小数位，roundingMode表示的就是保留模式是什么，是四舍五入啊还是其它的，你可以自己选！
    public static BiFunction<String, String, BigDecimal> divide = (s1, s2) -> getBig.apply(s1).divide(getBig.apply(s2),8,BigDecimal.ROUND_HALF_UP);

    public static BigDecimal divide(String bd1,String bd2,int scale,Integer type){
        if(scale < 0){ scale = 0; }
        if(Objects.isNull(type) || type > BigDecimal.ROUND_UNNECESSARY){type = BigDecimal.ROUND_HALF_UP;}//默认采取四舍五入
        return getBig.apply(bd1).divide(getBig.apply(bd2),scale,type);
    }

    //取整 向上 进位处理，2.32变成2.4
    public static Function<String, BigDecimal> round_up = (s1) -> getBig.apply(s1).setScale(1, BigDecimal.ROUND_UP);

    //取整 向下 直接删除多余的小数位，如2.35会变成2.3
    public static Function<String, BigDecimal> round_down = (s1) -> getBig.apply(s1).setScale(1, BigDecimal.ROUND_DOWN);

    //向上 四舍五入，2.35变成2.4
    public static Function<String, BigDecimal> round_half_up = (s1) -> getBig.apply(s1).setScale(1, BigDecimal.ROUND_HALF_UP);

    //向下 四舍五入，2.35变成2.3，如果是5则向下舍   这个方法好微妙   2.35=》2.3  2.351=》2.4  2.356=》2.36    简单来说看最后一位 和你需要截取位
    public static Function<String, BigDecimal> round_half_down = (s1) -> getBig.apply(s1).setScale(1, BigDecimal.ROUND_HALF_DOWN);

    //绝对值
    public static Function<String, BigDecimal> abs = (String s1) -> getBig.apply(s1).abs();

    //接近正无穷大的舍入   换句话说就是  往上加就对了
    public static Function<String, BigDecimal> round_ceiling = (s1) -> getBig.apply(s1).setScale(1, BigDecimal.ROUND_CEILING);

    //正数  直接砍掉末尾数  负数越变越小  且都不看四舍五入规则
    public static Function<String, BigDecimal> round_floor = (s1) -> getBig.apply(s1).setScale(1, BigDecimal.ROUND_FLOOR);

    //接近负无穷大的舍入，满足四舍五入规则
    public static Function<String, BigDecimal> round_half_even = (s1) -> getBig.apply(s1).setScale(1, BigDecimal.ROUND_HALF_EVEN);

    //对于一般add、subtract、multiply方法的小数位格式化如下：
    //BigDecimal mData = new BigDecimal("9.655").setScale(2, BigDecimal.ROUND_HALF_UP);
    //        System.out.println("mData=" + mData);
    //----结果：----- mData=9.66



    public static void main(String[] args) {
        SysFun.sysPrintln.accept(round_ceiling.apply("2.341"));
        SysFun.sysPrintln.accept(round_floor.apply("-2.311"));
        SysFun.sysPrintln.accept(round_floor.apply("2.391"));
        SysFun.sysPrintln.accept(round_half_even.apply("-2.35"));
        SysFun.sysPrintln.accept(round_half_even.apply("2.35"));


        //这个没研究柑桔是计算金额的
        NumberFormat currency = NumberFormat.getCurrencyInstance(); //建立货币格式化引用
        NumberFormat percent = NumberFormat.getPercentInstance();  //建立百分比格式化引用
        percent.setMaximumFractionDigits(3); //百分比小数点最多3位

        BigDecimal loanAmount = new BigDecimal("150.48"); //贷款金额
        BigDecimal interestRate = new BigDecimal("0.008"); //利率
        BigDecimal interest = loanAmount.multiply(interestRate); //相乘

        System.out.println("贷款金额:\t" + currency.format(loanAmount)); //贷款金额: ￥150.48
        System.out.println("利率:\t" + percent.format(interestRate));  //利率: 0.8%
        System.out.println("利息:\t" + currency.format(interest)); //利息: ￥1.20
    }
}
