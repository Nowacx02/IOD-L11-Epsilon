package pl.put.poznan.sortingmadness.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.sortingmadness.logic.SortingMadness;

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

    public SortingMadnessController() {
        this.sortingMadness = new SortingMadness();
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

        List<Map<String, String>> data = request.getData();
        validateData(data);

        List<String> keysToSort = request.getKeysToSort();
        validateKeysToSort(keysToSort, data);

        Integer globalMaxIterations = request.getGlobalMaxIterations();
        Map<String, Object> finalResult = new HashMap<>();

        List<SortingRequest.SortingParameter> sortingParameters = Optional.ofNullable(request.getSortingParameters())
                .orElseGet(() -> List.of(new SortingRequest.SortingParameter()));

        for (SortingRequest.SortingParameter param : sortingParameters) {
            String algorithm = Optional.ofNullable(param.getSortingAlgorithms()).orElseGet(() -> determineDefaultAlgorithm(data.size()));
            String direction = Optional.ofNullable(param.getDirections()).orElseThrow(() -> new IllegalArgumentException("Sorting direction must be specified."));

            int maxIterations = Optional.ofNullable(param.getMaxIterations()).orElse(globalMaxIterations != null ? globalMaxIterations : 0);

            List<Map<String, String>> inputDataCopy = new ArrayList<>(data);

            for (String key : keysToSort) {
                Map<String, Object> result = sortingMadness.sortData(inputDataCopy, key, algorithm, direction, maxIterations);
                finalResult.put(algorithm + "-" + key, result);
            }
        }

        return finalResult;
    }

    /**
     * Validates that the provided data is not empty or null.
     *
     * @param data the list of data to validate
     * @throws IllegalArgumentException if the data is empty or null
     */
    private void validateData(List<Map<String, String>> data) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Dataset is empty or null.");
        }
    }

    /**
     * Validates that the provided keys to sort exist in the dataset.
     *
     * @param keysToSort the list of keys to validate
     * @param data the dataset to validate against
     * @throws IllegalArgumentException if any key does not exist in the dataset
     */
    private void validateKeysToSort(List<String> keysToSort, List<Map<String, String>> data) {
        for (String key : keysToSort) {
            boolean columnExists = data.stream().anyMatch(map -> map.containsKey(key));
            if (!columnExists) {
                throw new IllegalArgumentException("Invalid column name: " + key);
            }
        }
    }

    /**
     * Determines the default sorting algorithm based on the size of the dataset.
     *
     * @param dataSize the size of the dataset
     * @return the name of the default sorting algorithm
     */
    private String determineDefaultAlgorithm(int dataSize) {
        if (dataSize <= 10) {
            return "bubble";
        } else if (dataSize <= 100) {
            return "selection";
        } else if (dataSize <= 1000) {
            return "insertion";
        } else if (dataSize <= 5000) {
            return "counting";
        } else if (dataSize <= 10000) {
            return "merge";
        } else {
            return "quick";
        }
    }
}
