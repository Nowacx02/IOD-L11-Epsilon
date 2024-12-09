package pl.put.poznan.sortingmadness.logic.algorithms;

import java.util.List;
import java.util.Map;

public class InsertionSort {

    public static Result sort(List<Map<String, String>> data, String key, String direction, int maxIterations) {
        int n = data.size();
        int iterations = 0;
        long startTime = System.nanoTime(); // Start pomiaru czasu

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
