package io.github.venkateshamurthy.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;

/**
 * DynamicEnum is a convenience structure over static {@link Enum} to add dynamically more instances.
 * Keeping the implementation classes as final and the constructor private can keep similar semantics to the static Enum.
 * @param <E> a type of self-referential generic.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public interface DynamicEnum<E extends DynamicEnum<E>>  {
    /**
     * Gets the name
     * @return name
     */
    @JsonValue @JsonProperty
    @Schema(description = "Name of the fault/error/exception")
    String name() ;

    /**
     * Gets the ordinal that provides the declaration ordered index.
     * @return ordinal.
     */
    default int ordinal() {
        return Internal.ordinal(getClass(), name());
    }

    /**
     * {@link Internal#register(Class, String, DynamicEnum) register} with internal registry this instance with name;
     * the class type is inferred automatically. In the usecases where the name is needed to register such as compact
     * constructor of record; this method can be utilized.
     */
    default void register(@NonNull final String name) {
        Internal.register(getClass(), name, this);
    }

    /**
     * Register the completely constructed instance.
     * You would use this in a post constructor call (if using spring) or
     * perhaps in the Lombok custom builders build logic post the construction.
     * @return the self
     */
    default E registerSelf() {
        register(name());
        return (E) this;
    }

    /**
     * values provides DynamicEnum instances array for a given type
     *
     * @param clazz is the dynamic enum class
     * @param <T> type of the dynamic enum
     * @return array of the type
     */
    static <T extends DynamicEnum<T>> T[] values(@NonNull final Class<?> clazz) {
        return Internal.values(clazz);
    }


    /**
     * valueOf provides the dynamic enum corresponding to the name.
     *
     * @param clazz is the dynamic enum class
     * @param name of the dynamic enum for which the corresponding subcclass of {@link DynamicEnum} is to fetched
     * @return T
     * @param <T> is basically the &lt;T extends DynamicEnum&lt;T&gt;&gt;
     * @throws RuntimeException when name does not match what is cached with in
     */
    static <T extends DynamicEnum<T>> T valueOf(@NonNull final Class<?> clazz,
                                                @NonNull final String name) {
        return Internal.valueOf(clazz, name);
    }
}