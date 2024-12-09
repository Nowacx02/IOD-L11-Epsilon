package pl.put.poznan.sortingmadness.logic.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sortingmadness.logic.SortingStrategy;

import java.util.List;
import java.util.Map;

public class InsertionSort implements SortingStrategy {
    private static final Logger logger = LoggerFactory.getLogger(InsertionSort.class);

    @Override
    public Map<String, Object> sort(List<Map<String, String>> data, String key, String direction, int maxIterations) {
        logger.info("Starting InsertionSort with key: {}, direction: {}, maxIterations: {}", key, direction, maxIterations);

        int n = data.size();
        int iterations = 0;
        long startTime = System.nanoTime(); // Start measuring time

        // Insertion Sort Algorithm
        for (int i = 1; i < n; i++) {
            if (iterations == maxIterations && maxIterations > 0) break;

            Map<String, String> current = data.get(i);
            int j = i - 1;

            while (j >= 0 && compareValues(data.get(j).get(key), current.get(key)) > 0) {
                if ("desc".equalsIgnoreCase(direction)) {
                    if (compareValues(data.get(j).get(key), current.get(key)) < 0) break;
                }
                data.set(j + 1, data.get(j));
                j--;
                iterations++;
            }
            data.set(j + 1, current);
        }

        long duration = System.nanoTime() - startTime; // End measuring time
        logger.info("InsertionSort completed in {} ms.", duration / 1_000_000.0);

        // Return result as a Map
        return Map.of(
                "sortedData", data,
                "executionTime", duration / 1_000_000.0
        );
    }

    // Helper method for comparing values, handles numeric and string comparisons
    private static int compareValues(String value1, String value2) {
        try {
            int int1 = Integer.parseInt(value1);
            int int2 = Integer.parseInt(value2);
            return Integer.compare(int1, int2);
        } catch (NumberFormatException e) {
            return value1.compareTo(value2);
        }
    }
}
