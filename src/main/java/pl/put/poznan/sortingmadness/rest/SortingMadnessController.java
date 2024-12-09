package pl.put.poznan.sortingmadness.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.sortingmadness.logic.SortingMadness;

import java.util.*;

@RestController
@RequestMapping("/sort")
public class SortingMadnessController {

    private final SortingMadness sortingMadness;

    public SortingMadnessController() {
        this.sortingMadness = new SortingMadness();
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> sort(@RequestBody SortingRequest request) {
        // Debug komunikaty w konsoli
        System.out.println("Received request: " + request);

        // Walidacja pustych danych
        List<Map<String, String>> data = request.getData();
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Dataset is empty or null.");
        }

        // Walidacja, czy wybrano przynajmniej jeden algorytm sortujący
        if (request.getSortingParameters() == null || request.getSortingParameters().isEmpty()) {
            throw new IllegalArgumentException("No sorting algorithms specified.");
        }

        // Walidacja, czy wszystkie kolumny istnieją w danych
        List<String> keysToSort = request.getKeysToSort();
        for (String key : keysToSort) {
            boolean columnExists = data.stream().anyMatch(map -> map.containsKey(key));
            if (!columnExists) {
                throw new IllegalArgumentException("Invalid column name: " + key);
            }
        }

        Integer globalMaxIterations = request.getGlobalMaxIterations();

        // Zbiór wyników dla każdego algorytmu
        Map<String, Object> finalResult = new HashMap<>();

        // Iteracja po każdym algorytmie w liście parametrów
        for (SortingRequest.SortingParameter param : request.getSortingParameters()) {
            String algorithm = param.getSortingAlgorithms();
            String direction = param.getDirections();

            System.out.println("Sorting with algorithm: " + algorithm + ", direction: " + direction);

            // Walidacja algorytmu
            if (!List.of("bubble", "insertion", "selection", "quick", "merge", "counting")
                    .contains(algorithm.toLowerCase())) {
                throw new IllegalArgumentException("Unknown sorting algorithm: " + algorithm);
            }

            int maxIterations = (param.getMaxIterations() != null)
                    ? param.getMaxIterations()
                    : ((globalMaxIterations != null) ? globalMaxIterations : 0); // Default: no limit

            // Każdy algorytm musi operować na kopii danych
            List<Map<String, String>> inputDataCopy = new ArrayList<>(data);

            for (String key : keysToSort) {
                Map<String, Object> result = sortingMadness.sortData(inputDataCopy, key, algorithm, direction, maxIterations);

                // Dodanie wyniku dla danego algorytmu i klucza do finalnego rezultatu
                finalResult.put(algorithm + "-" + key, result);

                System.out.println("Result for algorithm " + algorithm + " on key " + key + ": " + result);
            }
        }



        return finalResult;
    }
}
