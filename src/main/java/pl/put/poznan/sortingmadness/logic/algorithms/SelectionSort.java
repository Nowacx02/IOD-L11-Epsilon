package pl.put.poznan.sortingmadness.logic.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sortingmadness.logic.SortingStrategy;

import java.util.List;
import java.util.Map;

/**
 * Klasa implementująca algorytm sortowania przez wybór (SelectionSort).
 * Algorytm SelectionSort polega na wyborze najmniejszego (lub największego, w zależności od kierunku) elementu w każdym kroku
 * i umieszczeniu go na odpowiedniej pozycji.
 * Jest to algorytm o złożoności czasowej O(n^2).
 */
public class SelectionSort implements SortingStrategy {
    private static final Logger logger = LoggerFactory.getLogger(SelectionSort.class);

    /**
     * Sortuje dane przy użyciu algorytmu SelectionSort.
     *
     * @param data          lista map zawierających dane do posortowania
     * @param keys           klucz używany do porównania wartości w mapach
     * @param direction     kierunek sortowania: "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations maksymalna liczba iteracji sortowania; wartość 0 oznacza brak ograniczenia
     * @param <E>           typ elementów implementujących interfejs {@link Comparable}
     * @return mapa zawierająca posortowane dane oraz czas wykonania sortowania w milisekundach
     * @throws IllegalArgumentException jeśli typ wartości w mapach nie jest obsługiwany
     */
    @Override
    public <E extends Comparable<E>> Map<String, Object> sort(List<Map<String, E>> data, List<String> keys, String direction, int maxIterations) {
        logger.info("Starting InsertionSort with keys: {}, direction: {}, maxIterations: {}", keys, direction, maxIterations);

        int n = data.size();
        int iterations = 0;
        long startTime = System.nanoTime();

        // Algorytm sortowania przez wstawianie dla wielu kluczy
        for (int i = 1; i < n; i++) {
            if (iterations == maxIterations && maxIterations > 0) break;

            Map<String, E> current = data.get(i);
            int j = i - 1;

            while (j >= 0) {
                if (iterations == maxIterations && maxIterations > 0) break;

                int comparison = compareByKeys(data.get(j), current, keys);
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
     * Sortuje listę elementów przy użyciu algorytmu SelectionSort.
     *
     * @param data          lista elementów do posortowania
     * @param direction     kierunek sortowania: "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations maksymalna liczba iteracji sortowania; wartość 0 oznacza brak ograniczenia
     * @param <E>           typ elementów implementujących interfejs {@link Comparable}
     * @return mapa zawierająca posortowane dane oraz czas wykonania sortowania w milisekundach
     */
    @Override
    public <E extends Comparable<E>> Map<String, Object> sortList(List<E> data, String direction, int maxIterations) {
        logger.info("Starting SelectionSort with direction: {}, maxIterations: {}", direction, maxIterations);

        int n = data.size();
        int iterations = 0;
        long startTime = System.nanoTime();
        // Implementacja algorytmu Selection Sort dla listy elementów
        for (int i = 0; i < n - 1; i++) {
            int selectedIdx = i;

            for (int j = i + 1; j < n; j++) {
                if (iterations == maxIterations && maxIterations > 0) break;

                int comparison = data.get(j).compareTo(data.get(selectedIdx));
                if ("desc".equalsIgnoreCase(direction)) {
                    comparison = -comparison;
                }

                if (comparison < 0) {
                    selectedIdx = j;
                }
                iterations++;
            }

            if (iterations == maxIterations && maxIterations > 0) break;
            // Zamiana miejscami elementów
            E temp = data.get(selectedIdx);
            data.set(selectedIdx, data.get(i));
            data.set(i, temp);
        }

        long duration = System.nanoTime() - startTime;
        logger.info("SelectionSort completed in {} ms.", duration / 1_000_000.0);

        return Map.of(
                "sortedData", data,
                "executionTime", duration / 1_000_000.0
        );
    }
}
