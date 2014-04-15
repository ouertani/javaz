package com.technozor.javaz;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by slim on 4/14/14.
 */
@FunctionalInterface
public interface Function2<T, U, R> extends Function<T, Function<U, R>> {

    Function<U, R> apply(T t);

    default <V> Function2<T, U, V> withThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return t -> u -> after.apply(apply(t).apply(u));
    }

    default BiFunction<T,U,R> unCurried () {
        return (t, u) -> apply(t).apply(u);
    }

    static <T,U,R>  Function2<T,U,R> curried (BiFunction<T,U,R> f) {
        Objects.requireNonNull(f);
        return t -> u -> f.apply(t,u);
    }
}
