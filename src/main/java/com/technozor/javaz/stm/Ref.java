package com.technozor.javaz.stm;

import java.util.concurrent.atomic.AtomicInteger;

import static com.technozor.javaz.stm.Ref.$$;

/**
 * Created by souertani on 7/3/2014.
 */
public class Ref<T> {
    private $$<T> _content;
    public T value;

    Ref(T t) {
        this._content = $$.$(t, 0);
        this.value = t;
    }

    T get(Context ctx) {
        return ctx.retrieve(this);
    }

    void set(T t, Transaction ctx) {
        ctx.store(this, t);
    }

    void setContent(Object t, int version) {
        this._content = $$.$((T) t, version);
    }

    $$ getContent() {
        return _content;
    }

    static class $$<T> {
        T t;
        int version;

        $$(T t, int version) {
            this.t = t;
            this.version = version;
        }

        public static <T> $$<T> $(T t, int version) {
            return new $$(t, version);
        }

        public T getT() {
            return t;
        }

        public int getVersion() {
            return version;
        }
    }
}
