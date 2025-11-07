package io.github.venkateshamurthy.enums;

import io.github.venkateshamurthy.App;
import io.github.venkateshamurthy.enums.examples.Faults;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AppTest {

    @Test
    void testApp() {
        App.main(null);
        Faults DB_FAULT = Faults.builder().name("UNEXPECTED_ERROR").description("Unexpected Database page fault")
                .status(HttpStatus.SERVICE_UNAVAILABLE).build();
        assertTrue(Arrays.stream(Faults.values())
                .anyMatch(e -> e.getDescription().equals(DB_FAULT.getDescription())));
    }
}
