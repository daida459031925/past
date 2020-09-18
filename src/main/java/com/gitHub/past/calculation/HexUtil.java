package com.gitHub.past.calculation;

import java.util.*;

import static java.util.stream.Collectors.toList;

public abstract class HexUtil {

    /**
     * 进制数上限制
     */
    private static Integer radix = 0;

    //设置字符数组
    //可以添加任意不重复字符，提高能转换的进制的上限
    private static final List<Chard> chs = new LinkedList<>();

    static {
        //制定任意字符长度的进制数
        for (char i = 0; i < 10; i++) {
            chs.add(new Chard((char) ('0' + i)));
        }
        for (char i = 'A'; i <= 'Z'; i++) {
            chs.add(new Chard(i));
        }
        List<Chard> collect = chs.stream().distinct().collect(toList());
        chs.clear();
        chs.addAll(collect);
        radix = chs.size();
    }

    /**
     * 转换方法
     *
     * @param num       元数据字符串
     * @param fromRadix 元数据的进制类型
     * @param toRadix   目标进制类型
     * @return
     */
    static String transRadix(String num, int fromRadix, int toRadix) {
        if (fromRadix > radix) {
            throw new RuntimeException("已经超过最大进制限制，最大进制数为：" + radix);
        }

        int number = Integer.valueOf(num, fromRadix);
        StringBuilder sb = new StringBuilder();
        while (number != 0) {
            int i = number % toRadix;
            if (chs.size() - 1 < i) {
                throw new RuntimeException("已经超过最大转换进制限制，最大转换进制数为：" + chs.size());
            }
            sb.append(chs.get(i).getChs());
            number = number / toRadix;
        }
        return sb.reverse().toString();

    }

    //测试
    public static void main(String[] args) {
        System.out.println(transRadix("YGLkkk", 36, 39));
    }


    private static class Chard {
        private final char chs;

        private Chard(char chs) {
            this.chs = chs;
        }

        public char getChs() {
            return chs;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Chard chard = (Chard) o;
            return chs == chard.chs;
        }

        @Override
        public int hashCode() {
            return Objects.hash(chs);
        }

        @Override
        public String toString() {
            return String.valueOf(chs);
        }
    }
}
