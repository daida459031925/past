package com.gitHub.past.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * 类似go的result 作用是通用返还值，
 * @state 200正常500不正常
 * @msg 返还的提示信息
 * @data 返还的数据
 * @param <T>
 */
public class Result<T> {

    public static final int OK = 200;
    public static final int ERR = 500;
    /**
     * 返回结果状态码
     */
    private Integer state;

    /**
     * 返回提示信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;


    private Function<Object,Object> function;

    private Result(Integer state, String msg, T data) {
        this.state = state;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> OK() {
        return OK(null,null);
    }

    public static <T> Result<T> OK(T data) {
        return OK(data,null);
    }

    public static <T> Result<T> OK(T data,String msg){
        return new Result(OK,msg,data);
    }

    public static <T> Result<T> ERR() {
        return ERR(null,null);
    }

    public static <T> Result<T> ERR(String msg) {
        return ERR(null,msg);
    }

    public static <T> Result<T> ERR(T data,String msg) {
        return new Result(ERR,msg,data);
    }

    public Result<T> setFunction(Function<Object,Object> function){
        return setFunction(function,null);
    }

    public Result<T> setFunction(Function<Object,Object> function,String msg){
        Function<Object,Object> f = (obj)->{
            try {
                if(this.state.equals(OK)){
                    return function.apply(obj);
                }
            }catch (Exception e){
                e.printStackTrace();
                this.state = ERR;
                this.data = null;
                this.msg = msg;
            }
            return null;
        };

        if(Objects.isNull(this.function)){
            this.function = f;
        }else{
            this.function = this.function.andThen(f);
        }
        return this;
    }

    public Result<T> exec(){
        return exec(null);
    }

    public Result<T> exec(Object obj){
        if(Objects.nonNull(this.function)){
            Object apply = this.function.apply(obj);
            this.data = (T) apply;
        }
        return this;
    }

    public static void main(String[] args) {
        Function<Object,Object> function = (obj)->{
            System.out.println(obj);
            return 0;
        };

        Function<Object,Object> function1 = (obj)->{
            System.out.println(obj);
            return 1;
        };

        Function<Object,Object> function2 = (obj)->{
            System.out.println(obj);
            return 2;
        };

        Function<Object,Object> function3 = (obj)->{
            System.out.println(obj);
            Map<Object, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("ASDASD",123156);
            return objectObjectHashMap;
        };

        Result<Object> exec = OK().setFunction(function).setFunction(function1).setFunction(function2).setFunction(function3).exec("123");
        System.out.println(Objects.nonNull(exec));
        System.out.println(Objects.isNull(exec));
        Map<String,String> data1 = exec.getData(Map.class);
        System.out.println(data1);
    }


    @Override
    public String toString() {
        return "Result{" +
                "state=" + state +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public Integer getState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public <E> E getData(Class<E> clazz) {
        return (E)data;
    }

    public T getData() {
        return data;
    }
}
