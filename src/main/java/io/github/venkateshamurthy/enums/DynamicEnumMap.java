package io.github.venkateshamurthy.enums;

import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;

/**
 * A Map&lt;DynamicEnum&lt;K&gt;, V&gt; along the lines of EnumMap.
 * @param <K> key which is of the form K extends DynamicEnum&lt;K&gt;
 * @param <V> value
 */
@RequiredArgsConstructor
public class DynamicEnumMap<K extends DynamicEnum<K>, V> extends LinkedHashMap<K,V> {
    /** The subclass of {@link DynamicEnum} to which all the keys of this map MUST exactly match with.*/
    private final Class<K> enumClass;

    /**
     * Creates a {@link DynamicEnumMap}
     *
     * @param cls is Class&lt;A&gt;
     * @param a instance of type A
     * @param b instance of type B
     * @return DynamicEnumMap
     * @param <A> keu type
     * @param <B> value type
     */
    public static <A extends DynamicEnum<A>, B> DynamicEnumMap<A,B> of(Class<A> cls, A a, B b) {
        return new DynamicEnumMap<A, B>(cls).add(a, b);
    }

    /**
     * Creates a {@link DynamicEnumMap}
     *
     * @param cls is Class&lt;A&gt;
     * @param a1 instance of type A
     * @param b1 instance of type B
     * @param a2 instance of type A
     * @param b2 instance of type B
     * @return DynamicEnumMap
     * @param <A> keu type
     * @param <B> value type
     */
    public static <A extends DynamicEnum<A>, B> DynamicEnumMap<A,B> of(Class<A> cls, A a1, B b1, A a2, B b2) {
        return new DynamicEnumMap<A, B>(cls).add(a1, b1).add(a2, b2);
    }

    /**
     * Creates a {@link DynamicEnumMap}
     *
     * @param cls is Class&lt;A&gt;
     * @param a1 instance of type A
     * @param b1 instance of type B
     * @param a2 instance of type A
     * @param b2 instance of type B
     * @param a3 instance of type A
     * @param b3 instance of type B
     * @return DynamicEnumMap
     * @param <A> keu type
     * @param <B> value type
     */
    public static <A extends DynamicEnum<A>, B> DynamicEnumMap<A,B> of(Class<A> cls, A a1, B b1, A a2, B b2, A a3, B b3) {
        return new DynamicEnumMap<A, B>(cls).add(a1, b1).add(a2, b2).add(a3, b3);
    }

    /**
     * A fluent method to put key value pair to the map
     *
     * @param k key
     * @param v value
     * @return this
     */
    public DynamicEnumMap<K, V> add(K k, V v) {
        this.put(k, v);
        return this;
    }
}