package pl.put.poznan.sortingmadness.logic.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sortingmadness.logic.SortingStrategy;

import java.util.List;
import java.util.Map;

public class SelectionSort implements SortingStrategy {
    private static final Logger logger = LoggerFactory.getLogger(SelectionSort.class);

    @Override
    public Map<String, Object> sort(List<Map<String, String>> data, String key, String direction, int maxIterations) {
        logger.info("Starting SelectionSort with key: {}, direction: {}, maxIterations: {}", key, direction, maxIterations);

        int n = data.size();
        int iterations = 0;
        long startTime = System.nanoTime();

        for (int i = 0; i < n - 1; i++) {
            int selectedIdx = i;

            for (int j = i + 1; j < n; j++) {
                if (iterations == maxIterations && maxIterations > 0) break;

                int comparison = compareValues(data.get(j).get(key), data.get(selectedIdx).get(key));
                if ("desc".equalsIgnoreCase(direction)) {
                    comparison = -comparison;
                }

                if (comparison < 0) {
                    selectedIdx = j;
                }
                iterations++;
            }
            if (iterations == maxIterations && maxIterations > 0) break;

            Map<String, String> temp = data.get(selectedIdx);
            data.set(selectedIdx, data.get(i));
            data.set(i, temp);
        }

        long duration = System.nanoTime() - startTime;
        logger.info("SelectionSort completed in {} ms.", duration / 1_000_000.0);

        // Return result as a Map
        return Map.of(
                "sortedData", data,
                "executionTime", duration / 1_000_000.0
        );
    }

    private int compareValues(String value1, String value2) {
        try {
            int int1 = Integer.parseInt(value1);
            int int2 = Integer.parseInt(value2);
            return Integer.compare(int1, int2);
        } catch (NumberFormatException e) {
            return value1.compareTo(value2);
        }
    }
}
