package io.github.venkateshamurthy.enums;

import lombok.NonNull;
import org.apache.commons.collections4.KeyValue;
import org.apache.commons.collections4.keyvalue.UnmodifiableMapEntry;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A registry to capture internal detail of the class and {@link DynamicEnum} details.
 * @param klazz class type of the DynamicEnum holding.
 * @param ordinalCounter which is set with 0 for a given class type
 * @param map a registry of enum name to key value pair
 *
 * Note: This is deliberately kep as package friendly access with private constructor and is exclusively
 *            meant for only use within {@link DynamicEnum}
 */
record Internal(@NonNull Class<?> klazz, @NonNull AtomicInteger ordinalCounter,
                @NonNull Map<String, KeyValue<DynamicEnum<?>, Integer>> map) {
    /** A registry of the class type of enum to the {@link Internal} instances.*/
    private static final Map<Class<?>, Internal> instances = new ConcurrentHashMap<>();

    /**
     * Private Constructor used within
     * @param klass of the dynamic enum
     */
    private Internal(Class<?> klass) {
        this(klass, new AtomicInteger(0), Collections.synchronizedMap(new LinkedHashMap<>()));
    }

    /**
     * Register the class type, name and the dynamic enum itself within the registry
     * @param klass class of dynamic enum content type
     * @param name of the dynamic enum
     * @param dynamicEnum the dynamic enum itself
     * @throws RuntimeException if there is an attempt for duplicate registration
     */
    static void register(@NonNull final Class<?> klass, @NonNull final String name, @NonNull final DynamicEnum<?> dynamicEnum) {
        final Internal internal = instances.computeIfAbsent(klass, Internal::new);
        internal.map.compute(name, (nm, kv) -> {
            if (kv == null) return new UnmodifiableMapEntry<>(dynamicEnum, internal.ordinalCounter.getAndIncrement());
            throw new RuntimeException(klass.getName() + " - Duplicate instances are not allowed :" + name);
        });
    }

    /**
     * Returns the dynamic enum array give  its class type
     * @param clazz of the dynamic enum type
     * @param <T> type of the
     * @return an array of instances of type T
     */
    static <T extends DynamicEnum<T>> T[] values(@NonNull final Class<?> clazz) {
        var set = Stream.of(instances.get(clazz))
                .filter(Objects::nonNull)
                .flatMap(internal -> internal.map.values().stream())
                .sorted(Comparator.comparingInt(KeyValue::getValue))
                .map(KeyValue::getKey).collect(Collectors.toSet());
        var array = (T[]) Array.newInstance(clazz, set.size());
        return set.toArray(array);
    }

    /**
     * Returns the dynamic enum given its class and name
     * @param clazz of the dynamic enum type
     * @param name of the dynamic enum
     * @return {@link DynamicEnum}
     * @param <T> is the type of the dynamic enum
     */
    static <T extends DynamicEnum<T>> T valueOf(@NonNull final Class<?> clazz,
                                                @NonNull final String name) {
        return Stream.of(instances.get(clazz))
                .filter(Objects::nonNull)
                .flatMap(internal -> internal.map.values().stream())
                .map(KeyValue::getKey)
                .filter(e -> e.name().equalsIgnoreCase(name))
                .findFirst()
                .map(result -> (T) result)
                .orElseThrow(() -> new RuntimeException(clazz.getName() + " / " + name + "  is not found in this Enumerator"));
    }

    /**
     * Gets the ordinal number
     * @param clazz class of dynamic enum type held
     * @param name name of the dynamic enum
     * @return ordinal index within {@link Internal#map()}
     */
    static int ordinal(@NonNull final Class<?> clazz,
                       @NonNull final String name) {
        return Optional.ofNullable(instances.get(clazz))
                .map(internal -> internal.map)
                .map(m -> m.get(name).getValue())
                .orElseThrow();
    }
}
