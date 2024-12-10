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
     * @param key           klucz używany do pobierania wartości z map w celu porównania
     * @param direction     kierunek sortowania: "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations maksymalna liczba iteracji; 0 oznacza brak ograniczenia
     * @return mapa zawierająca posortowane dane oraz czas wykonania w milisekundach
     * @param <E> typ elementów do sortowania, który implementuje {@link Comparable}
     */
    @Override
    public <E extends Comparable<E>> Map<String, Object> sort(List<Map<String, E>> data, String key, String direction, int maxIterations) {
        logger.info("Starting BubbleSort with key: {}, direction: {}, maxIterations: {}", key, direction, maxIterations);

        int n = data.size();
        boolean swapped;
        int iterations = 0;
        long startTime = System.nanoTime();

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - i - 1 && (iterations < maxIterations || maxIterations <= 0); j++) {
                int comparison = compareValues(data.get(j).get(key), data.get(j + 1).get(key));

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
     * Porównuje dwie wartości, zwracając wynik porównania jako liczbę całkowitą.
     * <p>
     * Obsługuje wartości liczb całkowitych, zmiennoprzecinkowych oraz ciągów znaków.
     *
     * @param value1 pierwsza wartość do porównania
     * @param value2 druga wartość do porównania
     * @return wynik porównania: -1 jeśli value1 < value2, 0 jeśli są równe, 1 jeśli value1 > value2
     * @throws IllegalArgumentException jeśli wartości mają nieobsługiwane typy
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
