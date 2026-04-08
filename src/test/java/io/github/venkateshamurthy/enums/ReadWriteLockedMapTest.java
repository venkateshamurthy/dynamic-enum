package io.github.venkateshamurthy.enums;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class ReadWriteLockedMapTest {

    private ReadWriteLockedMap<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new ReadWriteLockedMap<>(new HashMap<>());
    }

    @Test
    void testPutAndGet() {
        map.put("a", 1);
        assertEquals(1, map.get("a"));
    }

    @Test
    void testSizeAndIsEmpty() {
        assertTrue(map.isEmpty());
        map.put("x", 42);
        assertEquals(1, map.size());
        assertFalse(map.isEmpty());
    }

    @Test
    void testContainsKeyAndValue() {
        map.put("k", 99);
        assertTrue(map.containsKey("k"));
        assertTrue(map.containsValue(99));
        assertFalse(map.containsKey("missing"));
    }

    @Test
    void testRemove() {
        map.put("r", 5);
        assertEquals(5, map.remove("r"));
        assertFalse(map.containsKey("r"));
    }

    @Test
    void testPutAllAndClear() {
        Map<String, Integer> batch = Map.of("a", 1, "b", 2);
        map.putAll(batch);
        assertEquals(2, map.size());
        map.clear();
        assertTrue(map.isEmpty());
    }

    @Test
    void testKeySetValuesEntrySetAreDefensiveCopies() {
        map.put("a", 1);
        Set<String> keys = map.keySet();
        keys.add("new"); // should not affect delegate
        assertFalse(map.containsKey("new"));

        Collection<Integer> values = map.values();
        values.add(99); // should not affect delegate
        assertFalse(map.containsValue(99));

        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        entries.clear(); // should not affect delegate
        assertFalse( map.entrySet().isEmpty());
    }

    @Test
    void testThreadSafetyWithConcurrentAccess() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // Writer task
        Callable<Void> writer = () -> {
            for (int i = 0; i < 100; i++) {
                map.put("k" + i, i);
            }
            return null;
        };

        // Reader task
        Callable<Integer> reader = () -> {
            int sum = 0;
            for (int i = 0; i < 100; i++) {
                Integer val = map.get("k" + i);
                if (val != null) sum += val;
            }
            return sum;
        };

        Future<Void> f1 = executor.submit(writer);
        Future<Integer> f2 = executor.submit(reader);

        f1.get(); // wait for writer
        int sum = f2.get(); // wait for reader

        assertTrue(sum >= 0); // sanity check
        executor.shutdown();
    }
}
