package pl.put.poznan.sortingmadness.logic.algorithms;

import java.util.List;
import java.util.Map;

public class QuickSort {

    public static Result sort(List<Map<String, String>> data, String key, String direction, int maxIterations) {
        long startTime = System.nanoTime();
        int iterations = sortRecursive(data, 0, data.size() - 1, key, direction, maxIterations, new int[]{0});
        long duration = System.nanoTime() - startTime;

        return new Result(data, duration / 1_000_000.0);
    }

    private static int sortRecursive(List<Map<String, String>> data, int low, int high, String key, String direction, int maxIterations, int[] iterations) {
        if (low < high && (iterations[0] < maxIterations || maxIterations == 0)) {
            int pivotIndex = partition(data, low, high, key, direction, maxIterations, iterations);

            sortRecursive(data, low, pivotIndex - 1, key, direction, maxIterations, iterations);
            sortRecursive(data, pivotIndex + 1, high, key, direction, maxIterations, iterations);
        }
        return iterations[0];
    }

    private static int partition(List<Map<String, String>> data, int low, int high, String key, String direction, int maxIterations, int[] iterations) {
        String pivotValue = data.get(high).get(key);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (iterations[0] == maxIterations && maxIterations > 0) break;

            int comparison = compareValues(data.get(j).get(key), pivotValue);
            if ("desc".equalsIgnoreCase(direction)) {
                comparison = -comparison;
            }

            if (comparison <= 0) {
                i++;
                Map<String, String> temp = data.get(i);
                data.set(i, data.get(j));
                data.set(j, temp);
            }
            iterations[0]++;
        }

        Map<String, String> temp = data.get(i + 1);
        data.set(i + 1, data.get(high));
        data.set(high, temp);

        return i + 1;
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
