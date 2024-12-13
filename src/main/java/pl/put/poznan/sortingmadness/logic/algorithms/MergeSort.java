package pl.put.poznan.sortingmadness.logic.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sortingmadness.logic.SortingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Klasa implementująca algorytm sortowania przez scalanie (MergeSort).
 * Algorytm dzieli dane na mniejsze podzbiory, sortuje je rekurencyjnie,
 * a następnie scala w posortowaną całość.
 */
public class MergeSort implements SortingStrategy {
    private static final Logger logger = LoggerFactory.getLogger(MergeSort.class);

    /**
     * Sortuje listę map według podanego klucza i kierunku za pomocą algorytmu sortowania przez scalanie.
     *
     * @param data          lista map zawierających dane do posortowania
     * @param keys           klucz używany do porównywania wartości w mapach
     * @param direction     kierunek sortowania: "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations maksymalna liczba iteracji do wykonania; 0 oznacza brak limitu
     * @param <E>           typ wartości w mapach, który musi być porównywalny
     * @return mapa z wynikami, zawierająca posortowaną listę danych oraz czas wykonania w milisekundach
     */
    @Override
    public <E extends Comparable<E>> Map<String, Object> sort(List<Map<String, E>> data, List<String> keys, String direction, int maxIterations) {
        logger.info("Starting MergeSort with keys: {}, direction: {}, maxIterations: {}", keys, direction, maxIterations);

        long startTime = System.nanoTime(); // Start time measurement

        // Rekurencyjne sortowanie listy za pomocą MergeSort
        List<Map<String, E>> sortedData = mergeSort(data, keys, direction, maxIterations);

        long duration = System.nanoTime() - startTime; // End time measurement
        logger.info("MergeSort completed in {} ms.", duration / 1_000_000.0);

        // Return result as a Map
        return Map.of(
                "sortedData", sortedData,
                "executionTime", duration / 1_000_000.0
        );
    }

    /**
     * Rekurencyjna metoda MergeSort.
     *
     * @param data Lista map do posortowania
     * @param keys Klucze określające priorytet sortowania
     * @param direction Kierunek sortowania (asc lub desc)
     * @param maxIterations Maksymalna liczba iteracji (0 oznacza brak limitu)
     * @return Posortowana lista map
     */
    private <E extends Comparable<E>> List<Map<String, E>> mergeSort(List<Map<String, E>> data, List<String> keys, String direction, int maxIterations) {
        if (data.size() <= 1) {
            return data;
        }

        int mid = data.size() / 2;
        List<Map<String, E>> left = mergeSort(new ArrayList<>(data.subList(0, mid)), keys, direction, maxIterations);
        List<Map<String, E>> right = mergeSort(new ArrayList<>(data.subList(mid, data.size())), keys, direction, maxIterations);

        return merge(left, right, keys, direction);
    }

    /**
     * Łączy dwie posortowane listy w jedną posortowaną listę.
     *
     * @param left Lewa posortowana lista
     * @param right Prawa posortowana lista
     * @param keys Klucze określające priorytet sortowania
     * @param direction Kierunek sortowania (asc lub desc)
     * @return Posortowana lista
     */
    private <E extends Comparable<E>> List<Map<String, E>> merge(List<Map<String, E>> left, List<Map<String, E>> right, List<String> keys, String direction) {
        List<Map<String, E>> merged = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            int comparison = compareByKeys(left.get(i), right.get(j), keys);
            if ("desc".equalsIgnoreCase(direction)) {
                comparison = -comparison;
            }

            if (comparison <= 0) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }

        while (i < left.size()) {
            merged.add(left.get(i++));
        }

        while (j < right.size()) {
            merged.add(right.get(j++));
        }

        return merged;
    }

    /**
     * Compares two maps based on a list of keys with a priority order.
     *
     * @param map1 the first map to compare
     * @param map2 the second map to compare
     * @param keys the list of keys defining the priority order for comparison
     * @return the comparison result: -1, 0, or 1
     */
    private <E extends Comparable<E>> int compareByKeys(Map<String, E> map1, Map<String, E> map2, List<String> keys) {
        for (String key : keys) {
            E value1 = map1.get(key);
            E value2 = map2.get(key);

            if (value1 == null || value2 == null) {
                throw new IllegalArgumentException("Key not found in one of the maps: " + key);
            }

            int comparison = compareValues(value1, value2);
            if (comparison != 0) {
                return comparison;
            }
        }
        return 0; // All keys are equal
    }

    /**
     * Pomocnicza metoda do porównywania wartości, obsługuje porównania liczbowe i tekstowe.
     *
     * @param value1 pierwsza wartość do porównania
     * @param value2 druga wartość do porównania
     * @return wynik porównania: -1 jeśli value1 < value2, 0 jeśli równy, 1 jeśli value1 > value2
     */
    private static <E extends Comparable<E>> int compareValues(E value1, E value2) {
        return value1.compareTo(value2);
    }


    /**
     * Sortuje listę elementów za pomocą algorytmu sortowania przez scalanie.
     *
     * @param data          lista danych do posortowania
     * @param direction     kierunek sortowania: "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations maksymalna liczba iteracji do wykonania; 0 oznacza brak limitu
     * @param <E>           typ elementów na liście, który musi być porównywalny
     * @return mapa z wynikami, zawierająca posortowaną listę danych oraz czas wykonania w milisekundach
     */
    @Override
    public <E extends Comparable<E>> Map<String, Object> sortList(List<E> data, String direction, int maxIterations) {
        logger.info("Starting MergeSort with direction: {}, maxIterations: {}", direction, maxIterations);

        long startTime = System.nanoTime();
        List<Comparable> sortedData = mergeSort(new ArrayList<>(data), direction, maxIterations, new int[]{0});
        long duration = System.nanoTime() - startTime;

        logger.info("MergeSort completed in {} ms.", duration / 1_000_000.0);

        // Return result as a Map
        return Map.of(
                "sortedData", sortedData,
                "executionTime", duration / 1_000_000.0
        );
    }

    /**
     * Rekurencyjna metoda implementująca algorytm sortowania przez scalanie.
     *
     * @param data          lista danych do posortowania
     * @param direction     kierunek sortowania: "asc" lub "desc"
     * @param maxIterations maksymalna liczba iteracji; 0 oznacza brak limitu
     * @param iterations    licznik wykonanych iteracji (przekazywany przez referencję)
     * @return posortowana lista danych
     */
    private <E extends Comparable<E>> List<Comparable> mergeSort(List<Comparable> data, String direction, int maxIterations, int[] iterations) {
        if (data.size() <= 1) return data;

        int mid = data.size() / 2;
        List<Comparable> left = mergeSort(data.subList(0, mid), direction, maxIterations, iterations);
        List<Comparable> right = mergeSort(data.subList(mid, data.size()), direction, maxIterations, iterations);

        return merge(left, right, direction, maxIterations, iterations);
    }

    /**
     * Scala dwie posortowane listy w jedną posortowaną listę.
     *
     * @param left          pierwsza posortowana lista
     * @param right         druga posortowana lista
     * @param direction     kierunek sortowania: "asc" lub "desc"
     * @param maxIterations maksymalna liczba iteracji; 0 oznacza brak limitu
     * @param iterations    licznik wykonanych iteracji (przekazywany przez referencję)
     * @return scalona i posortowana lista
     */
    private List<Comparable> merge(List<Comparable> left, List<Comparable> right, String direction, int maxIterations, int[] iterations) {
        List<Comparable> merged = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size() && (iterations[0] < maxIterations || maxIterations == 0)) {
            int comparison = left.get(i).compareTo(right.get(j));
            if ("desc".equalsIgnoreCase(direction)) {
                comparison = -comparison;
            }

            if (comparison <= 0) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
            iterations[0]++;
        }

        // Append any remaining elements
        while (i < left.size()) merged.add(left.get(i++));
        while (j < right.size()) merged.add(right.get(j++));

        return merged;
    }
}
