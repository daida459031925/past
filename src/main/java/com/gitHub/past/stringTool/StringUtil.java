package com.gitHub.past.stringTool;

import com.gitHub.past.Invariable;

import java.util.Optional;
import java.util.function.Function;

public class StringUtil {

    private String str;

    private StringUtil(String string){
        str = string;
    }

    public static StringUtil getString(Object string){
        return new StringUtil(Optional.ofNullable(string).map(e->toString.apply(e)).orElse(Invariable.EMPTY.toString()));
    }

    public static StringUtil getTrimString(Object string){
        return getString(string).trim();
    }

    public StringUtil trim(){
        str = str.trim();
        return this;
    }

    /**
     * 传入一个字符串查看是否为null 或者为"" 判断是否存在“ ”这个情况 将“ ”左右两边的进行过滤
     * 但是“A B”不算
     */
    public Boolean isEmpty (){
        return this.str.length() <= 0;
    }

    public Boolean isNotEmpty (){
        return this.str.length() > 0;
    }

    /**
     * 将对象使用String.valueOf 防止对象出现null的情况 如果传入的为null 那么返还"null"
     */
    public static Function<Object,String> toString = String::valueOf;

    public static void main(String[] args) {
        System.out.println(StringUtil.getString(null).trim().isEmpty());
    }
}
