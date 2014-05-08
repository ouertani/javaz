package com.technozor.javaz;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by slim on 4/14/14.
 */
@FunctionalInterface
public interface Function2<T, U, R> extends Function<T, Function<U, R>> {

    static <T, U, R> Function2<T, U, R> curried(BiFunction<T, U, R> f) {
        Objects.requireNonNull(f);
        return t -> u -> f.apply(t, u);
    }

    static <T, U, R> Function2<T, U, R> __curried(BiFunction<T, U, R> f) throws Throwable {
        Objects.requireNonNull(f);
        Lookup lookup = MethodHandles.lookup();


        MethodHandle mh = lookup.findVirtual(BiFunction.class, "apply", MethodType.methodType(Object.class, Object.class, Object.class));


        return new Function2<T, U, R>() {
            MethodHandle mh1 = MethodHandles.insertArguments(mh, 0, f);

            @Override
            public Function<U, R> apply(T t) {
                MethodHandle mh2 = MethodHandles.insertArguments(mh1, 0, (T) t);
                return u -> {
                    try {
                        return (R) MethodHandles.insertArguments(mh2, 0, (R) u).invoke();
                    } catch (Throwable throwable) {
                        doThrow(throwable);
                        return null;
                    }

                };
            }
        };
    }

    Function<U, R> apply(T t);

    default <V> Function2<T, U, V> withThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return t -> u -> after.apply(apply(t).apply(u));
    }

    default BiFunction<T, U, R> unCurried() {
        return (t, u) -> apply(t).apply(u);
    }

    @SuppressWarnings("unchecked")
    static <E extends Throwable> void doThrow(Throwable e) throws E {
        throw (E) e;
    }


}
