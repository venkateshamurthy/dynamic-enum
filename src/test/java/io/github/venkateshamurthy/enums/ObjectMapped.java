package io.github.venkateshamurthy.enums;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface ObjectMapped  {
    Map<Class<?>, ObjectMapper> objMappersMap = new ConcurrentHashMap<>();
    static ObjectMapper getDefaultObjectMapper(Class<?> clazz) {
        return objMappersMap.computeIfAbsent(clazz, klass -> {
            final SimpleModule module = new SimpleModule();
            module.registerSubtypes(klass);
            return new ObjectMapper().registerModule(module);
        });
    }
    default ObjectMapper getDefaultMapper(){
        return getDefaultObjectMapper(getClass());
    }
}
