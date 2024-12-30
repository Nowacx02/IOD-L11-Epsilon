package pl.put.poznan.sortingmadness.logic.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sortingmadness.logic.SortingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Klasa implementująca algorytm sortowania przez wstawianie.
 * <p>
 * Algorytm polega na iteracyjnym wstawianiu elementów na właściwe miejsca w uporządkowanej części listy,
 * aby uzyskać posortowaną listę. Obsługuje zarówno sortowanie rosnące, jak i malejące.
 */
public class InsertionSort implements SortingStrategy {
    private static final Logger logger = LoggerFactory.getLogger(InsertionSort.class);

    /**
     * Sortuje dane mapowane przy użyciu algorytmu sortowania przez wstawianie.
     *
     * @param data          lista map zawierających dane do posortowania
     * @param keys          lista kluczy używanych do porównania wartości
     * @param direction     kierunek sortowania: "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations maksymalna liczba iteracji; 0 oznacza brak ograniczenia
     * @return mapa zawierająca posortowane dane oraz czas wykonania w milisekundach
     * @param <E> typ wartości w mapach, który implementuje {@link Comparable}
     */
    @Override
    public <E extends Comparable<E>> Map<String, Object> sort(List<Map<String, E>> data, List<String> keys, String direction, int maxIterations) {
        if (data == null || keys == null) {
            throw new IllegalArgumentException("Input data and keys cannot be null.");
        }
        if (keys.isEmpty()) {
            throw new IllegalArgumentException("Keys list cannot be empty.");
        }
        if (direction == null || (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc"))) {
            throw new IllegalArgumentException("Invalid sorting direction. Use 'asc' or 'desc'.");
        }

        logger.info("Starting InsertionSort with keys: {}, direction: {}, maxIterations: {}", keys, direction, maxIterations);

        int n = data.size();
        int iterations = 0;
        long startTime = System.nanoTime();

        // Algorytm sortowania przez wstawianie
        for (int i = 1; i < n; i++) {
            if (maxIterations > 0 && iterations >= maxIterations) break;

            Map<String, E> current = data.get(i);
            int j = i - 1;

            while (j >= 0) {
                if (maxIterations > 0 && iterations >= maxIterations) break;

                int comparison = compareByKeys(data.get(j), current, keys, direction);

                if (comparison <= 0) break;

                data.set(j + 1, data.get(j));
                j--;
                iterations++;
            }

            data.set(j + 1, current);
        }

        long duration = System.nanoTime() - startTime;
        logger.info("InsertionSort completed in {} ms after {} iterations.", duration / 1_000_000.0, iterations);

        return Map.of(
                "sortedData", data,
                "executionTime", duration / 1_000_000.0
        );
    }

    /**
     * Sortuje listę elementów przy użyciu algorytmu sortowania przez wstawianie.
     *
     * @param data          lista danych do posortowania
     * @param direction     kierunek sortowania: "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations maksymalna liczba iteracji; 0 oznacza brak ograniczenia
     * @return mapa zawierająca posortowaną listę danych oraz czas wykonania w milisekundach
     * @param <E> typ elementów listy, który implementuje {@link Comparable}
     */
    @Override
    public <E extends Comparable<E>> Map<String, Object> sortList(List<E> data, String direction, int maxIterations) {
        if (data == null) {
            throw new IllegalArgumentException("Input data cannot be null.");
        }
        if (direction == null || (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc"))) {
            throw new IllegalArgumentException("Invalid sorting direction. Use 'asc' or 'desc'.");
        }

        logger.info("Starting InsertionSort with direction: {}, maxIterations: {}", direction, maxIterations);

        int n = data.size();
        int iterations = 0;
        long startTime = System.nanoTime();

        // Algorytm sortowania przez wstawianie
        for (int i = 1; i < n; i++) {
            if (maxIterations > 0 && iterations >= maxIterations) break;

            E current = data.get(i);
            int j = i - 1;

            while (j >= 0) {
                if (maxIterations > 0 && iterations >= maxIterations) break;

                int comparison = data.get(j).compareTo(current);
                if ("desc".equalsIgnoreCase(direction)) {
                    comparison = -comparison;
                }

                if (comparison <= 0) break;

                data.set(j + 1, data.get(j));
                j--;
                iterations++;
            }

            data.set(j + 1, current);
        }

        long duration = System.nanoTime() - startTime;
        logger.info("InsertionSort completed in {} ms after {} iterations.", duration / 1_000_000.0, iterations);

        return Map.of(
                "sortedData", new ArrayList<>(data),
                "executionTime", duration / 1_000_000.0
        );
    }

    /**
     * Pomocnicza metoda do porównywania dwóch map na podstawie listy kluczy.
     * Uwzględnia kierunek sortowania.
     *
     * @param map1      pierwsza mapa
     * @param map2      druga mapa
     * @param keys      lista kluczy do porównania
     * @param direction kierunek sortowania ("asc" lub "desc")
     * @return wynik porównania: -1, 0, 1
     */
    private static <E extends Comparable<E>> int compareByKeys(Map<String, E> map1, Map<String, E> map2, List<String> keys, String direction) {
        for (String key : keys) {
            E value1 = map1.get(key);
            E value2 = map2.get(key);

            if (value1 == null || value2 == null) {
                throw new IllegalArgumentException("Null values are not supported for key: " + key);
            }

            int comparison = compareValues(value1, value2);

            if ("desc".equalsIgnoreCase(direction)) {
                comparison = -comparison;
            }

            if (comparison != 0) {
                return comparison;
            }
        }
        return 0;
    }

    /**
     * Pomocnicza metoda do porównywania wartości, obsługuje porównania liczbowe i tekstowe.
     *
     * @param value1 pierwsza wartość do porównania
     * @param value2 druga wartość do porównania
     * @return wynik porównania: -1, 0, 1
     */
    private static <E extends Comparable<E>> int compareValues(E value1, E value2) {
        return value1.compareTo(value2);
    }
}
