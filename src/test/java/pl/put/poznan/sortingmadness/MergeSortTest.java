package pl.put.poznan.sortingmadness;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pl.put.poznan.sortingmadness.logic.algorithms.MergeSort;

import java.util.*;

public class MergeSortTest {
    private MergeSort mergeSort;

    @BeforeEach
    void setUp() {
        mergeSort = new MergeSort();
    }

    @Test
    void shouldSortIntegersInAscendingOrder() {
        // given
        List<Integer> data = Arrays.asList(7, 2, 9, 4, 1);

        // when
        Map<String, Object> result = mergeSort.sortList(data, "asc", 0);
        @SuppressWarnings("unchecked")
        List<Integer> sortedData = (List<Integer>) result.get("sortedData");

        // then
        assertEquals(Arrays.asList(1, 2, 4, 7, 9), sortedData);
        assertTrue((Double) result.get("executionTime") >= 0);
    }

    @Test
    void shouldSortIntegersInDescendingOrder() {
        // given
        List<Integer> data = Arrays.asList(7, 2, 9, 4, 1);

        // when
        Map<String, Object> result = mergeSort.sortList(data, "desc", 0);
        @SuppressWarnings("unchecked")
        List<Integer> sortedData = (List<Integer>) result.get("sortedData");

        // then
        assertEquals(Arrays.asList(9, 7, 4, 2, 1), sortedData);
    }

    @Test
    void shouldSortStringsAlphabetically() {
        // given
        List<String> data = Arrays.asList("banana", "apple", "cherry");

        // when
        Map<String, Object> result = mergeSort.sortList(data, "asc", 0);
        @SuppressWarnings("unchecked")
        List<String> sortedData = (List<String>) result.get("sortedData");

        // then
        assertEquals(Arrays.asList("apple", "banana", "cherry"), sortedData);
    }

    @Test
    void shouldHandleEmptyList() {
        // given
        List<Integer> data = new ArrayList<>();

        // when
        Map<String, Object> result = mergeSort.sortList(data, "asc", 0);
        @SuppressWarnings("unchecked")
        List<Integer> sortedData = (List<Integer>) result.get("sortedData");

        // then
        assertTrue(sortedData.isEmpty());
    }

    @Test
    void shouldHandleSingleElementList() {
        // given
        List<Integer> data = Collections.singletonList(42);

        // when
        Map<String, Object> result = mergeSort.sortList(data, "asc", 0);
        @SuppressWarnings("unchecked")
        List<Integer> sortedData = (List<Integer>) result.get("sortedData");

        // then
        assertEquals(Collections.singletonList(42), sortedData);
    }

    @Test
    void shouldSortMapsBySingleKey() {
        // given
        List<Map<String, Integer>> data = new ArrayList<>();
        data.add(Map.of("age", 40));
        data.add(Map.of("age", 20));
        data.add(Map.of("age", 30));

        // when
        Map<String, Object> result = mergeSort.sort(data, Collections.singletonList("age"), "asc", 0);
        @SuppressWarnings("unchecked")
        List<Map<String, Integer>> sortedData = (List<Map<String, Integer>>) result.get("sortedData");

        // then
        assertEquals(20, sortedData.get(0).get("age"));
        assertEquals(30, sortedData.get(1).get("age"));
        assertEquals(40, sortedData.get(2).get("age"));
    }

    @Test
    void shouldSortMapsByMultipleKeys() {
        // given
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> person1 = new HashMap<>();
        person1.put("lastName", "Smith");
        person1.put("firstName", "John");

        Map<String, String> person2 = new HashMap<>();
        person2.put("lastName", "Smith");
        person2.put("firstName", "Adam");

        Map<String, String> person3 = new HashMap<>();
        person3.put("lastName", "Brown");
        person3.put("firstName", "James");

        data.add(person1);
        data.add(person2);
        data.add(person3);

        // when
        List<String> keys = Arrays.asList("lastName", "firstName");
        Map<String, Object> result = mergeSort.sort(data, keys, "asc", 0);

        // then
        @SuppressWarnings("unchecked")
        List<Map<String, String>> sortedData = (List<Map<String, String>>) result.get("sortedData");

        assertEquals("Brown", sortedData.get(0).get("lastName"));
        assertEquals("Smith", sortedData.get(1).get("lastName"));
        assertEquals("Smith", sortedData.get(2).get("lastName"));

        assertEquals("James", sortedData.get(0).get("firstName"));
        assertEquals("Adam", sortedData.get(1).get("firstName"));
        assertEquals("John", sortedData.get(2).get("firstName"));
    }

    @Test
    void shouldThrowExceptionForMissingKeyInMaps() {
        // given
        List<Map<String, String>> data = new ArrayList<>();
        data.add(Map.of("name", "Alice"));
        data.add(Map.of("age", "30")); // Missing "name" key

        // then
        assertThrows(IllegalArgumentException.class, () ->
                mergeSort.sort(data, Collections.singletonList("name"), "asc", 0)
        );
    }
}
