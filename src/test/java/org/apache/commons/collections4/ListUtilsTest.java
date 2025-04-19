package org.apache.commons.collections4;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ListUtilsTest {

    @Test
    void testIntersection() {
        List<String> list1 = Arrays.asList("A", "B", "C");
        List<String> list2 = Arrays.asList("B", "C", "D");
        assertEquals(Arrays.asList("B", "C"), ListUtils.intersection(list1, list2));
    }

    @Test
    void testSubtract() {
        List<String> list1 = Arrays.asList("A", "B", "C");
        List<String> list2 = Arrays.asList("B");
        assertEquals(Arrays.asList("A", "C"), ListUtils.subtract(list1, list2));
    }

    @Test
    void testIsEqualList() {
        assertTrue(ListUtils.isEqualList(Arrays.asList(1, 2, 3), Arrays.asList(1, 2, 3)));
        assertFalse(ListUtils.isEqualList(Arrays.asList(1, 2), Arrays.asList(1, 2, 3)));
    }

    @Test
    void testEmptyIfNull() {
        assertEquals(Collections.emptyList(), ListUtils.emptyIfNull(null));
        assertEquals(Arrays.asList(1, 2), ListUtils.emptyIfNull(Arrays.asList(1, 2)));
    }

    @Test
    void testPartition() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        List<List<Integer>> partitions = ListUtils.partition(list, 2);
        assertEquals(3, partitions.size());
        assertEquals(Arrays.asList(1, 2), partitions.get(0));
        assertEquals(Arrays.asList(3, 4), partitions.get(1));
        assertEquals(Arrays.asList(5), partitions.get(2));
    }

    @Test
    void testSelect() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> selected = ListUtils.select(list, input -> input % 2 == 0);
        assertEquals(Arrays.asList(2, 4), selected);
    }
}