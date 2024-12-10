package pl.put.poznan.sortingmadness.logic.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sortingmadness.logic.SortingStrategy;

import java.util.ArrayList;
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
     * @param data          lista map zawierających dane do posortowania
     * @param key           klucz do użycia przy porównaniu wartości w mapach
     * @param direction     kierunek sortowania, "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations maksymalna liczba iteracji sortowania do wykonania
     * @param <E>           typ danych implementujący interfejs Comparable
     * @return mapa zawierająca wyniki sortowania z danymi posortowanymi oraz czasem wykonania
     */
    @Override
    public <E extends Comparable<E>> Map<String, Object> sort(List<Map<String, E>> data, String key, String direction, int maxIterations) {
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
     * @param data          lista map zawierających dane do posortowania
     * @param low           indeks początku segmentu do posortowania
     * @param high          indeks końca segmentu do posortowania
     * @param key           klucz do użycia przy porównaniu wartości w mapach
     * @param direction     kierunek sortowania, "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations maksymalna liczba iteracji sortowania do wykonania
     * @param iterations    wskaźnik liczby iteracji
     * @param <E>           typ danych implementujący interfejs Comparable
     * @return liczba wykonanych iteracji
     */
    private <E extends Comparable<E>> int sortRecursive(List<Map<String, E>> data, int low, int high, String key, String direction, int maxIterations, int[] iterations) {
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
     * @param data          lista map zawierających dane do posortowania
     * @param low           indeks początku segmentu do podziału
     * @param high          indeks końca segmentu do podziału
     * @param key           klucz do użycia przy porównaniu wartości w mapach
     * @param direction     kierunek sortowania, "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations maksymalna liczba iteracji sortowania do wykonania
     * @param iterations    wskaźnik liczby iteracji
     * @param <E>           typ danych implementujący interfejs Comparable
     * @return indeks pivotu
     */
    private <E extends Comparable<E>> int partition(List<Map<String, E>> data, int low, int high, String key, String direction, int maxIterations, int[] iterations) {
        E pivotValue = data.get(high).get(key);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (iterations[0] == maxIterations && maxIterations > 0) break;

            int comparison = compareValues(data.get(j).get(key), pivotValue);
            if ("desc".equalsIgnoreCase(direction)) {
                comparison = -comparison;
            }

            if (comparison <= 0) {
                i++;
                Map<String, E> temp = data.get(i);
                data.set(i, data.get(j));
                data.set(j, temp);
            }
            iterations[0]++;
        }

        // Swap the pivot into its correct place
        Map<String, E> temp = data.get(i + 1);
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
    private static int compareValues(Comparable value1, Comparable value2) {
        if (value1 instanceof Integer && value2 instanceof Integer) {
            return Integer.compare((Integer) value1, (Integer) value2);
        } else if (value1 instanceof Double && value2 instanceof Double) {
            return Double.compare((Double) value1, (Double) value2);
        } else if (value1 instanceof String && value2 instanceof String) {
            return ((String) value1).compareTo((String) value2);
        } else {
            // Handle other cases or throw an exception if types are unsupported
            throw new IllegalArgumentException("Unsupported comparison types: " + value1.getClass() + ", " + value2.getClass());
        }
    }

    /**
     * Metoda sortująca listę elementów przy użyciu algorytmu QuickSort.
     *
     * @param data          lista elementów do posortowania
     * @param direction     kierunek sortowania, "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations maksymalna liczba iteracji sortowania do wykonania
     * @param <E>           typ danych implementujący interfejs Comparable
     * @return mapa zawierająca wyniki sortowania z danymi posortowanymi oraz czasem wykonania
     */
    @Override
    public <E extends Comparable<E>> Map<String, Object> sortList(List<E> data, String direction, int maxIterations) {
        logger.info("Starting QuickSort with direction: {}, maxIterations: {}", direction, maxIterations);

        long startTime = System.nanoTime();
        List<E> sortedData = new ArrayList<>(data);  // Create a copy of the list to avoid external modifications
        int iterations = sortRecursive(sortedData, 0, sortedData.size() - 1, direction, maxIterations, new int[]{0});
        long duration = System.nanoTime() - startTime;

        logger.info("QuickSort completed in {} ms after {} iterations.", duration / 1_000_000.0, iterations);

        // Return result as a Map
        return Map.of(
                "sortedData", sortedData,
                "executionTime", duration / 1_000_000.0
        );
    }

    /**
     * Rekurencyjna metoda sortowania dla listy elementów.
     *
     * @param data          lista elementów do posortowania
     * @param low           indeks początku segmentu do posortowania
     * @param high          indeks końca segmentu do posortowania
     * @param direction     kierunek sortowania, "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations maksymalna liczba iteracji sortowania do wykonania
     * @param iterations    wskaźnik liczby iteracji
     * @param <E>           typ danych implementujący interfejs Comparable
     * @return liczba wykonanych iteracji
     */
    private <E extends Comparable<E>> int sortRecursive(List<E> data, int low, int high, String direction, int maxIterations, int[] iterations) {
        if (low < high && (iterations[0] < maxIterations || maxIterations == 0)) {
            int pivotIndex = partition(data, low, high, direction, maxIterations, iterations);

            // Recursive calls to sort the two partitions
            if (iterations[0] < maxIterations || maxIterations == 0) {
                sortRecursive(data, low, pivotIndex - 1, direction, maxIterations, iterations);
            }
            if (iterations[0] < maxIterations || maxIterations == 0) {
                sortRecursive(data, pivotIndex + 1, high, direction, maxIterations, iterations);
            }
        }
        return iterations[0];
    }

    /**
     * Metoda dzieląca listę elementów na podtablice według wyboru pivotu.
     *
     * @param data          lista elementów do posortowania
     * @param low           indeks początku segmentu do podziału
     * @param high          indeks końca segmentu do podziału
     * @param direction     kierunek sortowania, "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations maksymalna liczba iteracji sortowania do wykonania
     * @param iterations    wskaźnik liczby iteracji
     * @param <E>           typ danych implementujący interfejs Comparable
     * @return indeks pivotu
     */
    private <E extends Comparable<E>> int partition(List<E> data, int low, int high, String direction, int maxIterations, int[] iterations) {
        E pivotValue = data.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (iterations[0] == maxIterations && maxIterations > 0) break;

            // Comparison logic for direction
            int comparison = data.get(j).compareTo(pivotValue);
            if ("desc".equalsIgnoreCase(direction)) {
                comparison = -comparison;
            }

            if (comparison <= 0) {
                i++;
                // Swap elements
                E temp = data.get(i);
                data.set(i, data.get(j));
                data.set(j, temp);
            }
            iterations[0]++;
        }

        // Swap the pivot into its correct place
        E temp = data.get(i + 1);
        data.set(i + 1, data.get(high));
        data.set(high, temp);

        return i + 1;
    }
}