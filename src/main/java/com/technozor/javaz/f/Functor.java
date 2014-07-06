package com.technozor.javaz.f;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by souertani on 7/6/2014.
 */
@FunctionalInterface
public interface Functor<E,  V extends Functor<E, V>> extends Base<E, V> {
    <B> Functor<B> map (Function<? super E,? extends B> f);

    class StreamFunctor<E> implements Functor<E, StreamFunctor<E>> {

        private final  Stream<E> s;

        private  StreamFunctor(Stream<E> s) {
            this.s = s;
        }
        public static <E> StreamFunctor<E> asFunctor(Stream<E> s) { return new StreamFunctor<>(s);}

         @Override
        public <B> StreamFunctor<B> map(Function<? super E, ? extends B> f) {
            return asFunctor(s.map(f));
        }



    }

    class ListFunctor<E> implements Functor<E> {
        private final List<E> s;

        private ListFunctor(List<E> s) {
            this.s = s;
        }

        public static <E> ListFunctor<E> asFunctor(List<E> s) { return new ListFunctor<>(s);}

        @Override
        public <B> ListFunctor<B> map(Function<? super E, ? extends B> f) {
            return asFunctor(s.stream().map(f).collect(Collectors.toList()));
        }
    }
    class OptinalFunctor<E> implements Functor<E> {


    }

}
