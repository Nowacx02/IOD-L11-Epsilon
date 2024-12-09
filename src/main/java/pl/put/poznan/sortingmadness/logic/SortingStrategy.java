package pl.put.poznan.sortingmadness.logic;

import java.util.List;
import java.util.Map;

public interface SortingStrategy {
    Map<String, Object> sort(List<Map<String, String>> data, String key, String direction, int maxIterations);
}
