package com.technozor.javaz;

import java.util.Objects;
import java.util.function.Function;

/**
 * Created by slim on 4/15/14.
 */
@FunctionalInterface
public interface TriFunction<Q, T, U, R> {

    R apply(Q q, T t, U u);

    default <V> TriFunction<Q, T, U, V> withThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (q, t, u) -> after.apply(apply(q, t, u));
    }

    default Function3<Q, T, U, R> curried() {
        return q -> t -> u -> apply(q, t, u);
    }

}
