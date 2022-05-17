package com.gitHub.past.convert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

//public interface ClzssType<T> {
//
//    public T getT();
//    public T getT(String s);
//
//
//    public static Object call(String methodName, String param) {
//        try {
//            Method m = ClzssType.class.getDeclaredMethod(methodName, String.class);
//            return m.invoke(ClzssType.class, param);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return param;
//    }
//}

//class A<T>{
//
//
//    public static  <T> T getT(String s) {
//        return (T)new A<String>();
//    }
//
//    public static Object call(String methodName, String param) {
//        try {
//            Method m = ClzssType.class.getDeclaredMethod(methodName, String.class);
//            return m.invoke(ClzssType.class, param);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return param;
//    }
//
//    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        Object t = A.getT("");
//        Class.forName("java.lang.Integer").cast("1");
//        Integer parseInt = (Integer)Class.forName("java.lang.Integer")
//                .getMethod("parseInt", String.class).invoke(Integer.class, "1");
//    }
//}

class IHadnler<T>{

    private T t;

    public IHadnler(T t) {
        this.t = t;
    }

    public IHadnler() {
        super();
    }

    public IHadnler<T> of(String s,String value){
        IHadnler<T> iHadnler = null;
        switch (s){
            /**
             *     基本类型：int 二进制位数：32
             *     包装类：java.lang.Integer
             *     最小值：Integer.MIN_VALUE=-2147483648
             *     最大值：Integer.MAX_VALUE=2147483647
             */
            case "Integer": iHadnler = (IHadnler<T>) new IHadnler<>(Integer.valueOf(value));break;
            /**
             * 基本类型：double 二进制位数：64
             * 包装类：java.lang.Double
             * 最小值：Double.MIN_VALUE=4.9E-324
             * 最大值：Double.MAX_VALUE=1.7976931348623157E308
             */
            case "Double": iHadnler = (IHadnler<T>) new IHadnler<>(Double.valueOf(value));break;

            case "String": iHadnler = (IHadnler<T>) new IHadnler<>(String.valueOf(value));break;

            /**
             *     基本类型：byte 二进制位数：8
             *     包装类：java.lang.Byte
             *     最小值：Byte.MIN_VALUE=-128
             *     最大值：Byte.MAX_VALUE=127
             */
            case "Byte": iHadnler = (IHadnler<T>) new IHadnler<>(Byte.valueOf(value));break;
            /**
             *     基本类型：short 二进制位数：16
             *     包装类：java.lang.Short
             *     最小值：Short.MIN_VALUE=-32768
             *     最大值：Short.MAX_VALUE=32767
             */
            case "Short": iHadnler = (IHadnler<T>) new IHadnler<>(Short.valueOf(value));break;
            /**
             *     基本类型：long 二进制位数：64
             *     包装类：java.lang.Long
             *     最小值：Long.MIN_VALUE=-9223372036854775808
             *     最大值：Long.MAX_VALUE=9223372036854775807
             */
            case "Long": iHadnler = (IHadnler<T>) new IHadnler<>(Long.valueOf(value));break;
            /**
             *     基本类型：float 二进制位数：32
             *     包装类：java.lang.Float
             *     最小值：Float.MIN_VALUE=1.4E-45
             *     最大值：Float.MAX_VALUE=3.4028235E38
             */
            case "Float": iHadnler = (IHadnler<T>) new IHadnler<>(Float.valueOf(value));break;

            /**
             *     基本类型：char 二进制位数：16
             *     包装类：java.lang.Character
             *     最小值：Character.MIN_VALUE=0
             *     最大值：Character.MAX_VALUE=65535
             */
            case "char[]": iHadnler = (IHadnler<T>) new IHadnler<>(String.valueOf(value).toCharArray());break;
        }

        if(Objects.isNull(iHadnler)){

        }

        return  iHadnler;
    }

    public T getT() {
        return t;
    }
}