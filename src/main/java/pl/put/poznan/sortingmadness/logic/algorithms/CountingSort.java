package pl.put.poznan.sortingmadness.logic.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sortingmadness.logic.SortingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Klasa implementująca algorytm sortowania przez liczenie.
 * Algorytm polega na zliczaniu wystąpień wartości, a następnie przywraca uporządkowaną listę danych.
 */
public class CountingSort implements SortingStrategy {
    private static final Logger logger = LoggerFactory.getLogger(CountingSort.class);

    /**
     * Metoda sortująca dane przy użyciu algorytmu sortowania przez liczenie.
     *
     * @param data           lista map zawierających dane do posortowania
     * @param key            klucz do użycia przy porównaniu wartości w mapach
     * @param direction      kierunek sortowania, "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations  maksymalna liczba iteracji sortowania do wykonania
     * @return mapa zawierająca wyniki sortowania z danymi posortowanymi oraz czasem wykonania
     */
    @Override
    public Map<String, Object> sort(List<Map<String, String>> data, String key, String direction, int maxIterations) {
        logger.info("Starting CountingSort with key: {}, direction: {}, maxIterations: {}", key, direction, maxIterations);

        long startTime = System.nanoTime(); // Start time measurement

        List<Map<String, String>> sortedData = new ArrayList<>();
        Map<String, Integer> countMap = new HashMap<>();

        // Step 1: Count occurrences of each value
        for (Map<String, String> entry : data) {
            String value = entry.get(key);
            countMap.put(value, countMap.getOrDefault(value, 0) + 1);
        }

        // Step 2: Sort the keys based on the direction
        List<String> sortedKeys = new ArrayList<>(countMap.keySet());
        sortedKeys.sort((a, b) -> {
            int comparison = a.compareTo(b);
            return "desc".equalsIgnoreCase(direction) ? -comparison : comparison;
        });

        // Step 3: Reconstruct the sorted list based on the counts
        for (String sortedKey : sortedKeys) {
            int count = countMap.get(sortedKey);
            while (count > 0) {
                for (Map<String, String> entry : data) {
                    if (entry.get(key).equals(sortedKey)) {
                        sortedData.add(entry);
                        break;
                    }
                }
                count--;
            }
        }

        long duration = System.nanoTime() - startTime; // End time measurement
        logger.info("CountingSort completed in {} ms.", duration / 1_000_000.0);

        // Return result as a Map
        return Map.of(
                "sortedData", sortedData,
                "executionTime", duration / 1_000_000.0
        );
    }
}
