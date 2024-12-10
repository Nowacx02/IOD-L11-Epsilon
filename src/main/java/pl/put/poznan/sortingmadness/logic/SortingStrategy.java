package pl.put.poznan.sortingmadness.logic;

import java.util.List;
import java.util.Map;

public interface SortingStrategy {
    <E extends Comparable<E>> Map<String, Object> sort(List<Map<String, E>> data, String key, String direction, int maxIterations);
    <E extends Comparable<E>> Map<String, Object> sortList(List<E> data, String direction, int maxIterations);
}
