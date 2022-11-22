package com.gitHub.past.web;

import java.util.HashMap;
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

    public static Result OK() {
        return new Result(Http.OK,null,null);
    }

    public static <T> Result OK(T data) {
        return new Result(Http.OK,null,data);
    }

    public static <T> Result OK(String msg,T data) {
        return new Result(Http.OK,msg,data);
    }

    public static <T> Result ERR() {
        return new Result(Http.ERR,null,null);
    }

    public static <T> Result ERR(String msg) {
        return new Result(Http.ERR,msg,null);
    }

    public static <T> Result ERR(String msg,T data) {
        return new Result(Http.ERR,msg,data);
    }

    public Result setFunction(Function<Object,Object> function){
        return setFunction(function,null);
    }

    public Result setFunction(Function<Object,Object> function,String msg){
        Function<Object,Object> f = (obj)->{
            try {
                if(this.state.equals(Http.OK)){
                    return function.apply(obj);
                }
            }catch (Exception e){
                e.printStackTrace();
                this.state = Http.ERR;
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

    public Result exec(){
        return exec(null);
    }

    public Result exec(Object obj){
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
            HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("ASDASD",123156);
            return objectObjectHashMap;
        };

        Result exec = OK().setFunction(function).setFunction(function1).setFunction(function2).setFunction(function3).exec("123");
        System.out.println(Objects.nonNull(exec.msg));
        System.out.println(Objects.isNull(exec.msg));
    }


    @Override
    public String toString() {
        return "Result{" +
                "state=" + state +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

class Http {
    static Integer OK = 200;
    static Integer ERR = 500;
}
