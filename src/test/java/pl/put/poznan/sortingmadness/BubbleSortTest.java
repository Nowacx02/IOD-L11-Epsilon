package pl.put.poznan.sortingmadness;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import pl.put.poznan.sortingmadness.logic.algorithms.BubbleSort;
import pl.put.poznan.sortingmadness.logic.SortingStrategy;

import java.util.*;

public class BubbleSortTest {
    private BubbleSort bubbleSort;

    @BeforeEach
    void setUp() {
        bubbleSort = new BubbleSort();
    }

    @Test
    void shouldSortIntegersInAscendingOrder() {
        // given
        List<Integer> data = Arrays.asList(5, 3, 8, 1, 2, 9, 4);

        // when
        Map<String, Object> result = bubbleSort.sortList(data, "asc", 0);
        @SuppressWarnings("unchecked")
        List<Integer> sortedData = (List<Integer>) result.get("sortedData");

        // then
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 8, 9), sortedData);
        assertTrue((Double) result.get("executionTime") >= 0);
    }

    @Test
    void shouldSortIntegersInDescendingOrder() {
        // given
        List<Integer> data = Arrays.asList(5, 3, 8, 1, 2, 9, 4);

        // when
        Map<String, Object> result = bubbleSort.sortList(data, "desc", 0);
        @SuppressWarnings("unchecked")
        List<Integer> sortedData = (List<Integer>) result.get("sortedData");

        // then
        assertEquals(Arrays.asList(9, 8, 5, 4, 3, 2, 1), sortedData);
    }

    @Test
    void shouldSortStringsAlphabetically() {
        // given
        List<String> data = Arrays.asList("banana", "apple", "cherry", "date");

        // when
        Map<String, Object> result = bubbleSort.sortList(data, "asc", 0);
        @SuppressWarnings("unchecked")
        List<String> sortedData = (List<String>) result.get("sortedData");

        // then
        assertEquals(Arrays.asList("apple", "banana", "cherry", "date"), sortedData);
    }

    @Test
    void shouldRespectMaxIterations() {
        // given
        List<Integer> data = Arrays.asList(5, 4, 3, 2, 1);

        // when
        Map<String, Object> result = bubbleSort.sortList(data, "asc", 3);
        @SuppressWarnings("unchecked")
        List<Integer> sortedData = (List<Integer>) result.get("sortedData");

        // then
        assertNotEquals(Arrays.asList(1, 2, 3, 4, 5), sortedData);
    }

    @Test
    void shouldSortMapsBySingleKey() {
        // given
        List<Map<String, Integer>> data = new ArrayList<>();
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("age", 25);
        Map<String, Integer> map2 = new HashMap<>();
        map2.put("age", 20);
        Map<String, Integer> map3 = new HashMap<>();
        map3.put("age", 30);
        data.add(map1);
        data.add(map2);
        data.add(map3);

        // when
        Map<String, Object> result = bubbleSort.sort(data, Collections.singletonList("age"), "asc", 0);

        // then
        @SuppressWarnings("unchecked")
        List<Map<String, Integer>> sortedData = (List<Map<String, Integer>>) result.get("sortedData");
        assertEquals(20, sortedData.get(0).get("age"));
        assertEquals(25, sortedData.get(1).get("age"));
        assertEquals(30, sortedData.get(2).get("age"));
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
        Map<String, Object> result = bubbleSort.sort(data, keys, "asc", 0);

        // then
        @SuppressWarnings("unchecked")
        List<Map<String, String>> sortedData = (List<Map<String, String>>) result.get("sortedData");
        assertEquals("Brown", sortedData.get(0).get("lastName"));
        assertEquals("Smith", sortedData.get(1).get("lastName"));
        assertEquals("Smith", sortedData.get(2).get("lastName"));
        assertEquals("Adam", sortedData.get(1).get("firstName")); // Should come before John
    }

    @Test
    void shouldThrowExceptionForNullInput() {
        assertThrows(IllegalArgumentException.class, () ->
                bubbleSort.sortList(null, "asc", 0)
        );
    }

    @Test
    void shouldThrowExceptionForNullDirection() {
        // given
        List<Integer> data = Arrays.asList(1, 2, 3);

        // then
        assertThrows(IllegalArgumentException.class, () ->
                bubbleSort.sortList(data, null, 0)
        );
    }

    @Test
    void shouldHandleEmptyList() {
        // given
        List<Integer> data = new ArrayList<>();

        // when
        Map<String, Object> result = bubbleSort.sortList(data, "asc", 0);

        // then
        @SuppressWarnings("unchecked")
        List<Integer> sortedData = (List<Integer>) result.get("sortedData");
        assertTrue(sortedData.isEmpty());
    }

    @Test
    void shouldHandleSingleElementList() {
        // given
        List<Integer> data = Collections.singletonList(1);

        // when
        Map<String, Object> result = bubbleSort.sortList(data, "asc", 0);

        // then
        @SuppressWarnings("unchecked")
        List<Integer> sortedData = (List<Integer>) result.get("sortedData");
        assertEquals(1, sortedData.size());
        assertEquals(Integer.valueOf(1), sortedData.get(0));
    }

    @Test
    void shouldThrowExceptionForMissingKeyInMaps() {
        // given
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("name", "John");
        Map<String, String> map2 = new HashMap<>();
        map2.put("age", "25"); // Missing "name" key
        data.add(map1);
        data.add(map2);

        // then
        assertThrows(IllegalArgumentException.class, () ->
                bubbleSort.sort(data, Collections.singletonList("name"), "asc", 0)
        );
    }
}