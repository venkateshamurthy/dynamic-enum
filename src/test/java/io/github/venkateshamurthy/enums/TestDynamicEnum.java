package io.github.venkateshamurthy.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vavr.control.Try;

//@JsonTypeName("TestDynamicEnum")
public record TestDynamicEnum(@JsonProperty String name) implements DynamicEnum<TestDynamicEnum>,ObjectMapped {

    public static final TestDynamicEnum ALPHA = new TestDynamicEnum("ALPHA");
    public static final TestDynamicEnum BETA  = new TestDynamicEnum("BETA");
    public static final TestDynamicEnum UNKNOWN = new TestDynamicEnum("UNKNOWN");

    public TestDynamicEnum {
        register(name);
    }

    public static TestDynamicEnum[] values() {
        return DynamicEnum.values(TestDynamicEnum.class);
    }

    @JsonCreator
    public static TestDynamicEnum valueOf(@JsonProperty String name) {
        return DynamicEnum.valueOf(TestDynamicEnum.class, name, ()->UNKNOWN);
    }
}