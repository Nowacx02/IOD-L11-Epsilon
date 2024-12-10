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
 * Algorytm polega na iteracyjnym wstawianiu elementów na właściwe miejsca w uporządkowanej części tablicy,
 * aby uzyskać posortowaną listę. Działa zarówno dla sortowania rosnącego, jak i malejącego.
 */
public class InsertionSort implements SortingStrategy {
    private static final Logger logger = LoggerFactory.getLogger(InsertionSort.class);

    /**
     * Sortuje dane mapowane przy użyciu algorytmu sortowania przez wstawianie.
     *
     * @param data          lista map zawierających dane do posortowania
     * @param key           klucz używany do pobierania wartości z map w celu porównania
     * @param direction     kierunek sortowania: "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations maksymalna liczba iteracji; 0 oznacza brak ograniczenia
     * @return mapa zawierająca posortowane dane oraz czas wykonania w milisekundach
     * @param <E> typ wartości w mapach, który implementuje {@link Comparable}
     */
    @Override
    public <E extends Comparable<E>> Map<String, Object> sort(List<Map<String, E>> data, String key, String direction, int maxIterations) {
        logger.info("Starting InsertionSort with key: {}, direction: {}, maxIterations: {}", key, direction, maxIterations);

        int n = data.size();
        int iterations = 0;
        long startTime = System.nanoTime();

        // Algorytm sortowania przez wstawianie
        for (int i = 1; i < n; i++) {
            if (iterations == maxIterations && maxIterations > 0) break;

            Map<String, E> current = data.get(i);
            int j = i - 1;

            while (j >= 0) {
                if (iterations == maxIterations && maxIterations > 0) break;

                int comparison = compareValues(data.get(j).get(key), current.get(key));
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
                "sortedData", data,
                "executionTime", duration / 1_000_000.0
        );
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
            throw new IllegalArgumentException("Unsupported comparison types: " + value1.getClass() + ", " + value2.getClass());
        }
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
}
