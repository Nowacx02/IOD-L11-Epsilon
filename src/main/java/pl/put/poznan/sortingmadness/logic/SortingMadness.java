package pl.put.poznan.sortingmadness.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sortingmadness.logic.algorithms.*;

import java.util.List;
import java.util.Map;

public class SortingMadness {
    private static final Logger logger = LoggerFactory.getLogger(SortingMadness.class);

    public SortingMadness() {}

    public Map<String, Object> sortData(List<Map<String, String>> data, String key, String algorithm, String direction, int maxIterations) {
        logger.info("Starting sortData with algorithm: {}, key: {}, direction: {}, maxIterations: {}",
                algorithm, key, direction, maxIterations);
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Dataset is empty or null.");
        }
        if(maxIterations <= 0)
            maxIterations = 0;
        SortingStrategy strategy = getStrategy(algorithm);
        return strategy.sort(data, key, direction, maxIterations);
    }

    private SortingStrategy getStrategy(String algorithm) {
        switch (algorithm.toLowerCase()) {
            case "bubble":
                return new BubbleSort();
            case "insertion":
                return new InsertionSort();
            case "selection":
                return new SelectionSort();
            case "quick":
                return new QuickSort();
            case "merge":
                return new MergeSort();
            case "counting":
                return new CountingSort();
            default:
                throw new IllegalArgumentException("Unknown sorting algorithm: " + algorithm);
        }
    }
}
