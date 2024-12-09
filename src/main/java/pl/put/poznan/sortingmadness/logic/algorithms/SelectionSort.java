package pl.put.poznan.sortingmadness.logic.algorithms;

import java.util.List;
import java.util.Map;

public class SelectionSort {

    public static Result sort(List<Map<String, String>> data, String key, String direction, int maxIterations) {
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
        return new Result(data, duration / 1_000_000.0);
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
