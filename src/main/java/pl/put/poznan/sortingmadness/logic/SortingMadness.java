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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SortingMadness {
    private static final Logger logger = LoggerFactory.getLogger(SortingMadness.class);

    public SortingMadness() {}

    public Map<String, Object> sortData(List<Map<String, String>> data, String key, String algorithm, String direction, int maxIterations) {
        logger.info("Starting sortData with algorithm: {}, key: {}, direction: {}, maxIterations: {}",
                algorithm, key, direction, maxIterations);

        logger.debug("Dataset received: {}", data);

        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Dataset is empty or null.");
        }

        switch (algorithm.toLowerCase()) {
            case "bubble":
                logger.debug("BubbleSort input: {}", data);
                BubbleSort.Result bubbleResult = BubbleSort.sort(data, key, direction, maxIterations);
                logger.debug("BubbleSort output: {}", bubbleResult.getSortedData());
                return createResultMap(bubbleResult);
            case "insertion":
                logger.debug("InsertionSort input: {}", data);
                InsertionSort.Result insertionResult = InsertionSort.sort(data, key, direction, maxIterations);
                logger.debug("InsertionSort output: {}", insertionResult.getSortedData());
                return createResultMap(insertionResult);
            case "selection":
                logger.debug("SelectionSort input: {}", data);
                SelectionSort.Result selectionResult = SelectionSort.sort(data, key, direction, maxIterations);
                logger.debug("SelectionSort output: {}", selectionResult.getSortedData());
                return createResultMap(selectionResult);
            case "quick":
                logger.debug("QuickSort input: {}", data);
                QuickSort.Result quickResult = QuickSort.sort(data, key, direction, maxIterations);
                logger.debug("QuickSort output: {}", quickResult.getSortedData());
                return createResultMap(quickResult);
            case "merge":
                logger.debug("MergeSort input: {}", data);
                MergeSort.Result mergeResult = MergeSort.sort(data, key, direction, maxIterations);
                logger.debug("MergeSort output: {}", mergeResult.getSortedData());
                return createResultMap(mergeResult);
            case "counting":
                logger.debug("CountingSort input: {}", data);
                CountingSort.Result countingResult = CountingSort.sort(data, key, direction, maxIterations);
                logger.debug("CountingSort output: {}", countingResult.getSortedData());
                return createResultMap(countingResult);
            default:
                throw new IllegalArgumentException("Unknown sorting algorithm: " + algorithm);
        }
    }

    private Map<String, Object> createResultMap(BubbleSort.Result result) {
        logger.info("BubbleSort completed in {} ms. Sorted data: {}", result.getExecutionTime(), result.getSortedData());
        Map<String, Object> output = new HashMap<>();
        output.put("sortedData", result.getSortedData());
        output.put("executionTime", result.getExecutionTime());
        logger.debug("Result map for BubbleSort: {}", output);
        return output;
    }

    private Map<String, Object> createResultMap(InsertionSort.Result result) {
        logger.info("InsertionSort completed in {} ms. Sorted data: {}", result.getExecutionTime(), result.getSortedData());
        Map<String, Object> output = new HashMap<>();
        output.put("sortedData", result.getSortedData());
        output.put("executionTime", result.getExecutionTime());
        logger.debug("Result map for InsertionSort: {}", output);
        return output;
    }

    private Map<String, Object> createResultMap(SelectionSort.Result result) {
        logger.info("SelectionSort completed in {} ms. Sorted data: {}", result.getExecutionTime(), result.getSortedData());
        Map<String, Object> output = new HashMap<>();
        output.put("sortedData", result.getSortedData());
        output.put("executionTime", result.getExecutionTime());
        logger.debug("Result map for SelectionSort: {}", output);
        return output;
    }

    private Map<String, Object> createResultMap(QuickSort.Result result) {
        logger.info("QuickSort completed in {} ms. Sorted data: {}", result.getExecutionTime(), result.getSortedData());
        Map<String, Object> output = new HashMap<>();
        output.put("sortedData", result.getSortedData());
        output.put("executionTime", result.getExecutionTime());
        logger.debug("Result map for QuickSort: {}", output);
        return output;
    }

    private Map<String, Object> createResultMap(MergeSort.Result result) {
        logger.info("MergeSort completed in {} ms. Sorted data: {}", result.getExecutionTime(), result.getSortedData());
        Map<String, Object> output = new HashMap<>();
        output.put("sortedData", result.getSortedData());
        output.put("executionTime", result.getExecutionTime());
        logger.debug("Result map for MergeSort: {}", output);
        return output;
    }

    private Map<String, Object> createResultMap(CountingSort.Result result) {
        logger.info("CountingSort completed in {} ms. Sorted data: {}", result.getExecutionTime(), result.getSortedData());
        Map<String, Object> output = new HashMap<>();
        output.put("sortedData", result.getSortedData());
        output.put("executionTime", result.getExecutionTime());
        output.put("executionTime", result.getExecutionTime());
        logger.debug("Result map for CountingSort: {}", output);
        return output;
    }
}
