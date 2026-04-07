package io.github.venkateshamurthy.enums.spring;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
@Getter @Setter
@NoArgsConstructor
public class ErrorDefinition {
    private String name;
    private String description;
    private HttpStatus status;
}