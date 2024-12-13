package pl.put.poznan.sortingmadness.logic.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sortingmadness.logic.SortingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Klasa implementująca algorytm sortowania przez liczenie.
 * <p>
 * Algorytm polega na zliczaniu wystąpień wartości w danych wejściowych, a następnie odtwarza uporządkowaną
 * listę na podstawie zliczeń. Oferuje wsparcie dla sortowania rosnącego i malejącego oraz ograniczenia liczby iteracji.
 */
public class CountingSort implements SortingStrategy {
    private static final Logger logger = LoggerFactory.getLogger(CountingSort.class);

    /**
     * Sortuje dane mapowane przy użyciu algorytmu sortowania przez liczenie.
     *
     * @param data          lista map zawierających dane do posortowania
     * @param key           klucz używany do pobierania wartości z map w celu porównania
     * @param direction     kierunek sortowania: "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations maksymalna liczba iteracji; 0 oznacza brak ograniczenia
     * @return mapa zawierająca posortowane dane oraz czas wykonania w milisekundach
     * @param <E> typ wartości w mapach, który implementuje {@link Comparable}
     */
    @Override
    public <E extends Comparable<E>> Map<String, Object> sort(List<Map<String, E>> data, List<String> keys, String direction, int maxIterations) {
        logger.info("Starting CountingSort with keys: {}, direction: {}, maxIterations: {}", keys, direction, maxIterations);

        long startTime = System.nanoTime();

        // Zliczanie wystąpień dla każdego klucza z priorytetem kluczy
        List<Map<E, Integer>> countMaps = new ArrayList<>();
        for (String key : keys) {
            Map<E, Integer> countMap = new HashMap<>();
            for (Map<String, E> entry : data) {
                E value = entry.get(key);
                countMap.put(value, countMap.getOrDefault(value, 0) + 1);
            }
            countMaps.add(countMap);
        }

        // Sortowanie kluczy na podstawie kierunku dla każdego poziomu klucza
        List<List<E>> sortedKeysList = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++) {
            Map<E, Integer> countMap = countMaps.get(i);
            List<E> sortedKeys = new ArrayList<>(countMap.keySet());
            sortedKeys.sort((a, b) -> {
                int comparison = a.compareTo(b);
                return "desc".equalsIgnoreCase(direction) ? -comparison : comparison;
            });
            sortedKeysList.add(sortedKeys);
        }

        // Rekonstrukcja posortowanej listy na podstawie priorytetu kluczy
        List<Map<String, E>> sortedData = new ArrayList<>(data);
        for (int level = keys.size() - 1; level >= 0; level--) {
            String currentKey = keys.get(level);
            List<E> sortedKeys = sortedKeysList.get(level);

            List<Map<String, E>> tempData = new ArrayList<>();
            for (E sortedKey : sortedKeys) {
                for (Map<String, E> entry : sortedData) {
                    if (entry.get(currentKey).equals(sortedKey)) {
                        tempData.add(entry);
                    }
                }
            }
            sortedData = tempData;
        }

        long duration = System.nanoTime() - startTime;
        logger.info("CountingSort completed in {} ms.", duration / 1_000_000.0);

        return Map.of(
                "sortedData", sortedData,
                "executionTime", duration / 1_000_000.0
        );
    }


    /**
     * Sortuje listę elementów przy użyciu algorytmu sortowania przez liczenie.
     *
     * @param data          lista danych do posortowania
     * @param direction     kierunek sortowania: "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations maksymalna liczba iteracji; 0 oznacza brak ograniczenia
     * @return mapa zawierająca posortowaną listę danych oraz czas wykonania w milisekundach
     * @param <E> typ elementów listy, który implementuje {@link Comparable}
     */
    @Override
    public <E extends Comparable<E>> Map<String, Object> sortList(List<E> data, String direction, int maxIterations) {
        logger.info("Starting CountingSort with direction: {}, maxIterations: {}", direction, maxIterations);

        long startTime = System.nanoTime();

        // Zliczanie wystąpień wartości
        Map<Comparable, Integer> countMap = new HashMap<>();
        for (Comparable value : data) {
            countMap.put(value, countMap.getOrDefault(value, 0) + 1);
        }

        // Sortowanie kluczy na podstawie kierunku
        List<Comparable> sortedKeys = new ArrayList<>(countMap.keySet());
        sortedKeys.sort((a, b) -> {
            int comparison = a.compareTo(b);
            return "desc".equalsIgnoreCase(direction) ? -comparison : comparison;
        });

        // Rekonstrukcja posortowanej listy na podstawie zliczeń
        List<Comparable> sortedData = new ArrayList<>();
        int iterations = 0;
        for (Comparable sortedKey : sortedKeys) {
            int count = countMap.get(sortedKey);
            while (count > 0) {
                if (iterations == maxIterations && maxIterations > 0) break;
                sortedData.add(sortedKey);
                count--;
                iterations++;
            }
            if (iterations == maxIterations && maxIterations > 0) break;
        }

        long duration = System.nanoTime() - startTime;
        logger.info("CountingSort completed in {} ms.", duration / 1_000_000.0);

        return Map.of(
                "sortedData", sortedData,
                "executionTime", duration / 1_000_000.0
        );
    }
}
