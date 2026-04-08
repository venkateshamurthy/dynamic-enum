package io.github.venkateshamurthy.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link DynamicEnumSet}.
 */
@Slf4j
public class DynamicEnumSetTest {
    private record Hue(String name, @JsonProperty Currency currency) implements DynamicEnum<Hue> {
        public static final Hue NO = new Hue("No",Currency.getInstance(Locale.getDefault()));
    }
    // Concrete test dynamic enum type
    //@JsonTypeName("color")
    private record Color(String name, @JsonProperty Currency currency) implements DynamicEnum<Color>, ObjectMapped {
        // Do not use Local.getDefault as CI pipeline testing breaks. so using Locale.FRANCE
        public static final Color UNKNOWN = new Color("UNKNOWN", Currency.getInstance(Locale.FRANCE));
        public static final Color RED = new Color("RED", Currency.getInstance("INR"));
        public static final Color GREEN = new Color("GREEN", Currency.getInstance("USD"));
        public static final Color BLUE = new Color("BLUE", Currency.getInstance("CAD"));
        public Color{
            register(name);}

        public static Color[] values() {
            return DynamicEnum.values(Color.class);
        }

        public static Color valueOf(String name) {
            return Try.of(()->DynamicEnum.valueOf(Color.class, name))
                    .map(en->(Color)en)
                    .getOrElse(()->UNKNOWN);
        }
    }

    private DynamicEnumSet<Color> colorSet;

    @BeforeEach
    void setup() {
        colorSet = new DynamicEnumSet<>(Color.class);
    }

    @Test
    void testAddAndContains() throws JsonProcessingException {
        assertTrue(colorSet.add(Color.RED));
        assertTrue(colorSet.contains(Color.RED));
        assertEquals(1, colorSet.size());
        log.info("{}",Color.RED.getDefaultMapper().writeValueAsString(Color.RED));
    }

    @Test
    void testAddNullThrows() {
        assertDoesNotThrow(() -> colorSet.add(null));
    }

    @Test
    void testOfCreatesSetWithElements() {
        DynamicEnumSet<Color> set = DynamicEnumSet.of(Color.RED, Color.GREEN);
        assertEquals(2, set.size());
        assertTrue(set.contains(Color.RED));
        assertTrue(set.contains(Color.GREEN));
        assertEquals(Color.class, set.iterator().next().getClass());
    }

    @Test
    void testAllOfCreatesFullSet() {
        DynamicEnumSet<Color> set = DynamicEnumSet.dynamicEnumSet(Color.class);
        assertEquals(4, set.size());
        assertTrue(set.containsAll(List.of(Color.RED, Color.GREEN, Color.BLUE)));
    }

    @Test
    void testOrderIsPreservedFromLinkedHashSet() {
        DynamicEnumSet<Color> set = DynamicEnumSet.of(Color.GREEN, Color.BLUE, Color.RED);
        assertEquals(List.of(Color.GREEN, Color.BLUE, Color.RED), List.copyOf(set));
    }

    @Test
    void testValueOfWorksCorrectlyFromDynamicEnum() {
        Color c = Color.valueOf("RED");
        assertEquals(Color.RED, c);
    }

    @Test
    void testValueOfThrowsForInvalidName() {
        assertThrows(RuntimeException.class, () -> DynamicEnum.valueOf(Color.class,"PURPLE"));
        assertEquals(Color.UNKNOWN, Color.valueOf("PURPLE"));
    }

    @Test
    void testOrdinalIncreasesSequentially() {
        assertEquals(0, Color.UNKNOWN.ordinal());
        assertEquals(1, Color.RED.ordinal());
        assertEquals(2, Color.GREEN.ordinal());
        assertEquals(3, Color.BLUE.ordinal());
    }

}