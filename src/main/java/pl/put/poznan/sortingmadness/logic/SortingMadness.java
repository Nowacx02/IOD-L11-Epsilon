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
     * Metoda do sortowania danych przy użyciu wybranego algorytmu.
     *
     * @param data           lista map zawierających dane do posortowania
     * @param key            klucz do użycia przy sortowaniu wartości w mapach
     * @param algorithm      nazwa algorytmu sortującego (np. "bubble", "insertion", "selection", "quick", "merge", "counting")
     * @param direction      kierunek sortowania, "asc" (rosnąco) lub "desc" (malejąco)
     * @param maxIterations  maksymalna liczba iteracji sortowania do wykonania
     * @return mapa zawierająca wyniki sortowania z danymi posortowanymi oraz czasem wykonania
     */
    public Map<String, Object> sortData(List<Map<String, String>> data, String key, String algorithm, String direction, int maxIterations) {
        logger.info("Starting sortData with algorithm: {}, key: {}, direction: {}, maxIterations: {}",
                algorithm, key, direction, maxIterations);
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Dataset is empty or null.");
        }
        if(maxIterations <= 0)
            maxIterations = 0;
        SortingStrategy strategy = getStrategy(algorithm);
        return strategy.sort(data, key, direction, maxIterations);
    }

    /**
     * Metoda pomocnicza do wyboru odpowiedniego algorytmu sortującego na podstawie nazwy.
     *
     * @param algorithm nazwa algorytmu (np. "bubble", "insertion", "selection", "quick", "merge", "counting")
     * @return instancja odpowiedniego algorytmu sortującego
     * @throws IllegalArgumentException jeśli algorytm nie jest rozpoznany
     */
    private SortingStrategy getStrategy(String algorithm) {
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
