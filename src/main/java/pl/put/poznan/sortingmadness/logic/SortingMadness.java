package pl.put.poznan.sortingmadness.logic;

import pl.put.poznan.sortingmadness.logic.algorithms.BubbleSort;
import pl.put.poznan.sortingmadness.logic.algorithms.InsertionSort;

import java.util.*;

public class SortingMadness {

    public SortingMadness() {}

    public Map<String, Object> sortData(List<Map<String, String>> data, String key, String algorithm, String direction, int maxIterations) {
        if ("bubble".equalsIgnoreCase(algorithm)) {
            BubbleSort.Result result = BubbleSort.sort(data, key, direction, maxIterations);
            return createResultMap(result);
        } else if ("insertion".equalsIgnoreCase(algorithm)) {
            InsertionSort.Result result = InsertionSort.sort(data, key, direction, maxIterations);
            return createResultMap(result);
        } else {
            throw new IllegalArgumentException("Unknown sorting algorithm: " + algorithm);
        }
    }

    private Map<String, Object> createResultMap(BubbleSort.Result result) {
        Map<String, Object> output = new HashMap<>();
        output.put("sortedData", result.getSortedData());
        output.put("executionTime", result.getExecutionTime());
        return output;
    }

    private Map<String, Object> createResultMap(InsertionSort.Result result) {
        Map<String, Object> output = new HashMap<>();
        output.put("sortedData", result.getSortedData());
        output.put("executionTime", result.getExecutionTime());
        return output;
    }
}
