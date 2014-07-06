package com.technozor.javaz.f;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by souertani on 7/6/2014.
 */
@FunctionalInterface
public interface Functor<E> extends Base<E, Functor<E>> {
    <B> Functor<B> map (Function<? super E,? extends B> f);

    class StreamFunctor<E> implements Functor<E> {

        private final  Stream<E> s;

        public StreamFunctor(Stream<E> s) {
            this.s = s;
        }

        @Override
        public <B> Functor<B> map(Function<? super E, ? extends B> f) {
            return new StreamFunctor<>(s.map(f));
        }
    }

    class ListFunctor<E> implements Functor<E> {

    }

}
