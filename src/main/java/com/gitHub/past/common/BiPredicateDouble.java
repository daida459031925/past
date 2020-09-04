package com.gitHub.past.common;

import java.util.Objects;

/**
 * 模仿BiPredicate写一个
 *
 * @param <T>
 * @param <U>
 * @param <C>
 * @param <D>
 */
@FunctionalInterface
public interface BiPredicateDouble<T, U, C, D, A> {

    boolean test(T t, U u, C c, D d, A a);

    default BiPredicateDouble<T, U, C, D, A> and(BiPredicateDouble<? super T, ? super U, ? super C, ? super D, ? super A> other) {
        Objects.requireNonNull(other);
        return (T t, U u, C c, D d, A a) -> test(t, u, c, d, a) && other.test(t, u, c, d, a);
    }

    default BiPredicateDouble<T, U, C, D, A> negate() {
        return (T t, U u, C c, D d, A a) -> !test(t, u, c, d, a);
    }

    default BiPredicateDouble<T, U, C, D, A> or(BiPredicateDouble<? super T, ? super U, ? super C, ? super D, ? super A> other) {
        Objects.requireNonNull(other);
        return (T t, U u, C c, D d, A a) -> test(t, u, c, d, a) || other.test(t, u, c, d, a);
    }

}
