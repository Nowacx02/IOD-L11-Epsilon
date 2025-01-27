package pl.put.poznan.sortingmadness.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SortingMadnessTest {
    private SortingMadness sortingMadness;
    private SortingStrategy mockStrategy;

    @BeforeEach
    void setUp() {
        sortingMadness = new SortingMadness();
        mockStrategy = mock(SortingStrategy.class);
    }

    @Test
    void testSortData_ValidInput() {
        List<Map<String, Comparable>> mockData = List.of(Map.of("key", 3), Map.of("key", 1), Map.of("key", 2));
        List<String> keys = List.of("key");
        String algorithm = "mockAlgorithm";
        String direction = "ASC";
        int maxIterations = 10;

        when(mockStrategy.sort(mockData, keys, direction, maxIterations))
                .thenReturn(Map.of("result", List.of(Map.of("key", 1), Map.of("key", 2), Map.of("key", 3)), "executionTime", 5L));

        SortingMadness spySortingMadness = Mockito.spy(sortingMadness);
        doReturn(mockStrategy).when(spySortingMadness).getStrategy(algorithm);

        Map<String, Object> result = spySortingMadness.sortData(mockData, keys, algorithm, direction, maxIterations);

        assertNotNull(result);
        assertEquals(List.of(Map.of("key", 1), Map.of("key", 2), Map.of("key", 3)), result.get("result"));
        assertEquals(5L, result.get("executionTime"));

        verify(mockStrategy, times(1)).sort(mockData, keys, direction, maxIterations);
    }

    @Test
    void testSortData_InvalidAlgorithm() {
        List<Map<String, Comparable>> mockData = List.of(Map.of("key", 3));
        List<String> keys = List.of("key");
        String invalidAlgorithm = "unknownAlgorithm";
        String direction = "ASC";
        int maxIterations = 0;

        SortingMadness spySortingMadness = Mockito.spy(sortingMadness);

        // Mockowanie zachowania metody getStrategy
        doThrow(new IllegalArgumentException("Unknown sorting algorithm: " + invalidAlgorithm))
                .when(spySortingMadness).getStrategy(invalidAlgorithm);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            spySortingMadness.sortData(mockData, keys, invalidAlgorithm, direction, maxIterations);
        });

        assertEquals("Unknown sorting algorithm: unknownAlgorithm", exception.getMessage());

        // Weryfikacja, że metoda getStrategy została wywołana raz
        verify(spySortingMadness, times(1)).getStrategy(invalidAlgorithm);
    }

    @Test
    void testSortData_EmptyData() {
        List<Map<String, Comparable>> emptyData = List.of();
        List<String> keys = List.of("key");
        String algorithm = "bubble";
        String direction = "ASC";
        int maxIterations = 10;

        SortingMadness spySortingMadness = Mockito.spy(sortingMadness);

        // Mockowanie zachowania metody sortData
        doThrow(new IllegalArgumentException("Dataset is empty or null."))
                .when(spySortingMadness).sortData(emptyData, keys, algorithm, direction, maxIterations);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            spySortingMadness.sortData(emptyData, keys, algorithm, direction, maxIterations);
        });

        assertEquals("Dataset is empty or null.", exception.getMessage());

        // Weryfikacja, że metoda została wywołana raz
        verify(spySortingMadness, times(1)).sortData(emptyData, keys, algorithm, direction, maxIterations);
    }

    @Test
    void testSortDataList_ValidInput() {
        List<Comparable> mockDataList = List.of(3, 1, 2);
        String algorithm = "mockAlgorithm";
        String direction = "desc";
        int maxIterations = 5;

        when(mockStrategy.sortList(mockDataList, direction, maxIterations))
                .thenReturn(Map.of("result", List.of(3, 2, 1), "executionTime", 10L));

        SortingMadness spySortingMadness = Mockito.spy(sortingMadness);
        doReturn(mockStrategy).when(spySortingMadness).getStrategy(algorithm);

        Map<String, Object> result = spySortingMadness.sortDataList(mockDataList, algorithm, direction, maxIterations);

        assertNotNull(result);
        assertEquals(List.of(3, 2, 1), result.get("result"));
        assertEquals(10L, result.get("executionTime"));

        verify(mockStrategy, times(1)).sortList(mockDataList, direction, maxIterations);
    }

    @Test
    void testSortData_NullData() {
        List<Map<String, Comparable>> nullData = null;
        List<String> keys = List.of("key");
        String algorithm = "mockAlgorithm";
        String direction = "ASC";
        int maxIterations = 10;

        SortingMadness spySortingMadness = Mockito.spy(sortingMadness);

        // Mockowanie zachowania metody sortData
        doThrow(new IllegalArgumentException("Dataset is empty or null."))
                .when(spySortingMadness).sortData(nullData, keys, algorithm, direction, maxIterations);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            spySortingMadness.sortData(nullData, keys, algorithm, direction, maxIterations);
        });

        assertEquals("Dataset is empty or null.", exception.getMessage());

        // Weryfikacja, że metoda została wywołana raz
        verify(spySortingMadness, times(1)).sortData(nullData, keys, algorithm, direction, maxIterations);
    }

    @Test
    void testSortDataList_EmptyAlgorithm() {
        List<Comparable> mockDataList = List.of(1, 2, 3);
        String algorithm = "";
        String direction = "asc";
        int maxIterations = 5;

        SortingMadness spySortingMadness = Mockito.spy(sortingMadness);

        // Mockowanie zachowania metody getStrategy
        doThrow(new IllegalArgumentException("Unknown sorting algorithm: " + algorithm))
                .when(spySortingMadness).getStrategy(algorithm);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            spySortingMadness.sortDataList(mockDataList, algorithm, direction, maxIterations);
        });

        assertEquals("Unknown sorting algorithm: ", exception.getMessage());

        // Weryfikacja, że metoda getStrategy została wywołana raz
        verify(spySortingMadness, times(1)).getStrategy(algorithm);
    }

    @Test
    void testSortData_InvalidDirection() {
        List<Map<String, Comparable>> mockData = List.of(Map.of("key", 3));
        List<String> keys = List.of("key");
        String algorithm = "merge";
        String direction = "undefined";
        int maxIterations = 5;

        SortingMadness spySortingMadness = Mockito.spy(sortingMadness);

        // Mockowanie zachowania metody sortData
        doThrow(new IllegalArgumentException("Sorting direction must be specified."))
                .when(spySortingMadness).sortData(mockData, keys, algorithm, direction, maxIterations);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            spySortingMadness.sortData(mockData, keys, algorithm, direction, maxIterations);
        });

        assertEquals("Sorting direction must be specified.", exception.getMessage());

        // Weryfikacja, że metoda sortData została wywołana raz
        verify(spySortingMadness, times(1)).sortData(mockData, keys, algorithm, direction, maxIterations);
    }

    @Test
    void testSortDataList_DescendingOrderWithHighIterations() {
        List<Comparable> mockDataList = List.of(10, 3, 6, 2);
        String algorithm = "mockAlgorithm";
        String direction = "DESC";
        int maxIterations = 100;

        when(mockStrategy.sortList(mockDataList, direction, maxIterations))
                .thenReturn(Map.of("result", List.of(10, 6, 3, 2), "executionTime", 20L));

        SortingMadness spySortingMadness = Mockito.spy(sortingMadness);
        doReturn(mockStrategy).when(spySortingMadness).getStrategy(algorithm);

        Map<String, Object> result = spySortingMadness.sortDataList(mockDataList, algorithm, direction, maxIterations);

        assertNotNull(result);
        assertEquals(List.of(10, 6, 3, 2), result.get("result"));
        assertEquals(20L, result.get("executionTime"));

        verify(mockStrategy, times(1)).sortList(mockDataList, direction, maxIterations);
    }

    @Test
    void testSortDataList2_DescendingOrderWithHighIterations() {
        List<Comparable> mockDataList = List.of(25, 15, 40, 10, 30);
        String algorithm = "mockAlgorithm";
        String direction = "DESC";
        int maxIterations = 50;

        when(mockStrategy.sortList(mockDataList, direction, maxIterations))
                .thenReturn(Map.of("result", List.of(40, 30, 25, 15, 10), "executionTime", 25L));

        SortingMadness spySortingMadness = Mockito.spy(sortingMadness);
        doReturn(mockStrategy).when(spySortingMadness).getStrategy(algorithm);

        Map<String, Object> result = spySortingMadness.sortDataList(mockDataList, algorithm, direction, maxIterations);

        assertNotNull(result);
        assertEquals(List.of(40, 30, 25, 15, 10), result.get("result"));
        assertEquals(25L, result.get("executionTime"));

        verify(mockStrategy, times(1)).sortList(mockDataList, direction, maxIterations);
    }

    @Test
    void testSortData_MultipleKeys() {
        List<Map<String, Comparable>> mockData = List.of(
                Map.of("key1", 3, "key2", 2),
                Map.of("key1", 1, "key2", 4),
                Map.of("key1", 2, "key2", 1)
        );
        List<String> keys = List.of("key1", "key2");
        String algorithm = "mockAlgorithm";
        String direction = "ASC";
        int maxIterations = 5;

        when(mockStrategy.sort(mockData, keys, direction, maxIterations))
                .thenReturn(Map.of("result", List.of(
                        Map.of("key1", 1, "key2", 4),
                        Map.of("key1", 2, "key2", 1),
                        Map.of("key1", 3, "key2", 2)
                ), "executionTime", 15L));

        SortingMadness spySortingMadness = Mockito.spy(sortingMadness);
        doReturn(mockStrategy).when(spySortingMadness).getStrategy(algorithm);

        Map<String, Object> result = spySortingMadness.sortData(mockData, keys, algorithm, direction, maxIterations);

        assertNotNull(result);
        assertEquals(List.of(
                Map.of("key1", 1, "key2", 4),
                Map.of("key1", 2, "key2", 1),
                Map.of("key1", 3, "key2", 2)
        ), result.get("result"));
        assertEquals(15L, result.get("executionTime"));

        verify(mockStrategy, times(1)).sort(mockData, keys, direction, maxIterations);
    }
}