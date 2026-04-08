package io.github.venkateshamurthy.enums.spring;

import io.github.venkateshamurthy.enums.examples.Errors;
import io.github.venkateshamurthy.enums.examples.Faults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * It is crucial for this registry config to have @SpringBootConfiguration
 * lest the application yml wont be read
 */
@SpringBootConfiguration
public class ErrorRegistryConfig {
    @Bean
    public CommandLineRunner initErrors(AppErrorProperties appErrorProperties) {
        return args -> {
            // Assuming mvn clean test
            if (appErrorProperties.getErrors() != null) {
                appErrorProperties.getErrors().forEach(ed-> Errors.create(ed.getName(), ed.getDescription(), ed.getStatus()));
            }
            if (appErrorProperties.getFaults() != null) {
                // Ensure Faults class also has registerSelf()
                appErrorProperties.getFaults().forEach(ed->new Faults(ed.getName(),ed.getDescription(),ed.getStatus()));
            }
        };
    }

    @Bean
    public StringToHttpStatusCodeConverter getStringToHttpStatusCodeConverter() {
        return new StringToHttpStatusCodeConverter();
    }
}
