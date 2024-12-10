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
     * @param key           klucz używany do porównania wartości w mapach
     * @param direction     kierunek sortowania: "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations maksymalna liczba iteracji sortowania; wartość 0 oznacza brak ograniczenia
     * @param <E>           typ elementów implementujących interfejs {@link Comparable}
     * @return mapa zawierająca posortowane dane oraz czas wykonania sortowania w milisekundach
     * @throws IllegalArgumentException jeśli typ wartości w mapach nie jest obsługiwany
     */
    @Override
    public <E extends Comparable<E>> Map<String, Object> sort(List<Map<String, E>> data, String key, String direction, int maxIterations) {
        logger.info("Starting SelectionSort with key: {}, direction: {}, maxIterations: {}", key, direction, maxIterations);

        int n = data.size();
        int iterations = 0;
        long startTime = System.nanoTime();

        // Implementacja algorytmu Selection Sort
        for (int i = 0; i < n - 1; i++) {
            int selectedIdx = i;

            for (int j = i + 1; j < n; j++) {
                if (iterations == maxIterations && maxIterations > 0) break;

                int comparison = compareValues(data.get(j).get(key), data.get(selectedIdx).get(key));
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
            Map<String, E> temp = data.get(selectedIdx);
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

    /**
     * Porównuje dwie wartości implementujące interfejs {@link Comparable}.
     *
     * @param value1 pierwsza wartość do porównania
     * @param value2 druga wartość do porównania
     * @return wynik porównania: -1 jeśli value1 < value2, 0 jeśli są równe, 1 jeśli value1 > value2
     * @throws IllegalArgumentException jeśli typ wartości nie jest obsługiwany
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
