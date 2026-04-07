package io.github.venkateshamurthy.enums;

import io.github.venkateshamurthy.enums.examples.Faults;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AppTest {

    @Test
    void testApp() {
        assertTrue(Arrays.toString(Faults.values()).length() >0);
    }
}
