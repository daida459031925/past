package com.gitHub.past.convert;

import java.io.File;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

//org.apache.commons.beanutils.ConvertUtils这个类的使用介绍，这个工具类的职能是在字符串和指定类型的实例之间进行转换。
//唯一的问题是转换出来的东西都是object
public class ConventUtil {

    protected static Map<String,Class<?>> map = new HashMap<>();

    static {
        map.put("Integer",Integer.class);
        map.put("String",String.class);
        map.put("BigDecimal",BigDecimal.class);
        map.put("BigInteger",BigInteger.class);
        map.put("Boolean",Boolean.class);
        map.put("Byte",Byte.class);
        map.put("Character",Character.class);
        map.put("Class",Class.class);
        map.put("Double",Double.class);
        map.put("Float",Float.class);
        map.put("Long",Long.class);
        map.put("Short",Short.class);
        map.put("URL",URL.class);
        map.put("File",File.class);
        map.put("Date",Date.class);
        map.put("Time",Time.class);
        map.put("Timestamp",Timestamp.class);

    }

    private static Object call(String methodName) {
        try {
            Method m = ConventUtil.class.getDeclaredMethod(methodName);
            return m.invoke(ConventUtil.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Object call(String methodName, String param) {
        try {
            Method m = ConventUtil.class.getDeclaredMethod(methodName, String.class);
            return m.invoke(ConventUtil.class, param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return param;
    }

    public static Integer convert(String param){
        return Integer.parseInt(param);
    }



    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> integer = map.get("Integer");


        Object convert1 = call("convert","2");
        int i = 1;
        if(convert1 instanceof Integer){

        }
        Function<String,Class<Integer>> function = (string)->{
            return Integer.class;
        };
        Class<?> apply = function.apply("1");
        Class<?> integer1 = map.get("Integer");
        Constructor constructor = integer1.getConstructor(String.class);

//        ConventUtil conventUtil = new ConventUtil();
//        Integer convert = conventUtil.convert();
//        Object o = integer1.newInstance();
//        Class<T>; entityClass = (ClassL<T>) ((ParameterizedType)apply.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//        Object integer = ConventUtil.getInstance().convert("1");

    }
//
//    public static void main(String[] args) {
//        Integer convert = convert("1", Integer.class);
//
//    }
//
//    public static  <T> T get(Class<T> clz,Object o){
//        if(clz.isInstance(o)){
//            return clz.cast(o);
//        }
//        return null;
//    }
//
//    public static AtomicReference<?> aaa(){
//        AtomicReference<Integer> integerAtomicReference = new AtomicReference<>();
//        integerAtomicReference.set(1);
//        return integerAtomicReference;
//    }
//
//
//    public Class<?> a(){
//        return Integer.class;
//    }
//
//
//    public static<T> T convert(Object obj, Class<T> type) {
//        if (obj != null && !obj.toString().isEmpty()) {
//            if (type.equals(Integer.class)||type.equals(int.class)) {
//                return (T)new Integer(obj.toString());
//            } else if (type.equals(Long.class)||type.equals(long.class)) {
//                return (T)new Long(obj.toString());
//            } else if (type.equals(Boolean.class)||type.equals(boolean.class)) {
//                return (T) new Boolean(obj.toString());
//            } else if (type.equals(Short.class)||type.equals(short.class)) {
//                return (T) new Short(obj.toString());
//            } else if (type.equals(Float.class)||type.equals(float.class)) {
//                return (T) new Float(obj.toString());
//            } else if (type.equals(Double.class)||type.equals(double.class)) {
//                return (T) new Double(obj.toString());
//            } else if (type.equals(Byte.class)||type.equals(byte.class)) {
//                return (T) new Byte(obj.toString());
//            } else if (type.equals(Character.class)||type.equals(char.class)) {
//                return (T)new Character(obj.toString().charAt(0));
//            } else if (type.equals(String.class)) {
//                return (T) obj;
//            } else if (type.equals(BigDecimal.class)) {
//                return (T) new BigDecimal(obj.toString());
//            } else if (type.equals(LocalDateTime.class)) {
//                //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                return (T) LocalDateTime.parse(obj.toString());
//            } else if (type.equals(Date.class)) {
//                try
//                {
//                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//                    return (T) formatter.parse(obj.toString());
//                }
//                catch (ParseException e)
//                {
//                    throw new RuntimeException(e.getMessage());
//                }
//
//            }else{
//                return null;
//            }
//        } else {
//            if (type.equals(int.class)) {
//                return (T)new Integer(0);
//            } else if (type.equals(long.class)) {
//                return (T)new Long(0L);
//            } else if (type.equals(boolean.class)) {
//                return (T)new Boolean(false);
//            } else if (type.equals(short.class)) {
//                return (T)new Short("0");
//            } else if (type.equals(float.class)) {
//                return (T) new Float(0.0);
//            } else if (type.equals(double.class)) {
//                return (T) new Double(0.0);
//            } else if (type.equals(byte.class)) {
//                return (T) new Byte("0");
//            } else if (type.equals(char.class)) {
//                return (T) new Character('\u0000');
//            }else {
//                return null;
//            }
//        }
//    }
}
