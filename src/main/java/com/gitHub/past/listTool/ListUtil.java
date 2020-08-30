package com.gitHub.past.listTool;

import com.gitHub.past.common.DefOptional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
     * @param fzuo 需要将list中zuo的数据变成一个新list   需要传入的就是java8中 流的map(e->XXXX)形式
     * @param fyou 需要将list中you的数据变成一个新list   需要传入的就是java8中 流的map(e->XXXX)形式
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

    public static void main(String[] args) {
        List<Integer> a = Arrays.asList(1, 2, 3);
        List<Integer> b = Arrays.asList(3, 4, 5);

        System.out.println(ListUtil.getListUtil(a, b, null, null).ajiaoJib());
        System.out.println(ListUtil.getListUtil(a, b, null, null).bjiaoJia());

        System.out.println(ListUtil.getListUtil(a, b, null, null).aJianb());
        System.out.println(ListUtil.getListUtil(a, b, null, null).bJiana());
    }
}
