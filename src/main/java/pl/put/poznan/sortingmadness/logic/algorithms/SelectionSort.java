package pl.put.poznan.sortingmadness.logic.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sortingmadness.logic.SortingStrategy;

import java.util.List;
import java.util.Map;

/**
 * Klasa implementująca algorytm sortowania przez wybór (SelectionSort).
 * Algorytm SelectionSort polega na wyborze najmniejszego (lub największego, w zależności od kierunku) elementu w każdym kroku i umieszczeniu go na odpowiedniej pozycji.
 */
public class SelectionSort implements SortingStrategy {
    private static final Logger logger = LoggerFactory.getLogger(SelectionSort.class);

    /**
     * Metoda sortująca dane przy użyciu algorytmu SelectionSort.
     *
     * @param data           lista map zawierających dane do posortowania
     * @param key            klucz do użycia przy porównaniu wartości w mapach
     * @param direction      kierunek sortowania, "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations  maksymalna liczba iteracji sortowania do wykonania
     * @return mapa zawierająca wyniki sortowania z danymi posortowanymi oraz czasem wykonania
     */
    @Override
    public Map<String, Object> sort(List<Map<String, String>> data, String key, String direction, int maxIterations) {
        logger.info("Starting SelectionSort with key: {}, direction: {}, maxIterations: {}", key, direction, maxIterations);

        int n = data.size();
        int iterations = 0;
        long startTime = System.nanoTime();

        // Selection Sort Algorithm
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

            // Swap the selected element with the current element
            Map<String, String> temp = data.get(selectedIdx);
            data.set(selectedIdx, data.get(i));
            data.set(i, temp);
        }

        long duration = System.nanoTime() - startTime;
        logger.info("SelectionSort completed in {} ms.", duration / 1_000_000.0);

        // Return result as a Map
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
