package com.gitHub.past.listTool;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

//普通这样写是没有任何问题的但是对象的化如何对比
public class ListUtil<T,R,XT> {
    //数据资源1
    private List<T> zuo;
    //数据资源2
    private List<R> you;
    //由于不知道是什么对象所以让用户自己传入转换后的
    private Function<T,XT> fzuo;
    //由于不知道是什么对象所以让用户自己传入转换后的
    private Function<R,XT> fyou;

    //通过fzuo返还的新对象
    private List<XT> xzuo;
    //通过fyou返还的新对象
    private List<XT> xyou;

    {
        fzuo = null;
        fyou = null;
    }

    /***
     * @param zuo 基础数据list
     * @param you 基础数据list
     * @param fzuo 需要将list中zuo的数据变成一个新list   需要传入的就是java8中 流的map(e->XXXX)形式
     * @param fyou 需要将list中you的数据变成一个新list   需要传入的就是java8中 流的map(e->XXXX)形式
     */
    public ListUtil(List<T> zuo, List<R> you, Function<T, XT> fzuo, Function<R, XT> fyou) {
        this.zuo = zuo;
        this.you = you;
        this.fzuo = fzuo;
        this.fyou = fyou;
    }

    /***
     * @param zuo 基础数据list
     * @param you 基础数据list
     * @param fzuo 需要将list中zuo的数据变成一个新list   需要传入的就是java8中 流的map(e->XXXX)形式
     * @param fyou 需要将list中you的数据变成一个新list   需要传入的就是java8中 流的map(e->XXXX)形式
     */
    public ListUtil<T,R,XT> ListUtilAll(List<T> zuo, List<R> you, Function<T, XT> fzuo, Function<R, XT> fyou) {
        this.zuo = zuo;
        this.you = you;
        this.fzuo = fzuo;
        this.fyou = fyou;
        return this;
    }

    /***
     * @param zuo 基础数据list
     * @param you 基础数据list
     * @param fyou 需要将list中you的数据变成一个新list   需要传入的就是java8中 流的map(e->XXXX)形式
     */
    public ListUtil<T,R,XT> ListUtilyou(List<T> zuo, List<R> you, Function<R, XT> fyou) {
        this.zuo = zuo;
        this.you = you;
        this.fzuo = null;
        this.fyou = fyou;
        return this;
    }

    /***
     * @param zuo 基础数据list
     * @param you 基础数据list
     * @param fzuo 需要将list中zuo的数据变成一个新list   需要传入的就是java8中 流的map(e->XXXX)形式
     */
    public ListUtil<T,R,XT> ListUtilzuo(List<T> zuo, List<R> you, Function<T, XT> fzuo) {
        this.zuo = zuo;
        this.you = you;
        this.fzuo = fzuo;
        this.fyou = null;
        return this;
    }

    /***
     * @param zuo 基础数据list
     * @param you 基础数据list
     */
    public ListUtil<T,R,XT> ListUtil(List<T> zuo, List<R> you) {
        this.zuo = zuo;
        this.you = you;
        this.fzuo = null;
        this.fyou = null;
        return this;
    }

    public void ajiaoJib(){
        ajiaoJib.apply(zuo,you);
    }

    private List<?> getXzuo(){
        Optional.ofNullable(this.fzuo).map(e-> this.zuo.stream().map(v->this.fzuo.apply(v)).collect(Collectors.toList())).orElse(this.zuo);

        return null;
    }

    private List<?> getXyou(){

        return null;
    }

    //交集
    private BiFunction<List<T>,List<R>,List<T>> ajiaoJib = (List<T> i1, List<R> i2)->
            i1.stream().filter(num -> i2.contains(num)).collect(Collectors.toList());;

    private BiFunction<List<R>,List<T>,List<R>> bjiaoJia = (List<R> i2, List<T> i1)->i2.stream().filter(num -> i1.contains(num)).collect(Collectors.toList());;

    //差集
    private BiFunction<List<T>,List<R>,List<T>> aJianb = (List<T> i1, List<R> i2)->i1.stream().filter(num -> !i2.contains(num)).collect(Collectors.toList());

    //差集
    private BiFunction<List<R>,List<T>,List<R>> bJiana = (List<R> i2 , List<T> i1)->i2.stream().filter(num -> !i1.contains(num)).collect(Collectors.toList());

    //并集  不去重
    private BiFunction<List<Object>,List<Object>,List<Object>> bingJiNoQuchong = (List<Object> i1, List<Object> i2)->{i1.addAll(i2);return i1;};

    //并集  去重
    private BiFunction<List<Object>,List<Object>,List<Object>> bingJiQuchong = (List<Object> i1, List<Object> i2)->{
        i1.addAll(i2);
        List<Object> collect4 = i1.stream().distinct().collect(Collectors.toList());
        return collect4;
    };

}
