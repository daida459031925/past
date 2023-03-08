package com.gitHub.past.annotate.initValue;

import java.lang.reflect.Field;

public class InitValueFactory {
    public static <T> T create(Class<T> t) throws IllegalAccessException, InstantiationException {
        T t1 = t.newInstance();
        Field[] fields = t1.getClass().getDeclaredFields();
        for (Field field:fields) {
            if(field.isAnnotationPresent(InitValue.class)){
                InitValue annotation = field.getAnnotation(InitValue.class);
                try {
                    field.setAccessible(true);
                    Class<?> type = field.getType();
                    Object obj=null;
                    if(type == Integer.class){
                        obj = Integer.valueOf(annotation.value());

                    }
                    if(type==String.class){
                        obj=annotation.value();
                    }
                    field.set(t1,obj);

                    // method.invoke(t1,annotation.value(),annotation.intValue());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return t1;

    }
}
