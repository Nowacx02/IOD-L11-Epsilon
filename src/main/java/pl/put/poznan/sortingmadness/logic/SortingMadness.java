package pl.put.poznan.sortingmadness.logic;

import pl.put.poznan.sortingmadness.logic.algorithms.BubbleSort;
import pl.put.poznan.sortingmadness.logic.algorithms.InsertionSort;
import pl.put.poznan.sortingmadness.logic.algorithms.SelectionSort;
import pl.put.poznan.sortingmadness.logic.algorithms.QuickSort;
import pl.put.poznan.sortingmadness.logic.algorithms.MergeSort;
import pl.put.poznan.sortingmadness.logic.algorithms.CountingSort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortingMadness {

    public SortingMadness() {}

    public Map<String, Object> sortData(List<Map<String, String>> data, String key, String algorithm, String direction, int maxIterations) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Dataset is empty or null.");
        }

        if(maxIterations <= 0)
            maxIterations = 0;

        switch (algorithm.toLowerCase()) {
            case "bubble":
                BubbleSort.Result bubbleResult = BubbleSort.sort(data, key, direction, maxIterations);
                return createResultMap(bubbleResult);
            case "insertion":
                InsertionSort.Result insertionResult = InsertionSort.sort(data, key, direction, maxIterations);
                return createResultMap(insertionResult);
            case "selection":
                SelectionSort.Result selectionResult = SelectionSort.sort(data, key, direction, maxIterations);
                return createResultMap(selectionResult);
            case "quick":
                QuickSort.Result quickResult = QuickSort.sort(data, key, direction, maxIterations);
                return createResultMap(quickResult);
            case "merge":
                MergeSort.Result mergeResult = MergeSort.sort(data, key, direction, maxIterations);
                return createResultMap(mergeResult);
            case "counting":
                CountingSort.Result countingResult = CountingSort.sort(data, key, direction, maxIterations);
                return createResultMap(countingResult);
            default:
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

    private Map<String, Object> createResultMap(SelectionSort.Result result) {
        Map<String, Object> output = new HashMap<>();
        output.put("sortedData", result.getSortedData());
        output.put("executionTime", result.getExecutionTime());
        return output;
    }

    private Map<String, Object> createResultMap(QuickSort.Result result) {
        Map<String, Object> output = new HashMap<>();
        output.put("sortedData", result.getSortedData());
        output.put("executionTime", result.getExecutionTime());
        return output;
    }

    private Map<String, Object> createResultMap(MergeSort.Result result) {
        Map<String, Object> output = new HashMap<>();
        output.put("sortedData", result.getSortedData());
        output.put("executionTime", result.getExecutionTime());
        return output;
    }

    private Map<String, Object> createResultMap(CountingSort.Result result) {
        Map<String, Object> output = new HashMap<>();
        output.put("sortedData", result.getSortedData());
        output.put("executionTime", result.getExecutionTime());
        return output;
    }
}
