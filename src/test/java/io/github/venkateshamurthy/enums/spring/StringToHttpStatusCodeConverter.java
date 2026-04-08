package io.github.venkateshamurthy.enums.spring;

import io.github.venkateshamurthy.enums.examples.HttpStatusCode;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToHttpStatusCodeConverter implements Converter<String, HttpStatusCode> {
    @Override
    public HttpStatusCode convert(@NonNull final String source) {
        HttpStatusCode code = HttpStatusCode.valueOf(source);
        if (code == null) {
            throw new IllegalArgumentException("Unknown HttpStatusCode: " + source);
        }
        return code;
    }
}