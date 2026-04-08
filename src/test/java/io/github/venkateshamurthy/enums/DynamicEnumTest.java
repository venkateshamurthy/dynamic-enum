package io.github.venkateshamurthy.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import static io.github.venkateshamurthy.enums.TestDynamicEnum.*;
import static org.junit.jupiter.api.Assertions.*;
@Slf4j
class DynamicEnumTest {

    @Test
    void testNameAndOrdinal() {
        assertEquals("ALPHA", ALPHA.name());
        assertEquals(0, ALPHA.ordinal());

        assertEquals("BETA", BETA.name());
        assertEquals(1, BETA.ordinal());

        assertEquals("UNKNOWN", UNKNOWN.name());
        assertEquals(2, UNKNOWN.ordinal());
    }

    @Test
    void testEqualityAndHashCode() {
        // Same reference
        assertEquals(ALPHA, ALPHA);
        assertEquals(ALPHA.hashCode(), ALPHA.hashCode());

        // Different constants are not equal
        assertNotEquals(ALPHA, BETA);
    }

    @Test
    void testToStringContainsNameOnly() {
        String repr = ALPHA.toString();
        assertTrue(repr.contains("ALPHA"));
        assertFalse(repr.contains("ordinal"));
    }

    record EmptyEnum(String name) implements DynamicEnum<EmptyEnum>{
        public EmptyEnum {
            register(name);}
    }
    record UncapturedEmptyEnum(String name) implements DynamicEnum<UncapturedEmptyEnum>{
        //public EmptyEnum {captureInstance(name);}
    }
    @Test
    void testEmptyInstanceSet(){
        assertEquals(0, DynamicEnum.values(UncapturedEmptyEnum.class).length);
        assertEquals(0, DynamicEnum.values(EmptyEnum.class).length);
    }

    @Test
    void testDuplicateInstanceThrows() throws NoSuchMethodException {
        final Constructor<TestDynamicEnum> c = TestDynamicEnum.class.getDeclaredConstructor(String.class);
        c.setAccessible(true);
        InvocationTargetException ex = assertThrows(InvocationTargetException.class, () -> {
            c.newInstance("ALPHA");
        });
        assertInstanceOf(RuntimeException.class, ex.getCause());
        assertEquals(TestDynamicEnum.class.getName() + " - Duplicate instances are not allowed :ALPHA",
                ex.getCause().getMessage());
        c.setAccessible(false);
    }

    @Test
    void testValues() {
        Color[] colors = Color.values();
        assertEquals(4, colors.length);
        Set<String> colorSet =  Set.of("RED", "GREEN", "BLUE", "UNKNOWN");
        assertEquals(colorSet, Arrays.stream(colors).map(DynamicEnum::name).collect(Collectors.toSet()));
    }

    @Test
    @SneakyThrows
    void testSerDeser() {
        TestDynamicEnum testEnum = UNKNOWN;
        final ObjectMapper mapper = testEnum.getDefaultMapper();
        final String alpha = mapper.writeValueAsString(ALPHA);
        //assertEquals("{\"type\":\"TestDynamicEnum\",\"name\":\"ALPHA\"}", alpha);
        assertEquals("[\"TestDynamicEnum\",\"ALPHA\"]", alpha);
        final TestDynamicEnum enumAlpha = mapper.readValue(alpha, TestDynamicEnum.class);
        assertEquals(ALPHA, enumAlpha);
    }

    @AfterAll
    static void cleanup() throws Exception {
        // Remove TestDynamicEnum entries from DynamicEnum.values map
        Field valuesField = Internal.class.getDeclaredField("instances");
        valuesField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<Class<? extends DynamicEnum>, ?> values =
                (Map<Class<? extends DynamicEnum>, ?>) valuesField.get(null);
        values.remove(TestDynamicEnum.class);
    }

    private record Color(@JsonProperty String name, @JsonProperty Currency currency)  implements DynamicEnum<Color> {
        public static final Color UNKNOWN = new Color("UNKNOWN", Currency.getInstance(Locale.FRANCE));
        public static final Color RED = new Color("RED", Currency.getInstance("INR"));
        public static final Color GREEN = new Color("GREEN", Currency.getInstance("USD"));
        public static final Color BLUE = new Color("BLUE", Currency.getInstance("CAD"));
        public Color{
            register(name);}
        public static Color[] values() {
            return DynamicEnum.values(Color.class);
        }

        public static Color valueOf(String name){
            return DynamicEnum.valueOf(Color.class, name);
        }

    }
}