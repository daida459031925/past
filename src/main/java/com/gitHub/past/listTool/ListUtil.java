package com.gitHub.past.listTool;

import com.gitHub.past.common.DefOptional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

//普通这样写是没有任何问题的但是对象的化如何对比
public class ListUtil<T, R, XT> {
    //数据资源1
    private List<T> zuo;
    //数据资源2
    private List<R> you;
    //由于不知道是什么对象所以让用户自己传入转换后的
    private Function<T, XT> fzuo;
    //由于不知道是什么对象所以让用户自己传入转换后的
    private Function<R, XT> fyou;

    public List<T> getZuo() {
        return zuo;
    }
    public ListUtil<T, R, XT> setZuo(List<T> zuo) {
        this.zuo = zuo;
        return this;
    }

    public List<R> getYou() {
        return you;
    }
    public ListUtil<T, R, XT> setYou(List<R> you) {
        this.you = you;return this;
    }

    public Function<T, XT> getFzuo() {
        return fzuo;
    }
    public ListUtil<T, R, XT> setFzuo(Function<T, XT> fzuo) {
        this.fzuo = fzuo;return this;
    }

    public Function<R, XT> getFyou() {
        return fyou;
    }
    public ListUtil<T, R, XT> setFyou(Function<R, XT> fyou) {
        this.fyou = fyou;return this;
    }

    {
        fzuo = null;
        fyou = null;
    }

    /**
     * 无参构造方法 在这个里面主要的目的就是给方法可以使用new
     */
    public ListUtil() {}

    /***
     * @param zuo 基础数据list
     * @param you 基础数据list
     * @param fzuo 需要将list中zuo的数据变成一个新list   需要传入的就是java8中 流的map(e-》XXXX)形式
     * @param fyou 需要将list中you的数据变成一个新list   需要传入的就是java8中 流的map(e-》XXXX)形式
     */
    public static <T, R, XT> ListUtil<T, R, XT> getListUtil(List<T> zuo, List<R> you, Function<T, XT> fzuo, Function<R, XT> fyou){
        return new ListUtil<T, R, XT>().setZuo(zuo).setYou(you).setFzuo(fzuo).setFyou(fyou);
    }

    public List<T> ajiaoJib() {
        return aJiaoJib.apply(zuo, you);
    }

    public List<R> bjiaoJia() {
        return bJiaoJia.apply(you,zuo);
    }

    public List<T> aJianb() {
        return aJianb.apply(zuo,you);
    }

    public List<R> bJiana() {
        return bJiana.apply(you,zuo);
    }

//    public List<Object> bingJiNoQuchong(){
//        return bingJiNoQuchong.apply(zuo,you);
//    }
//
//    public List<Object> bingJiQuchong(){
//
//    }


    /**
     * 目标根据指定 指定list 和需要
     * @return
     */
    private List<?> getListXzuo() {
        Optional<List<?>> xts = getProcessingResults(this.fzuo,this.zuo);
        if(xts.isPresent()){ return xts.get(); }else{ return this.zuo; }
    }


    private List<?> getListXyou() {
        Optional<List<?>> xts = getProcessingResults(this.fyou,this.you);
        if(this.fyou != null){ return xts.get(); }else{ return this.you; }
    }

    private static <A, B> Optional<List<?>> getProcessingResults(Function<A,B> function, List<A> list) {
        return DefOptional.ofNullable(function, list)
                .map(e -> list.stream().map(e::apply).collect(Collectors.toList())).get();
    }

    /**
     *
     * @param a     需要变跟的数据
     * @param fhwh  变更的方法
     * @param <A>   若无法变更则使用原来的值
     * @return
     */
    private static <A> Optional<?> getwenhao(A a,Function<A,?> fhwh) {
        return DefOptional.ofNullable(fhwh,a).map(e->e.apply(a)).get();
    }

    //交集
    private BiFunction<List<T>, List<R>, List<T>> aJiaoJib = (List<T> izuo, List<R> iyou) ->
            izuo.stream().filter(e -> getListXyou().contains(getwenhao(e,this.fzuo).get())).collect(Collectors.toList());


    private BiFunction<List<R>, List<T>, List<R>> bJiaoJia = (List<R> iyou, List<T> izuo) ->
            iyou.stream().filter(e -> getListXzuo().contains(getwenhao(e,this.fyou).get())).collect(Collectors.toList());


    //差集
    private BiFunction<List<T>, List<R>, List<T>> aJianb = (List<T> izuo, List<R> iyou) ->
            izuo.stream().filter(e -> !getListXyou().contains(getwenhao(e,this.fzuo).get())).collect(Collectors.toList());

    //差集
    private BiFunction<List<R>, List<T>, List<R>> bJiana = (List<R> iyou, List<T> izuo) ->
            iyou.stream().filter(e -> !getListXzuo().contains(getwenhao(e,this.fyou).get())).collect(Collectors.toList());

    //并集  不去重
    private BiFunction<List<Object>, List<Object>, List<Object>> bingJiNoQuchong = (List<Object> i1, List<Object> i2) -> {
        i1.addAll(i2);
        return i1;
    };

    //并集  去重
    private BiFunction<List<Object>, List<Object>, List<Object>> bingJiQuchong = (List<Object> i1, List<Object> i2) -> {
        i1.addAll(i2);
        List<Object> collect4 = i1.stream().distinct().collect(Collectors.toList());
        return collect4;
    };

    private static BiFunction<Long,Long,List<Long>> offset = (offset, limit) -> {
        long thisPath = BigDecimal.valueOf(offset).divide(BigDecimal.valueOf(limit)).setScale(0, BigDecimal.ROUND_UP).longValue();
        return Arrays.asList((thisPath + 1) * limit,thisPath * limit);
    };

    private static BiFunction<Long,Long,List<Long>> page = (page,limit) -> Arrays.asList((page + 1) * limit,page * limit);


    /***
     *
     * @param offset  传入的起始查看当前页的第一个数值
     * @param limit   传入的是每页多少数据
     * @param list    传入的需要分页的数据
     * @param <T>     传入什么类型返还什么类型
     * @return        又返还值
     */
    public static <T> List<T> getFenYeList(long offset,long limit, List<T> list){
        //java8中分页需要使用limit limit 是0～<limit 的数值   而skip是    xxx>= skip 的数值
        long thisPath = BigDecimal.valueOf(offset).divide(BigDecimal.valueOf(limit)).setScale(0, BigDecimal.ROUND_UP).longValue();
        //计算公式
        List<Long> apply = Arrays.asList((thisPath + 1) * limit,thisPath * limit);
        return list.stream().limit(apply.get(0)).skip(apply.get(1)).collect(Collectors.toList());
    }


    /***
     *
     * @param page    传入的起始页数
     * @param limit   传入的是每页多少数据
     * @param list    传入的需要分页的数据
     * @param <T>     传入什么类型返还什么类型
     * @return        又返还值
     */
    public static <T> List<T> getFenYeList(Long page,Long limit, List<T> list){
        List<Long> apply = Arrays.asList((page + 1) * limit,page * limit);
        return list.stream().limit(apply.get(0)).skip(apply.get(1)).collect(Collectors.toList());
    }

    /**
     *
     * @param list       传入对应需要按指定条件去重
     * @param comparator 去重的条件
     * @param <T>
     * @return
     */
    public static <T> List<T> distinctComparing(List<T> list,Comparator<T> comparator){
        return list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                new TreeSet<T>(comparator)), ArrayList::new));
    }

    /**
     * 拿到最后一个list的数据
     */
    public static <T> T findLast(List<T> list){
        return list.stream().reduce((first, second) -> second).orElse(null);
    }


    /**
     * addAll     并集  这个没问题直接合并 问题是在是否去重
     * removeAll  差集  差集 a -b  b - a  结果不相同不是一般想要目的  里面存在一个问题就是否所有人都🗡减
     * 若所有人都有相同数据集才能对最后的数据进行减少，那么可以采用 交集取反的方式达到想要的结果。 若不是那么就和你怎么减的有直接关系
     * retainAll  交集  经过自己简单测试也可以达到一般目的  多个list里面数据各不相同 那么返还的size为0
     *
     * @param elementLists
     * @param <T>
     * @return
     */
    public static <T> List<T>  retainElementList(List<List<T>> elementLists) {
        Optional<List<T>> result = elementLists.parallelStream()
                .filter(elementList -> elementList != null && ((List) elementList).size() != 0)
                .reduce((a, b) -> {
                    a.retainAll(b);
                    return a;
                });
        return result.orElse(new ArrayList<>());
    }


}
