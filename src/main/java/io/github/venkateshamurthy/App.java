package io.github.venkateshamurthy;

import io.github.venkateshamurthy.enums.examples.Faults;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
@Slf4j
public class App {
    /**
     * A simple testing main application
     * @param args to the program
     */
    public static void main(String[] args) {
        log.info("Checking the Dynamic Enum...");
        Faults DB_FAULT = Faults.builder().name("DB_FAULT").description("Database page fault")
                .status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        log.info("All faults:{}", Faults.allOf(Faults.class));
    }
}
