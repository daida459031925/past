package com.gitHub.past.stringTool;

import com.gitHub.past.Invariable;

import java.util.Map;
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


    /**
     * 目标是根据传入的字符串换出指定格式内容 例如传入1.1   格式化初“ 1 1”
     */
    public static String a(String str,String regex,Integer integer){
//        正则对象    目前还存在bug
//        Pattern pattern = Pattern.compile(regExString);
//
//        Matcher matcher = pattern.matcher(line);

        String[] split = str.split(regex);
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i<split.length ;i++){

            sb.append(String.format("%"+ integer.toString() +"s",split[i]));
        }
        return sb.toString();
    }

}
