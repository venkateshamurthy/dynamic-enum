package io.github.venkateshamurthy.enums.examples;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static io.github.venkateshamurthy.enums.examples.HttpStatusCode.*;
import static org.junit.jupiter.api.Assertions.*;

public class HttpStatusCodeTest {
    @Test
    void test(){
        assertThrows(RuntimeException.class, () -> valueOf(10000));
        assertTrue(values().length > 0);
        assertEquals(BAD_REQUEST, valueOf("BAD_REQUEST"));
        assertEquals(BAD_REQUEST, valueOf(400));
    }

    @Test
    void testIsMethods() {
        // 1xx informational
        assertTrue(CONTINUE.is1xxInformational());   // 100
        assertFalse(OK.is1xxInformational());        // 200

        // 2xx successful
        assertTrue(OK.is2xxSuccessful());            // 200
        assertFalse(CONTINUE.is2xxSuccessful());     // 100
        assertFalse(MOVED_PERMANENTLY.is2xxSuccessful()); // 301

        // 3xx redirection
        assertTrue(MOVED_PERMANENTLY.is3xxRedirection()); // 301
        assertFalse(OK.is3xxRedirection());               // 200
        assertFalse(BAD_REQUEST.is3xxRedirection());      // 400

        // 4xx client error
        assertTrue(BAD_REQUEST.is4xxClientError());       // 400
        assertFalse(CONTINUE.is4xxClientError());         // 100
        assertFalse(BAD_GATEWAY.is4xxClientError());      // 502

        // 5xx server error
        assertTrue(BAD_GATEWAY.is5xxServerError());       // 502
        assertFalse(OK.is5xxServerError());               // 200
        assertFalse(BAD_REQUEST.is5xxServerError());      // 400
    }
}
