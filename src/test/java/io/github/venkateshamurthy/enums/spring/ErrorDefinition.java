package io.github.venkateshamurthy.enums.spring;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.github.venkateshamurthy.enums.examples.HttpStatusCode;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@AllArgsConstructor(onConstructor = @__(@ConstructorBinding))
@Getter
@Setter
@NoArgsConstructor
public class ErrorDefinition {
    private String name;
    private String description;
    private HttpStatusCode status;
}