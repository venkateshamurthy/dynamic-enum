package io.github.venkateshamurthy.enums.spring;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.github.venkateshamurthy.enums.examples.HttpStatusCode;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ErrorDefinition {
    private String name;
    private String description;
    private HttpStatusCode status;
}