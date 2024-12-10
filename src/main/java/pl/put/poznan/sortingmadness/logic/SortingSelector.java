package pl.put.poznan.sortingmadness.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sortingmadness.logic.algorithms.*;

import java.util.*;

public class SortingSelector {

    private static final Logger logger = LoggerFactory.getLogger(SortingSelector.class);
    private static final Map<String, SortingStrategy> algorithmMap = initializeAlgorithmMap();

    private static Map<String, SortingStrategy> initializeAlgorithmMap() {
        Map<String, SortingStrategy> map = new HashMap<>();
        map.put("bubble", new BubbleSort());
        map.put("selection", new SelectionSort());
        map.put("insertion", new InsertionSort());
        map.put("merge", new MergeSort());
        map.put("counting", new CountingSort());
        map.put("quick", new QuickSort());
        return map;
    }

    public static String selectAlgorithmList(List<?> data, boolean descendingOrder) {
        if (!data.isEmpty() && data.get(0) instanceof Comparable) {
            return selectAlgorithmForComparableList((List<Comparable>) data, descendingOrder);
        } else {
            throw new IllegalArgumentException("Unsupported data type");
        }
    }

    public static String selectAlgorithmMap(List<Map<String, Comparable>> data, List<String> keysToSort, boolean descendingOrder) {
        if (!data.isEmpty() && data.get(0) instanceof Map) {
            return selectAlgorithmForMapList((List<Map<String, Comparable>>) data, keysToSort, descendingOrder);
        } else {
            throw new IllegalArgumentException("Unsupported data type");
        }
    }

    private static String selectAlgorithmForComparableList(List<Comparable> data, boolean descendingOrder) {
        System.out.println("wybieram algorytm");
        System.out.println(isSorted(data, descendingOrder));
        if (isSorted(data, descendingOrder)) {
            return "insertion";
        }
        if (hasManyDuplicates(data)) {
            return "merge";
        }
        if (numericData(data)) {
            return "counting";
        }
        if (data.size() <= 10) {
            return "bubble";
        }
        if (data.size() <= 100) {
            return "insertion";
        }
        if (data.size() <= 1000) {
            return "merge";
        }
        return "quick";
    }

    private static String selectAlgorithmForMapList(List<Map<String, Comparable>> data, List<String> keysToSort, boolean descendingOrder) {
        if (isSortedMap(data, keysToSort, descendingOrder)) {
            return "insertion";
        }
        if (hasManyDuplicatesMap(data)) {
            return "merge";
        }
        if (numericDataMap(data, keysToSort)) {
            return "counting";
        }
        if (data.size() <= 10) {
            return "bubble";
        }
        if (data.size() <= 100) {
            return "insertion";
        }
        if (data.size() <= 1000) {
            return "merge";
        }
        return "quick";
    }

    private static boolean isSorted(List<Comparable> data, boolean descendingOrder) {
        for (int i = 1; i < data.size(); i++) {
            if (descendingOrder ? data.get(i).compareTo(data.get(i - 1)) > 0 : data.get(i).compareTo(data.get(i - 1)) < 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSortedMap(List<Map<String, Comparable>> data, List<String> keysToSort, boolean descendingOrder) {
        for (int i = 1; i < data.size(); i++) {
            Map<String, Comparable> current = data.get(i);
            Map<String, Comparable> previous = data.get(i - 1);
            for (String key : keysToSort) {
                int comparison = current.get(key).compareTo(previous.get(key));
                if ((descendingOrder && comparison > 0) || (!descendingOrder && comparison < 0)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean hasManyDuplicates(List<Comparable> data) {
        Set<Comparable> set = new HashSet<>(data);
        return set.size() < data.size() / 2;
    }

    private static boolean hasManyDuplicatesMap(List<Map<String, Comparable>> data) {
        Set<Map<String, Comparable>> set = new HashSet<>(data);
        return set.size() < data.size() / 2;
    }

    private static boolean numericData(List<Comparable> data) {
        return data.stream().allMatch(e -> e instanceof Integer);
    }

    private static boolean numericDataMap(List<Map<String, Comparable>> data, List<String> keysToSort) {
        for (Map<String, Comparable> map : data) {
            for (String key : keysToSort) {
                if (!(map.get(key) instanceof Integer)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static SortingStrategy getSortingStrategy(String algorithm) {
        SortingStrategy strategy = algorithmMap.get(algorithm);
        if (strategy == null) {
            throw new IllegalArgumentException("Unknown sorting algorithm: " + algorithm);
        }
        return strategy;
    }
}
