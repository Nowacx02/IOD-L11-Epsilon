package pl.put.poznan.sortingmadness.logic.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sortingmadness.logic.SortingStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Klasa implementująca algorytm sortowania bąbelkowego.
 * <p>
 * Algorytm przegląda dane i na każdej iteracji porównuje sąsiadujące elementy, zamieniając je miejscami,
 * jeśli są w złej kolejności. Proces powtarza się, aż wszystkie elementy zostaną posortowane.
 * Oferuje wsparcie dla różnych typów danych, kierunków sortowania oraz ograniczeń liczby iteracji.
 */
public class BubbleSort implements SortingStrategy {
    private static final Logger logger = LoggerFactory.getLogger(BubbleSort.class);

    /**
     * Sortuje dane przy użyciu algorytmu sortowania bąbelkowego.
     *
     * @param data          lista map zawierających dane do posortowania
     * @param keys          klucz używany do pobierania wartości z map w celu porównania
     * @param direction     kierunek sortowania: "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations maksymalna liczba iteracji; 0 oznacza brak ograniczenia
     * @return mapa zawierająca posortowane dane oraz czas wykonania w milisekundach
     * @param <E> typ elementów do sortowania, który implementuje {@link Comparable}
     */
    @Override
    public <E extends Comparable<E>> Map<String, Object> sort(List<Map<String, E>> data, List<String> keys, String direction, int maxIterations) {
        logger.info("Starting BubbleSort with keys: {}, direction: {}, maxIterations: {}", keys, direction, maxIterations);

        int n = data.size();
        boolean swapped;
        int iterations = 0;
        long startTime = System.nanoTime();

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - i - 1 && (iterations < maxIterations || maxIterations <= 0); j++) {
                int comparison = compareByKeys(data.get(j), data.get(j + 1), keys);

                if ("desc".equalsIgnoreCase(direction)) {
                    comparison = -comparison;
                }

                if (comparison > 0) {
                    Collections.swap(data, j, j + 1);
                    swapped = true;
                }

                iterations++;

                if (iterations >= maxIterations && maxIterations > 0) {
                    break;
                }
            }

            if (!swapped || (iterations >= maxIterations && maxIterations > 0)) {
                break;
            }
        }

        long duration = System.nanoTime() - startTime;
        logger.info("BubbleSort completed in {} ms.", duration / 1_000_000.0);

        return Map.of(
                "sortedData", data,
                "executionTime", duration / 1_000_000.0
        );
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
     * Compares two values, returning the comparison result as an integer.
     *
     * Supports integer, double, and string types.
     *
     * @param value1 the first value to compare
     * @param value2 the second value to compare
     * @return the comparison result: -1 if value1 < value2, 0 if equal, 1 if value1 > value2
     * @throws IllegalArgumentException if the values have unsupported types
     */
    private static <E extends Comparable<E>> int compareValues(E value1, E value2) {
        return value1.compareTo(value2);
    }

    /**
     * Sortuje listę obiektów implementujących {@link Comparable} przy użyciu algorytmu sortowania bąbelkowego.
     *
     * @param data          lista danych do posortowania
     * @param direction     kierunek sortowania: "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations maksymalna liczba iteracji; 0 oznacza brak ograniczenia
     * @return mapa zawierająca posortowaną listę danych oraz czas wykonania w milisekundach
     * @param <E> typ elementów listy, który implementuje {@link Comparable}
     * @throws IllegalArgumentException jeśli lista danych lub kierunek są null
     */
    @Override
    public <E extends Comparable<E>> Map<String, Object> sortList(List<E> data, String direction, int maxIterations) {
        if (data == null || direction == null) {
            throw new IllegalArgumentException("Data list and direction must not be null");
        }

        logger.info("Starting BubbleSort with direction: {}, maxIterations: {}", direction, maxIterations);

        int n = data.size();
        boolean swapped;
        int iterations = 0;
        long startTime = System.nanoTime();

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (iterations >= maxIterations && maxIterations > 0) {
                    break;
                }

                int comparison = data.get(j).compareTo(data.get(j + 1));
                if ("desc".equalsIgnoreCase(direction)) {
                    comparison = -comparison;
                }

                if (comparison > 0) {
                    Collections.swap(data, j, j + 1);
                    swapped = true;
                }
                iterations++;
            }

            if (iterations >= maxIterations && maxIterations > 0) {
                break;
            }

            if (!swapped) break;
        }

        long duration = System.nanoTime() - startTime;
        double executionTimeMs = duration / 1_000_000.0;
        logger.info("BubbleSort completed in {} ms after {} iterations.", executionTimeMs, iterations);

        return Map.of(
                "sortedData", new ArrayList<>(data),
                "executionTime", executionTimeMs
        );
    }
}
