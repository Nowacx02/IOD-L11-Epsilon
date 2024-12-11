package pl.put.poznan.sortingmadness.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.sortingmadness.logic.SortingMadness;
import pl.put.poznan.sortingmadness.logic.SortingSelector;

import java.util.*;

/**
 * Klasa {@code SortingMadnessController} służy jako kontroler REST API dla operacji sortowania danych.
 * Udostępnia punkt końcowy POST na ścieżce "/sort", który przyjmuje żądanie z danymi do posortowania i zwraca wynik sortowania.
 */
@RestController
@RequestMapping("/sort")
public class SortingMadnessController {
    private static final Logger logger = LoggerFactory.getLogger(SortingMadnessController.class);

    private final SortingMadness sortingMadness;
    private final SortingSelector sortingSelector;

    public SortingMadnessController() {
        this.sortingMadness = new SortingMadness();
        this.sortingSelector = new SortingSelector(); // Initialize SortingSelector
    }

    /**
     * Punkt końcowy POST dla operacji sortowania.
     * Przechwytuje żądanie {@link SortingRequest} zawierające dane do posortowania, klucze sortowania, parametry sortowania
     * oraz maksymalną liczbę iteracji do wykonania.
     * Wykonuje sortowanie danych przy użyciu odpowiedniego algorytmu i kierunku, a wynik sortowania jest zwracany jako mapa.
     *
     * @param request obiekt {@link SortingRequest} zawierający szczegóły żądania sortowania
     * @return mapa zawierająca wyniki sortowania dla każdego algorytmu i klucza sortowania
     *         z polami:
     *         <ul>
     *         <li>{@code algorithm-key}: wynik sortowania dla danego algorytmu i klucza sortowania</li>
     *         <li>{@code result}: posortowane dane</li>
     *         <li>{@code executionTime}: czas wykonania sortowania w milisekundach</li>
     *         </ul>
     * @throws IllegalArgumentException jeśli dane są puste lub niepoprawnie sformatowane
     */
    @PostMapping(produces = "application/json")
    public Map<String, Object> sort(@RequestBody SortingRequest request) {
        logger.debug("Received request: {}", request);
        Map<String, Object> finalResult = new HashMap<>();

        List<Map<String, Comparable>> data = request.getData();
        if (validateData(data)) {

            List<String> keysToSort = request.getKeysToSort();
            Collections.reverse(keysToSort);
            validateKeysToSort(keysToSort, data);

            Integer globalMaxIterations = request.getGlobalMaxIterations();

            List<SortingRequest.SortingParameter> sortingParameters = Optional.ofNullable(request.getSortingParameters())
                    .orElseGet(() -> List.of(new SortingRequest.SortingParameter()));

            for (SortingRequest.SortingParameter param : sortingParameters) {

                String direction = Optional.ofNullable(param.getDirections())
                        .orElseThrow(() -> new IllegalArgumentException("Sorting direction must be specified."));
                boolean dir = "DESC".equals(direction);

                String algorithm = Optional.ofNullable(param.getSortingAlgorithms())
                        .orElseGet(() -> sortingSelector.selectAlgorithmMap(data, keysToSort, dir).toString());
                int maxIterations = Optional.ofNullable(param.getMaxIterations())
                        .orElse(globalMaxIterations != null ? globalMaxIterations : 0);

                List<Map<String, Comparable>> inputDataCopy = new ArrayList<>(data);

                List<Map<String, Object>> tempResult = new ArrayList<>();

                for (int i = 0; i < keysToSort.size(); i++) {
                    String key = keysToSort.get(i);
                    Map<String, Object> result = sortingMadness.sortData(inputDataCopy, key, algorithm, direction, maxIterations);

                    if (i == keysToSort.size() - 1) {
                        tempResult.add(result);
                    } else {
                        inputDataCopy = new ArrayList<>((List<Map<String, Comparable>>) result.get("sortedData"));

                    }
                }

                finalResult.put(algorithm + "-" + String.join(",", keysToSort) + "-" + direction, tempResult);
            }
        } else {
            Integer globalMaxIterations = request.getGlobalMaxIterations();
            List<Comparable> dataList = request.getDataList();
            if (validateDataList(dataList)) {
                List<SortingRequest.SortingParameter> sortingParameters = Optional.ofNullable(request.getSortingParameters())
                        .orElse(List.of(new SortingRequest.SortingParameter()));

                for (SortingRequest.SortingParameter param : sortingParameters) {
                    String direction = Optional.ofNullable(param.getDirections())
                            .orElseThrow(() -> new IllegalArgumentException("Sorting direction must be specified."));
                    boolean dir = "DESC".equals(direction);

                    String algorithm = Optional.ofNullable(param.getSortingAlgorithms())
                            .orElseGet(() -> sortingSelector.selectAlgorithmList(dataList, dir).toString());

                    int maxIterations = Optional.ofNullable(param.getMaxIterations())
                            .orElse(globalMaxIterations != null ? globalMaxIterations : 0);

                    List<Comparable> inputDataCopy = new ArrayList<>(dataList);
                    Map<String, Object> result = sortingMadness.sortDataList(inputDataCopy, algorithm, direction, maxIterations);
                    finalResult.put(algorithm + "-" + direction, result);
                }
            }
        }
        return finalResult;
    }

    /**
     * Waliduje dane w formacie listy map.
     *
     * @param data lista map reprezentujących dane wejściowe
     * @return {@code true} jeśli dane są poprawne, {@code false} w przeciwnym wypadku
     * @throws IllegalArgumentException jeśli dane są puste
     */
    private boolean validateData(List<Map<String, Comparable>> data) {
        if (data == null) {
            return false;
        }
        if (data.isEmpty()) {
            throw new IllegalArgumentException("Dataset is empty.");
        }
        return true;
    }

    /**
     * Waliduje dane w formacie listy elementów porównywalnych.
     *
     * @param data lista elementów porównywalnych reprezentujących dane wejściowe
     * @return {@code true} jeśli dane są poprawne, {@code false} w przeciwnym wypadku
     * @throws IllegalArgumentException jeśli dane są puste
     */
    private boolean validateDataList(List<Comparable> data) {
        if (data == null) {
            return false;
        }
        if (data.isEmpty()) {
            throw new IllegalArgumentException("Dataset (list) is empty.");
        }
        return true;
    }

    /**
     * Waliduje klucze sortowania w odniesieniu do danych wejściowych.
     *
     * @param keysToSort lista kluczy sortowania
     * @param data lista map reprezentujących dane wejściowe
     * @throws IllegalArgumentException jeśli jakikolwiek klucz nie istnieje w danych wejściowych
     */
    private void validateKeysToSort(List<String> keysToSort, List<Map<String, Comparable>> data) {
        for (String key : keysToSort) {
            boolean columnExists = data.stream().anyMatch(map -> map.containsKey(key));
            if (!columnExists) {
                throw new IllegalArgumentException("Invalid column name: " + key);
            }
        }
    }
}
