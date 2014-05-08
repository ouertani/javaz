package com.technozor.javaz;

import java.util.Optional;
import java.util.function.Function;

/**
 * Created by slim on 4/15/14.
 */
@FunctionalInterface
public interface PartialFunction2<T, R> extends Function<T, Optional<R>> {


    Optional<R> apply(T t);


    static <T, R> PartialFunction2<T, R> apply(Function< T, Boolean> p, Function<T, R> f) {
        return t -> {
            if (p.apply(t))
                return Optional.of(f.apply(t));
            else
                return Optional.empty();
        };
    }


}
