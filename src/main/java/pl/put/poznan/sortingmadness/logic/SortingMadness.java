package pl.put.poznan.sortingmadness.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sortingmadness.logic.algorithms.*;

import java.util.List;
import java.util.Map;

/**
 * Klasa zarządzająca różnymi algorytmami sortowania (Bubble, Insertion, Selection, Quick, Merge, Counting).
 * Umożliwia sortowanie danych na podstawie wybranego algorytmu i klucza do sortowania.
 */
public class SortingMadness {
    private static final Logger logger = LoggerFactory.getLogger(SortingMadness.class);

    /**
     * Konstruktor domyślny.
     */
    public SortingMadness() {}

    /**
     * Sortuje dane na podstawie wybranego algorytmu sortowania.
     *
     * @param data           lista map zawierających dane do posortowania
     * @param keys            klucz używany do sortowania wartości w mapach
     * @param algorithm      nazwa algorytmu sortującego (np. "bubble", "insertion", "selection", "quick", "merge", "counting")
     * @param direction      kierunek sortowania: "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations  maksymalna liczba iteracji sortowania; wartość 0 oznacza brak ograniczenia
     * @return mapa zawierająca wyniki sortowania: posortowane dane oraz czas wykonania w milisekundach
     * @throws IllegalArgumentException jeśli dane są puste lub algorytm nie jest rozpoznany
     */
    public Map<String, Object> sortData(List<Map<String, Comparable>> data, List<String> keys, String algorithm, String direction, int maxIterations) {
        logger.info("Starting sortData with algorithm: {}, key: {}, direction: {}, maxIterations: {}",
                algorithm, keys, direction, maxIterations);
            if (direction == null || (!direction.equals("ASC") && !direction.equals("DESC"))) {
                throw new IllegalArgumentException("Sorting direction must be specified.");
            }
        if (algorithm == null ) {
            throw new IllegalArgumentException("Sorting direction must be specified.");
        }

        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Dataset is empty or null.");
        }
        if (maxIterations <= 0) {
            maxIterations = 0;
        }

        SortingStrategy strategy = getStrategy(algorithm);
        return strategy.sort(data, keys, direction, maxIterations);
    }

    /**
     * Sortuje listę elementów na podstawie wybranego algorytmu sortowania.
     *
     * @param data          lista elementów do posortowania
     * @param algorithm     nazwa algorytmu sortującego (np. "bubble", "insertion", "selection", "quick", "merge", "counting")
     * @param direction     kierunek sortowania: "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations maksymalna liczba iteracji sortowania; wartość 0 oznacza brak ograniczenia
     * @return mapa zawierająca wyniki sortowania: posortowane dane oraz czas wykonania w milisekundach
     * @throws IllegalArgumentException jeśli dane są puste lub algorytm nie jest rozpoznany
     */
    public Map<String, Object> sortDataList(List<Comparable> data, String algorithm, String direction, int maxIterations) {
        logger.info("Starting sortDataList with algorithm: {}, direction: {}, maxIterations: {}",
                algorithm, direction, maxIterations);

        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Dataset is empty or null.");
        }
        if (maxIterations < 0) {
            maxIterations = 0;
        }

        SortingStrategy strategy = getStrategy(algorithm);
        return strategy.sortList(data, direction, maxIterations);
    }

    /**
     * Wybiera odpowiednią strategię sortowania na podstawie nazwy algorytmu.
     *
     * @param algorithm nazwa algorytmu sortowania (np. "bubble", "insertion", "selection", "quick", "merge", "counting")
     * @return instancja klasy implementującej wybrany algorytm sortowania
     * @throws IllegalArgumentException jeśli podana nazwa algorytmu jest nierozpoznana
     */
    public SortingStrategy getStrategy(String algorithm) {
        switch (algorithm.toLowerCase()) {
            case "bubble":
                return new BubbleSort();
            case "insertion":
                return new InsertionSort();
            case "selection":
                return new SelectionSort();
            case "quick":
                return new QuickSort();
            case "merge":
                return new MergeSort();
            case "counting":
                return new CountingSort();
            default:
                throw new IllegalArgumentException("Unknown sorting algorithm: " + algorithm);
        }
    }
}
