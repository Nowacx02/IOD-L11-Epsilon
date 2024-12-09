package pl.put.poznan.sortingmadness.logic.algorithms;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BubbleSort {

    public static Result sort(List<Map<String, String>> data, String key, String direction, int maxIterations) {
        int n = data.size();
        boolean swapped;
        int iterations = 0;
        long startTime = System.nanoTime(); // Start pomiaru czasu

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

        long duration = System.nanoTime() - startTime; // Koniec pomiaru czasu
        return new Result(data, duration / 1_000_000.0); // Zwracamy wynik wraz z czasem
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

    public static class Result {
        private final List<Map<String, String>> sortedData;
        private final double executionTime;

        public Result(List<Map<String, String>> sortedData, double executionTime) {
            this.sortedData = sortedData;
            this.executionTime = executionTime;
        }

        public List<Map<String, String>> getSortedData() {
            return sortedData;
        }

        public double getExecutionTime() {
            return executionTime;
        }
    }
}
