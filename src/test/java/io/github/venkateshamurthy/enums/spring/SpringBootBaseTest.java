package io.github.venkateshamurthy.enums.spring;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
@EnableConfigurationProperties(AppErrorProperties.class)
@SpringBootTest(classes = ErrorRegistryConfig.class)
@NoArgsConstructor(onConstructor = @__(@Autowired))
public class SpringBootBaseTest {
    private AppErrorProperties appErrorProperties;
}
