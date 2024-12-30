package pl.put.poznan.sortingmadness;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pl.put.poznan.sortingmadness.logic.algorithms.InsertionSort;
import java.util.*;

public class InsertionSortTest {
    private InsertionSort insertionSort;

    @BeforeEach
    void setUp() {
        insertionSort = new InsertionSort();
    }

    @Test
    void shouldSortIntegersInAscendingOrder() {
        // given
        List<Integer> data = Arrays.asList(8, 3, 1, 5, 2);

        // when
        Map<String, Object> result = insertionSort.sortList(data, "asc", 0);
        @SuppressWarnings("unchecked")
        List<Integer> sortedData = (List<Integer>) result.get("sortedData");

        // then
        assertEquals(Arrays.asList(1, 2, 3, 5, 8), sortedData);
        assertTrue((Double) result.get("executionTime") >= 0);
    }

    @Test
    void shouldSortIntegersInDescendingOrder() {
        // given
        List<Integer> data = Arrays.asList(8, 3, 1, 5, 2);

        // when
        Map<String, Object> result = insertionSort.sortList(data, "desc", 0);
        @SuppressWarnings("unchecked")
        List<Integer> sortedData = (List<Integer>) result.get("sortedData");

        // then
        assertEquals(Arrays.asList(8, 5, 3, 2, 1), sortedData);
    }

    @Test
    void shouldSortStringsAlphabetically() {
        // given
        List<String> data = Arrays.asList("pear", "apple", "orange");

        // when
        Map<String, Object> result = insertionSort.sortList(data, "asc", 0);
        @SuppressWarnings("unchecked")
        List<String> sortedData = (List<String>) result.get("sortedData");

        // then
        assertEquals(Arrays.asList("apple", "orange", "pear"), sortedData);
    }

    @Test
    void shouldRespectMaxIterations() {
        // given
        List<Integer> data = Arrays.asList(5, 4, 3, 2, 1);

        // when
        Map<String, Object> result = insertionSort.sortList(data, "asc", 2);
        @SuppressWarnings("unchecked")
        List<Integer> sortedData = (List<Integer>) result.get("sortedData");

        // then
        assertNotEquals(Arrays.asList(1, 2, 3, 4, 5), sortedData);
    }

    @Test
    void shouldSortMapsBySingleKey() {
        // given
        List<Map<String, Integer>> data = new ArrayList<>();
        data.add(Map.of("age", 30));
        data.add(Map.of("age", 20));
        data.add(Map.of("age", 25));

        // when
        Map<String, Object> result = insertionSort.sort(data, Collections.singletonList("age"), "asc", 0);
        @SuppressWarnings("unchecked")
        List<Map<String, Integer>> sortedData = (List<Map<String, Integer>>) result.get("sortedData");

        // then
        assertEquals(20, sortedData.get(0).get("age"));
        assertEquals(25, sortedData.get(1).get("age"));
        assertEquals(30, sortedData.get(2).get("age"));
    }

    @Test
    void shouldThrowExceptionForNullInput() {
        assertThrows(IllegalArgumentException.class, () ->
                insertionSort.sortList(null, "asc", 0)
        );
    }

    @Test
    void shouldThrowExceptionForNullDirection() {
        // given
        List<Integer> data = Arrays.asList(1, 2, 3);

        // then
        assertThrows(IllegalArgumentException.class, () ->
                insertionSort.sortList(data, null, 0)
        );
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
        Map<String, Object> result = insertionSort.sort(data, keys, "asc", 0);

        // then
        @SuppressWarnings("unchecked")
        List<Map<String, String>> sortedData = (List<Map<String, String>>) result.get("sortedData");

        // Verify order by lastName (primary key)
        assertEquals("Brown", sortedData.get(0).get("lastName"));
        assertEquals("Smith", sortedData.get(1).get("lastName"));
        assertEquals("Smith", sortedData.get(2).get("lastName"));

        // Verify order by firstName (secondary key) when lastName is equal
        assertEquals("James", sortedData.get(0).get("firstName")); // "Brown"
        assertEquals("Adam", sortedData.get(1).get("firstName"));  // "Smith"
        assertEquals("John", sortedData.get(2).get("firstName"));  // "Smith"
    }


    @Test
    void shouldHandleEmptyList() {
        // given
        List<Integer> data = new ArrayList<>();

        // when
        Map<String, Object> result = insertionSort.sortList(data, "asc", 0);
        @SuppressWarnings("unchecked")
        List<Integer> sortedData = (List<Integer>) result.get("sortedData");

        // then
        assertTrue(sortedData.isEmpty());
    }

    @Test
    void shouldHandleSingleElementList() {
        // given
        List<Integer> data = Collections.singletonList(1);

        // when
        Map<String, Object> result = insertionSort.sortList(data, "asc", 0);
        @SuppressWarnings("unchecked")
        List<Integer> sortedData = (List<Integer>) result.get("sortedData");

        // then
        assertEquals(Collections.singletonList(1), sortedData);
    }

    @Test
    void shouldThrowExceptionForMissingKeyInMaps() {
        // given
        List<Map<String, String>> data = new ArrayList<>();
        data.add(Map.of("name", "John"));
        data.add(Map.of("age", "25")); // Missing "name" key

        // then
        assertThrows(IllegalArgumentException.class, () ->
                insertionSort.sort(data, Collections.singletonList("name"), "asc", 0)
        );
    }
}
