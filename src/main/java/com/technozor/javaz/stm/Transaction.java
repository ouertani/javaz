package com.technozor.javaz.stm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by souertani on 7/3/2014.
 */
public class Transaction extends Context {

    private final Map<Ref<?>, Object> world = new HashMap<>();

    private final Set<Ref<?>> writes = new HashSet<>();

    private final  Map<Ref<?>, Integer> versions = new HashMap<>();

    private final int revision;

    public Transaction(int revision) {
        this.revision = revision;
    }

    @Override
    public <T> T retrieve(Ref<T> ref) {
        Ref<?> castRef = ref;

        if (!world.containsKey(castRef)) {
            world.put(castRef, ref.value);
        }
        return (T) world.get(castRef);
    }

    public <T> void store(Ref<T> ref, T t) {
        Ref<?> castRef = ref;
        world.put(castRef, t);
        writes.add(castRef);
    }

    boolean commit() {
        synchronized (this) {
            // TODO  validate
            Set<Map.Entry<Ref<?>, Object>> entries = world.entrySet();
            boolean needRetry = entries.stream().filter(this::hasDifferentVersion).findFirst().isPresent();

            if(!needRetry) {
                writes.forEach(ref -> {
                    Object o = world.get(ref);
                    ref.setContent(o, revision);
                });
            }
            return !needRetry ;
        }
    }

    private boolean hasDifferentVersion(Map.Entry<Ref<?>, Object> m) {
        Ref<?> key =  m.getKey();
        return  m.getKey().getContent().getVersion() != versions.get(key);
    }
}
