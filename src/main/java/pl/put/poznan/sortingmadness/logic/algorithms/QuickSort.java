package pl.put.poznan.sortingmadness.logic.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sortingmadness.logic.SortingStrategy;

import java.util.List;
import java.util.Map;

/**
 * Klasa implementująca algorytm sortowania szybkiego (QuickSort).
 * Algorytm QuickSort polega na rekurencyjnym dzieleniu danych na mniejsze kawałki i wywoływaniu sortowania dla każdej części.
 * Następnie elementy są przydzielane do odpowiednich części w oparciu o określony pivot.
 */
public class QuickSort implements SortingStrategy {
    private static final Logger logger = LoggerFactory.getLogger(QuickSort.class);

    /**
     * Metoda sortująca dane przy użyciu algorytmu QuickSort.
     *
     * @param data           lista map zawierających dane do posortowania
     * @param key            klucz do użycia przy porównaniu wartości w mapach
     * @param direction      kierunek sortowania, "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations  maksymalna liczba iteracji sortowania do wykonania
     * @return mapa zawierająca wyniki sortowania z danymi posortowanymi oraz czasem wykonania
     */
    @Override
    public Map<String, Object> sort(List<Map<String, String>> data, String key, String direction, int maxIterations) {
        logger.info("Starting QuickSort with key: {}, direction: {}, maxIterations: {}", key, direction, maxIterations);

        long startTime = System.nanoTime();
        int iterations = sortRecursive(data, 0, data.size() - 1, key, direction, maxIterations, new int[]{0});
        long duration = System.nanoTime() - startTime;

        logger.info("QuickSort completed in {} ms.", duration / 1_000_000.0);

        // Return result as a Map
        return Map.of(
                "sortedData", data,
                "executionTime", duration / 1_000_000.0
        );
    }

    /**
     * Rekurencyjna metoda sortowania przez szybki sort (QuickSort).
     *
     * @param data           lista map zawierających dane do posortowania
     * @param low            indeks początku segmentu do posortowania
     * @param high           indeks końca segmentu do posortowania
     * @param key            klucz do użycia przy porównaniu wartości w mapach
     * @param direction      kierunek sortowania, "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations  maksymalna liczba iteracji sortowania do wykonania
     * @param iterations     wskaźnik liczby iteracji
     * @return liczba wykonanych iteracji
     */
    private int sortRecursive(List<Map<String, String>> data, int low, int high, String key, String direction, int maxIterations, int[] iterations) {
        if (low < high && (iterations[0] < maxIterations || maxIterations == 0)) {
            int pivotIndex = partition(data, low, high, key, direction, maxIterations, iterations);

            sortRecursive(data, low, pivotIndex - 1, key, direction, maxIterations, iterations);
            sortRecursive(data, pivotIndex + 1, high, key, direction, maxIterations, iterations);
        }
        return iterations[0];
    }

    /**
     * Metoda dzieląca dane na podtablice według wyboru pivotu.
     *
     * @param data           lista map zawierających dane do posortowania
     * @param low            indeks początku segmentu do podziału
     * @param high           indeks końca segmentu do podziału
     * @param key            klucz do użycia przy porównaniu wartości w mapach
     * @param direction      kierunek sortowania, "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations  maksymalna liczba iteracji sortowania do wykonania
     * @param iterations     wskaźnik liczby iteracji
     * @return indeks pivotu
     */
    private int partition(List<Map<String, String>> data, int low, int high, String key, String direction, int maxIterations, int[] iterations) {
        String pivotValue = data.get(high).get(key);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (iterations[0] == maxIterations && maxIterations > 0) break;

            int comparison = compareValues(data.get(j).get(key), pivotValue);
            if ("desc".equalsIgnoreCase(direction)) {
                comparison = -comparison;
            }

            if (comparison <= 0) {
                i++;
                Map<String, String> temp = data.get(i);
                data.set(i, data.get(j));
                data.set(j, temp);
            }
            iterations[0]++;
        }

        // Swap the pivot into its correct place
        Map<String, String> temp = data.get(i + 1);
        data.set(i + 1, data.get(high));
        data.set(high, temp);

        return i + 1;
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
