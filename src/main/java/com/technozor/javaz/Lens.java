package com.technozor.javaz;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by slim on 5/8/14.
 */
public class Lens<A, B> {

    public final Function<A, B> getter;
    public final BiFunction<A, B, A> setter;

    public static <A, B> Lens<A, B> lens(Function<A, B> get, BiFunction<A, B, A> set) {
        return new Lens<>(get, set);
    }


    public <C> Lens<C, B> compose(final Lens<C, A> that) {
        return new Lens<>(c -> getter.apply(that.getter.apply(c)), (c, b) -> that.mod(c, a -> setter.apply(a, b)));
    }

    public <C> Lens<A, C> andThen(Lens<B, C> that) {
        return that.compose(this);
    }

    private Lens(Function<A, B> get, BiFunction<A, B, A> set) {
        this.getter = get;
        this.setter = set;
    }


    public A mod(A a, Function<B, B> updater) {
        return setter.apply(a, updater.apply(getter.apply(a)));
    }
}
