package com.gitHub.past.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class ClassTool<T> extends ClassA<T> {

    public static Function<Object, String> java8FunctionType = (t1) -> {
        Type[] ts = t1.getClass().getGenericInterfaces();
        if (ts != null && ts.length > 0) {
            if (ts[0] instanceof ParameterizedType) {
                Logger.getGlobal().info("当前类型为： "+ts);
                for (Type type : ((ParameterizedType) ts[0]).getActualTypeArguments()) {
                    return type.getTypeName();
                }
            }
        }
        return "";
    };

    public static void main(String[] args){
        Consumer<String> consumer = (tq)->new String();

        consumer.accept("1");

        Predicate<Boolean> predicate = (t)->false;


        Supplier<String> stringSupplier = ()->new String();
        stringSupplier.getClass().getSuperclass();

        try {
            //大括号非常重要，相当于匿名内部类
            Map<String, Integer> map = new HashMap<String, Integer>(){};
            Type type = map.getClass().getGenericSuperclass();
            ParameterizedType parameterizedType = ParameterizedType.class.cast(type);
            for (Type typeArgument : parameterizedType.getActualTypeArguments()) {
                System.out.println(typeArgument.getTypeName());
            }

            /* Output
            java.lang.String
            java.lang.Integer
            */

            List<String> list = new LinkedList<String>(){};
            java8FunctionType.apply(consumer);
//            type = consumer.getClass().getGenericSuperclass();
//            parameterizedType = ParameterizedType.class.cast(type);
//            for (Type typeArgument : parameterizedType.getActualTypeArguments()) {
//                System.out.println(typeArgument.getTypeName());
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        java8FunctionType.apply(a);
    }
}

class  ClassA <T>{
    private T obj;
    public void setObject(T obj) {      this.obj = obj;  }
    public T getObject() {    return obj;   }

    /**
     * 获取T的实际类型
     */
    public void testClassA() throws NoSuchFieldException, SecurityException {
        System.out.print("getSuperclass:");
        System.out.println(this.getClass().getSuperclass().getName());
        System.out.print("getGenericSuperclass:");
        Type t = this.getClass().getGenericSuperclass();
        System.out.println(t);
        if (ParameterizedType.class.isAssignableFrom(t.getClass())) {
            System.out.print("getActualTypeArguments:");
            for (Type t1 : ((ParameterizedType) t).getActualTypeArguments()) {
                System.out.print(t1 + ",");
            }
            System.out.println();
        }
    }

    /***
     * 获取List中的泛型
     */
    public static void testList() throws NoSuchFieldException, SecurityException {
        Type t = ClassA.class.getDeclaredField("obj").getGenericType();
        if (ParameterizedType.class.isAssignableFrom(t.getClass())) {
            for (Type t1 : ((ParameterizedType) t).getActualTypeArguments()) {
                System.out.print(t1 + ",");
            }
            System.out.println();
        }
    }

}

