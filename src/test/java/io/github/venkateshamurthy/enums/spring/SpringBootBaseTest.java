package io.github.venkateshamurthy.enums.spring;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
@EnableConfigurationProperties(AppErrorProperties.class)
@SpringBootTest(classes = ErrorRegistryConfig.class)
@NoArgsConstructor()
@Setter @Getter
public class SpringBootBaseTest {
    @Autowired private AppErrorProperties appErrorProperties;
}
