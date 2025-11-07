package io.github.venkateshamurthy.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vavr.control.Try;
import lombok.Builder;

//@JsonTypeName("TestDynamicEnum")
public final class SampleDynamicEnum extends DynamicEnum<SampleDynamicEnum> {
    public static final SampleDynamicEnum ALPHA = new SampleDynamicEnum("ALPHA");
    public static final SampleDynamicEnum BETA  = new SampleDynamicEnum("BETA");
    public static final SampleDynamicEnum UNKNOWN = new SampleDynamicEnum("UNKNOWN");

    public static SampleDynamicEnum[] values() {
        return DynamicEnum.values(SampleDynamicEnum.class, SampleDynamicEnum[]::new);
    }

    //@JsonCreator
    @Builder
    private SampleDynamicEnum(@JsonProperty String name) {
        super(name);
    }

   /** @JsonCreator
    public static TestDynamicEnum valueOf(Map<String, Object> mapObj) {
        return Try.of(()->DynamicEnum.valueOf(TestDynamicEnum.class, MapUtils.getString(mapObj,"name")))
                .getOrElse(()->UNKNOWN);
    }*/
    @JsonCreator
    public static SampleDynamicEnum valueOf(@JsonProperty String name) {
        return Try.of(()->DynamicEnum.valueOf(SampleDynamicEnum.class, name))
                .getOrElse(()->UNKNOWN);
    }
}