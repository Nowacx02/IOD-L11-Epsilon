package pl.put.poznan.sortingmadness.logic.algorithms;

import java.util.List;
import java.util.Map;

public class InsertionSort {

    public static List<Map<String, String>> sort(List<Map<String, String>> data, String key, String direction, int maxIterations) {
        int n = data.size();
        int iterations = 0;

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

        return data;
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
