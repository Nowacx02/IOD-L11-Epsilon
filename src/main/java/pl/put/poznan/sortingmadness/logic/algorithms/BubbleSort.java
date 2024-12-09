package pl.put.poznan.sortingmadness.logic.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sortingmadness.logic.SortingStrategy;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BubbleSort implements SortingStrategy {
    private static final Logger logger = LoggerFactory.getLogger(BubbleSort.class);

    @Override
    public Map<String, Object> sort(List<Map<String, String>> data, String key, String direction, int maxIterations) {
        logger.info("Starting BubbleSort with key: {}, direction: {}, maxIterations: {}", key, direction, maxIterations);

        int n = data.size();
        boolean swapped;
        int iterations = 0;
        long startTime = System.nanoTime(); // Start time measurement

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (iterations == maxIterations && maxIterations > 0) break;

                int comparison = compareValues(data.get(j).get(key), data.get(j + 1).get(key));
                if ("desc".equalsIgnoreCase(direction)) {
                    comparison = -comparison;
                }

                if (comparison > 0) {
                    Collections.swap(data, j, j + 1);
                    swapped = true;
                }
                iterations++;
            }
            if (!swapped) break;
        }

        long duration = System.nanoTime() - startTime; // End time measurement
        logger.info("BubbleSort completed in {} ms.", duration / 1_000_000.0);

        return Map.of(
                "sortedData", data,
                "executionTime", duration / 1_000_000.0
        );
    }

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
