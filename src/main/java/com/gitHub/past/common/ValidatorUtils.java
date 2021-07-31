package com.gitHub.past.common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * 制作目的，防止各种if判断来写出这个参数怎么怎么不行的问题，
 * 若使用 Hibernate Validator 则需要引入jar 并且有学习成本
 * 所以自己实现自己要的
 */
public class ValidatorUtils {

    private List<String> list = new LinkedList<>();

    public static ValidatorUtils of() {
        return new ValidatorUtils();
    }

    public static ValidatorUtils of(boolean boo, String str) {
        return of().isApply(boo, str);
    }

    public static ValidatorUtils of(Predicate predicate,Object o, String str) {
        return of().isApply(predicate.test(o), str);
    }

    public ValidatorUtils isApply(boolean boo, String str) {
        if (Objects.isNull(str)) {
            str = "尚未添加任何信息";
        }
        if (boo && !list.contains(str)) {
            list.add(str);
        }
        return this;
    }

    public ValidatorUtils isApply(Predicate predicate,Object o, String str) {
        return isApply(predicate.test(o),str);
    }

    public List<String> getList() {
        return new ArrayList<>(list);
    }

    public void getError() throws Exception {
        if (!list.isEmpty()) {
            throw new RuntimeException(list.toString());
        }
    }
}
