package com.technozor.javaz.stm;

/**
 * Created by souertani on 7/3/2014.
 */
abstract class Context {
    abstract <T> T retrieve(Ref<T> ref);


    public Context getInstance() {
        return ContextHolder.INSTANCE;
    }

    private static class ContextHolder {
        private static final Context INSTANCE = new LiveContext();
    }

    private static class LiveContext extends Context {

        @Override
        <T> T retrieve(Ref<T> ref) {
            return ref.value;
        }
    }
}
