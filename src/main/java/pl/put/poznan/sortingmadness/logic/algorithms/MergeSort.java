package pl.put.poznan.sortingmadness.logic.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sortingmadness.logic.SortingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Klasa implementująca algorytm sortowania przez scalanie (MergeSort).
 * Algorytm polega na rekurencyjnym dzieleniu danych na mniejsze kawałki i scalaniu ich w odpowiedniej kolejności.
 */
public class MergeSort implements SortingStrategy {
    private static final Logger logger = LoggerFactory.getLogger(MergeSort.class);

    /**
     * Metoda sortująca dane przy użyciu algorytmu sortowania przez scalanie.
     *
     * @param data           lista map zawierających dane do posortowania
     * @param key            klucz do użycia przy porównaniu wartości w mapach
     * @param direction      kierunek sortowania, "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations  maksymalna liczba iteracji sortowania do wykonania
     * @return mapa zawierająca wyniki sortowania z danymi posortowanymi oraz czasem wykonania
     */
    @Override
    public Map<String, Object> sort(List<Map<String, String>> data, String key, String direction, int maxIterations) {
        logger.info("Starting MergeSort with key: {}, direction: {}, maxIterations: {}", key, direction, maxIterations);

        long startTime = System.nanoTime();
        List<Map<String, String>> sortedData = mergeSort(data, key, direction, maxIterations, new int[]{0});
        long duration = System.nanoTime() - startTime;

        logger.info("MergeSort completed in {} ms.", duration / 1_000_000.0);

        // Return result as a Map
        return Map.of(
                "sortedData", sortedData,
                "executionTime", duration / 1_000_000.0
        );
    }

    /**
     * Rekurencyjna metoda sortująca przez scalanie (merge sort).
     *
     * @param data           lista map zawierających dane do posortowania
     * @param key            klucz do użycia przy porównaniu wartości w mapach
     * @param direction      kierunek sortowania, "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations  maksymalna liczba iteracji sortowania do wykonania
     * @param iterations     wskaźnik liczby iteracji
     * @return posortowana lista map
     */
    private List<Map<String, String>> mergeSort(List<Map<String, String>> data, String key, String direction, int maxIterations, int[] iterations) {
        if (data.size() <= 1) return data;

        int mid = data.size() / 2;
        List<Map<String, String>> left = mergeSort(data.subList(0, mid), key, direction, maxIterations, iterations);
        List<Map<String, String>> right = mergeSort(data.subList(mid, data.size()), key, direction, maxIterations, iterations);

        return merge(left, right, key, direction, maxIterations, iterations);
    }

    /**
     * Metoda scalająca dwie posortowane listy map na podstawie klucza.
     *
     * @param left           lewa lista map
     * @param right          prawa lista map
     * @param key            klucz do użycia przy porównaniu wartości w mapach
     * @param direction      kierunek sortowania, "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations  maksymalna liczba iteracji sortowania do wykonania
     * @param iterations     wskaźnik liczby iteracji
     * @return scalona lista map
     */
    private List<Map<String, String>> merge(List<Map<String, String>> left, List<Map<String, String>> right, String key, String direction, int maxIterations, int[] iterations) {
        List<Map<String, String>> merged = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size() && (iterations[0] < maxIterations || maxIterations == 0)) {
            int comparison = compareValues(left.get(i).get(key), right.get(j).get(key));
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

    /**
     * Pomocnicza metoda do porównywania wartości, obsługuje porównania liczbowe i tekstowe.
     *
     * @param value1 pierwsza wartość do porównania
     * @param value2 druga wartość do porównania
     * @return wynik porównania: -1 jeśli value1 < value2, 0 jeśli równy, 1 jeśli value1 > value2
     */
    private int compareValues(String value1, String value2) {
        try {
            int int1 = Integer.parseInt(value1);
            int int2 = Integer.parseInt(value2);
            return Integer.compare(int1, int2);
        } catch (NumberFormatException e) {
            return value1.compareTo(value2);
        }
    }
}
