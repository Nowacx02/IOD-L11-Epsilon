package pl.put.poznan.sortingmadness.logic.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountingSort {

    public static Result sort(List<Map<String, String>> data, String key, String direction, int maxIterations) {
        long startTime = System.nanoTime();

        List<Map<String, String>> sortedData = new ArrayList<>();
        Map<String, Integer> countMap = new HashMap<>();

        for (Map<String, String> entry : data) {
            String value = entry.get(key);
            countMap.put(value, countMap.getOrDefault(value, 0) + 1);
        }

        List<String> sortedKeys = new ArrayList<>(countMap.keySet());
        sortedKeys.sort((a, b) -> {
            int comparison = a.compareTo(b);
            return "desc".equalsIgnoreCase(direction) ? -comparison : comparison;
        });

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

        long duration = System.nanoTime() - startTime;
        return new Result(sortedData, duration / 1_000_000.0);
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
