package com.gitHub.past.common;

import java.util.Objects;
import java.util.function.Function;


@FunctionalInterface
public interface CiFunction<T, U, R, V> {


    V apply(T t, U u, R r);

    /**
     *  compose 和 andThen  目前还未实现
     */
//    default <V> CiFunction<T, U, R, V> andThen(Function<? super R, ? extends V> after) {
//        Objects.requireNonNull(after);
//        return (T t, U u) -> after.apply(apply(t, u));
//    }
}

