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
     * @param key           klucz używany do porównywania wartości w mapach
     * @param direction     kierunek sortowania: "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations maksymalna liczba iteracji do wykonania; 0 oznacza brak limitu
     * @param <E>           typ wartości w mapach, który musi być porównywalny
     * @return mapa z wynikami, zawierająca posortowaną listę danych oraz czas wykonania w milisekundach
     */
    @Override
    public <E extends Comparable<E>> Map<String, Object> sort(List<Map<String, E>> data, String key, String direction, int maxIterations) {
        logger.info("Starting MergeSort with key: {}, direction: {}, maxIterations: {}", key, direction, maxIterations);

        long startTime = System.nanoTime(); // Start time measurement

        // Step 1: Count occurrences of each value
        Map<E, Integer> countMap = new HashMap<>();
        for (Map<String, E> entry : data) {
            E value = entry.get(key);
            countMap.put(value, countMap.getOrDefault(value, 0) + 1);
        }

        // Step 2: Sort the keys based on the direction
        List<E> sortedKeys = new ArrayList<>(countMap.keySet());
        sortedKeys.sort((a, b) -> {
            int comparison = a.compareTo(b);
            return "desc".equalsIgnoreCase(direction) ? -comparison : comparison;
        });

        // Step 3: Reconstruct the sorted list based on the counts
        // Poprawiony fragment
        List<Map<String, E>> sortedData = new ArrayList<>();
        List<Map<String, E>> remainingEntries = new ArrayList<>(data); // Kopia oryginalnej listy

        for (E sortedKey : sortedKeys) {
            int count = countMap.get(sortedKey);
            for (int i = 0; i < remainingEntries.size() && count > 0; i++) {
                Map<String, E> entry = remainingEntries.get(i);
                if (entry.get(key).equals(sortedKey)) {
                    sortedData.add(entry);
                    remainingEntries.remove(i--); // Usuwamy element, który został już dodany
                    count--;
                }
            }
        }


        long duration = System.nanoTime() - startTime; // End time measurement
        logger.info("MergeSort completed in {} ms.", duration / 1_000_000.0);

        // Return result as a Map
        return Map.of(
                "sortedData", sortedData,
                "executionTime", duration / 1_000_000.0
        );
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
