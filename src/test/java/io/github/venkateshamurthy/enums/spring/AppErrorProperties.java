package io.github.venkateshamurthy.enums.spring;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Please check application.yml for the error definitions
 */
@Component
@ConfigurationProperties(prefix = "app")
@Data @Getter @Setter
public class AppErrorProperties {
    private List<ErrorDefinition> errors;
    private List<ErrorDefinition> faults;
}
