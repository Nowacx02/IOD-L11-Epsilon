package pl.put.poznan.sortingmadness.logic;

import pl.put.poznan.sortingmadness.logic.algorithms.InsertionSort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SortingMadness {

    public SortingMadness() {}

    public List<Map<String, String>> sortData(List<Map<String, String>> data, String key, String algorithm, String direction, int maxIterations) {
        // Wyodrębnij dane do posortowania
        List<Map<String, String>> sortedData = new ArrayList<>(data);

        switch (algorithm.toLowerCase()) {
            case "insertion":
                sortedData = InsertionSort.sort(sortedData, key, direction, maxIterations);
                break;
            default:
                throw new IllegalArgumentException("Unknown sorting algorithm: " + algorithm);
        }

        return sortedData;
    }
}
