package com.technozor.javaz;

import java.util.Objects;
import java.util.function.Function;

/**
 * Created by slim on 4/14/14.
 */
@FunctionalInterface
public interface Function3<Q, T, U, R> extends Function<Q, Function2<T, U, R>> {
    Function2<T, U, R> apply(Q q);


    default <V> Function3<Q, T, U, V> withThen(Function2<? super U, ? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return q -> t -> u -> after.apply(u).apply(apply(q).apply(t).apply(u));
    }

    default <V> Function3<Q, T, U, V> withThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return q -> t -> u -> after.apply(apply(q).apply(t).apply(u));
    }

    default   TriFunction<Q,T,U,R> uncurrying ( ) {
        return (q, t, u) -> apply(q).apply(t).apply(u);
    }



}
