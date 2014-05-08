package com.technozor.javaz;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.function.BiFunction;
import java.util.function.Function;

import static junit.framework.TestCase.assertEquals;


/**
 * Created by slim on 4/15/14.
 */
@RunWith(JUnit4.class)
public class Function2Test {
    final Function2<Integer, Integer, Integer> cSum = x1 -> x2 -> x1 + x2;
    final BiFunction<Integer, Integer, Integer> uncSum = (x1, x2) -> x1 + x2;

    final Function2<Integer, Integer, Integer> cMulti = x1 -> x2 -> x1 * x2;
    final Function2<Integer, Integer, Function<Integer, Integer>> addAndMulti = cSum.withThen(cMulti);
    final BiFunction<Integer, Integer, Integer> uncMulti = (x1, x2) -> x1 * x2;
    final Function<Integer, Integer> doubler = x -> 2 * x;


    @Test
    public void regularCurredSummer() {
        assertEquals(Integer.valueOf(4), cSum.apply(2).apply(2));
        Function<Integer, Integer> add2 = cSum.apply(2);
        assertEquals(Integer.valueOf(4), add2.apply(2));
        assertEquals(Integer.valueOf(6), add2.apply(4));
    }

    @Test
    public void regularunCurredSummer() {
        Function<Integer, Integer> add2 = cSum.apply(2);
        assertEquals(uncSum.apply(2, 2), add2.apply(2));
        assertEquals(uncSum.apply(2, 4), add2.apply(4));
    }

    @Test
    public void regularCurredSummerComposition() {
        assertEquals(20, addAndMulti.apply(2).apply(3).apply(4).intValue());
        assertEquals(14, addAndMulti.apply(4).apply(3).apply(2).intValue());
    }

    @Test
    public void regularCurriedToUncarriedTransformation() {
        BiFunction<Integer, Integer, Integer> nuncSum = cSum.unCurried();
        assertEquals(uncSum.apply(2, 4), nuncSum.apply(2, 4));
        assertEquals(uncSum.apply(2, 0), nuncSum.apply(2, 0));
        assertEquals(uncSum.apply(0, 4), nuncSum.apply(0, 4));
        assertEquals(uncSum.apply(0, 0), nuncSum.apply(0, 0));

    }

    @Test
    public void regularUncarriedToCurriedTransformation() {
        Function2<Integer, Integer, Integer> curried = Function2.curried(uncSum);
        assertEquals(cSum.apply(2).apply(4), curried.apply(2).apply(4));
        assertEquals(cSum.apply(2).apply(0), curried.apply(2).apply(0));
        assertEquals(cSum.apply(0).apply(4), curried.apply(0).apply(4));
        assertEquals(cSum.apply(0).apply(0), curried.apply(0).apply(0));
    }

    @Test
    public void test__UNC() throws Throwable {
        Function2<Integer, Integer, Integer> curried = Function2.__curried(uncSum);
        assertEquals(cSum.apply(2).apply(4), curried.apply(2).apply(4));
    }
}
