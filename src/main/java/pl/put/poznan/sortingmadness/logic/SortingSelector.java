/**
 * Klasa {@code SortingSelector} odpowiada za wybór optymalnego algorytmu sortowania
 * w zależności od charakterystyki danych wejściowych.
 * Implementuje mechanizmy dopasowania algorytmu zarówno dla list obiektów
 * implementujących {@link Comparable}, jak i list map ({@link Map}).
 */
package pl.put.poznan.sortingmadness.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sortingmadness.logic.algorithms.*;

import java.util.*;

public class SortingSelector {

    /** Logger do śledzenia działania klasy */
    private static final Logger logger = LoggerFactory.getLogger(SortingSelector.class);

    /**
     * Mapa odwzorowująca nazwy algorytmów na odpowiadające im strategie sortowania.
     */
    private static final Map<String, SortingStrategy> algorithmMap = initializeAlgorithmMap();

    /**
     * Inicjalizuje mapę algorytmów sortowania.
     *
     * @return Mapa algorytmów sortowania
     */
    private static Map<String, SortingStrategy> initializeAlgorithmMap() {
        Map<String, SortingStrategy> map = new HashMap<>();
        map.put("bubble", new BubbleSort());
        map.put("selection", new SelectionSort());
        map.put("insertion", new InsertionSort());
        map.put("merge", new MergeSort());
        map.put("counting", new CountingSort());
        map.put("quick", new QuickSort());
        return map;
    }

    /**
     * Wybiera optymalny algorytm sortowania dla listy obiektów implementujących {@link Comparable}.
     *
     * @param data Lista danych do posortowania
     * @param descendingOrder Flaga określająca, czy sortowanie powinno być malejące
     * @return Nazwa wybranego algorytmu sortowania
     * @throws IllegalArgumentException jeśli dane wejściowe są nieobsługiwane
     */
    public static String selectAlgorithmList(List<?> data, boolean descendingOrder) {
        if (!data.isEmpty() && data.get(0) instanceof Comparable) {
            return selectAlgorithmForComparableList((List<Comparable>) data, descendingOrder);
        } else {
            throw new IllegalArgumentException("Unsupported data type");
        }
    }

    /**
     * Wybiera optymalny algorytm sortowania dla listy map.
     *
     * @param data Lista map do posortowania
     * @param keysToSort Klucze, według których należy sortować
     * @param descendingOrder Flaga określająca, czy sortowanie powinno być malejące
     * @return Nazwa wybranego algorytmu sortowania
     * @throws IllegalArgumentException jeśli dane wejściowe są nieobsługiwane
     */
    public static String selectAlgorithmMap(List<Map<String, Comparable>> data, List<String> keysToSort, boolean descendingOrder) {
        if (!data.isEmpty() && data.get(0) instanceof Map) {
            return selectAlgorithmForMapList((List<Map<String, Comparable>>) data, keysToSort, descendingOrder);
        } else {
            throw new IllegalArgumentException("Unsupported data type");
        }
    }

    /**
     * Wybiera algorytm sortowania dla listy obiektów {@link Comparable} na podstawie jej charakterystyk.
     *
     * @param data Lista danych do analizy
     * @param descendingOrder Flaga określająca porządek sortowania
     * @return Nazwa wybranego algorytmu sortowania
     */
    private static String selectAlgorithmForComparableList(List<Comparable> data, boolean descendingOrder) {
        if (isSorted(data, descendingOrder)) {
            return "insertion";
        }
        if (hasManyDuplicates(data)) {
            return "merge";
        }
        if (numericData(data)) {
            return "counting";
        }
        if (data.size() <= 10) {
            return "bubble";
        }
        if (data.size() <= 100) {
            return "insertion";
        }
        if (data.size() <= 1000) {
            return "merge";
        }
        return "quick";
    }

    /**
     * Wybiera algorytm sortowania dla listy map na podstawie jej charakterystyk.
     *
     * @param data Lista map do analizy
     * @param keysToSort Klucze do sortowania
     * @param descendingOrder Flaga określająca porządek sortowania
     * @return Nazwa wybranego algorytmu sortowania
     */
    private static String selectAlgorithmForMapList(List<Map<String, Comparable>> data, List<String> keysToSort, boolean descendingOrder) {
        if (isSortedMap(data, keysToSort, descendingOrder)) {
            return "insertion";
        }
        if (hasManyDuplicatesMap(data)) {
            return "merge";
        }
        if (numericDataMap(data, keysToSort)) {
            return "counting";
        }
        if (data.size() <= 10) {
            return "bubble";
        }
        if (data.size() <= 100) {
            return "insertion";
        }
        if (data.size() <= 1000) {
            return "merge";
        }
        return "quick";
    }

    /**
     * Sprawdza, czy lista jest posortowana.
     *
     * @param data Lista danych do sprawdzenia
     * @param descendingOrder Flaga określająca porządek sortowania
     * @return {@code true} jeśli lista jest posortowana, {@code false} w przeciwnym wypadku
     */
    private static boolean isSorted(List<Comparable> data, boolean descendingOrder) {
        for (int i = 1; i < data.size(); i++) {
            if (descendingOrder ? data.get(i).compareTo(data.get(i - 1)) > 0 : data.get(i).compareTo(data.get(i - 1)) < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Sprawdza, czy lista map jest posortowana.
     *
     * @param data Lista map do sprawdzenia
     * @param keysToSort Klucze do sprawdzenia porządku
     * @param descendingOrder Flaga określająca porządek sortowania
     * @return {@code true} jeśli lista map jest posortowana, {@code false} w przeciwnym wypadku
     */
    private static boolean isSortedMap(List<Map<String, Comparable>> data, List<String> keysToSort, boolean descendingOrder) {
        for (int i = 1; i < data.size(); i++) {
            Map<String, Comparable> current = data.get(i);
            Map<String, Comparable> previous = data.get(i - 1);
            for (String key : keysToSort) {
                int comparison = current.get(key).compareTo(previous.get(key));
                if ((descendingOrder && comparison > 0) || (!descendingOrder && comparison < 0)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Sprawdza, czy lista zawiera wiele duplikatów.
     *
     * @param data Lista danych do sprawdzenia
     * @return {@code true} jeśli lista zawiera wiele duplikatów, {@code false} w przeciwnym wypadku
     */
    private static boolean hasManyDuplicates(List<Comparable> data) {
        Set<Comparable> set = new HashSet<>(data);
        return set.size() < data.size() / 2;
    }

    /**
     * Sprawdza, czy lista map zawiera wiele duplikatów.
     *
     * @param data Lista map do sprawdzenia
     * @return {@code true} jeśli lista map zawiera wiele duplikatów, {@code false} w przeciwnym wypadku
     */
    private static boolean hasManyDuplicatesMap(List<Map<String, Comparable>> data) {
        Set<Map<String, Comparable>> set = new HashSet<>(data);
        return set.size() < data.size() / 2;
    }

    /**
     * Sprawdza, czy wszystkie elementy listy są typu numerycznego.
     *
     * @param data Lista danych do sprawdzenia
     * @return {@code true} jeśli wszystkie elementy są numeryczne, {@code false} w przeciwnym wypadku
     */
    private static boolean numericData(List<Comparable> data) {
        return data.stream().allMatch(e -> e instanceof Integer);
    }

    /**
     * Sprawdza, czy wszystkie klucze w listach map są typu numerycznego.
     *
     * @param data Lista map do sprawdzenia
     * @param keysToSort Klucze do sprawdzenia
     * @return {@code true} jeśli wszystkie klucze są numeryczne, {@code false} w przeciwnym wypadku
     */
    private static boolean numericDataMap(List<Map<String, Comparable>> data, List<String> keysToSort) {
        for (Map<String, Comparable> map : data) {
            for (String key : keysToSort) {
                if (!(map.get(key) instanceof Integer)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Zwraca strategię sortowania na podstawie nazwy algorytmu.
     *
     * @param algorithm Nazwa algorytmu sortowania
     * @return Strategia sortowania
     * @throws IllegalArgumentException jeśli nazwa algorytmu jest nieznana
     */
    public static SortingStrategy getSortingStrategy(String algorithm) {
        SortingStrategy strategy = algorithmMap.get(algorithm);
        if (strategy == null) {
            throw new IllegalArgumentException("Unknown sorting algorithm: " + algorithm);
        }
        return strategy;
    }
}
