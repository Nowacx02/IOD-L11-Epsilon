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
        System.out.println("Received request: " + request);

        List<Map<String, String>> data = request.getData();
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Dataset is empty or null.");
        }

        List<String> keysToSort = request.getKeysToSort();
        for (String key : keysToSort) {
            boolean columnExists = data.stream().anyMatch(map -> map.containsKey(key));
            if (!columnExists) {
                throw new IllegalArgumentException("Invalid column name: " + key);
            }
        }

        Integer globalMaxIterations = request.getGlobalMaxIterations();

        Map<String, Object> finalResult = new HashMap<>();

        List<SortingRequest.SortingParameter> sortingParameters = request.getSortingParameters();

        // Jeśli użytkownik nie podał parametrów sortowania, ustaw domyślne
        if (sortingParameters == null || sortingParameters.isEmpty()) {
            sortingParameters = List.of(new SortingRequest.SortingParameter());
        }

        for (SortingRequest.SortingParameter param : sortingParameters) {
            String algorithm = param.getSortingAlgorithms();

            // Jeśli algorytm nie został podany, wybierz domyślny
            if (algorithm == null || algorithm.isBlank()) {
                //algorithm = "quick"; // Możemy tutaj zmienić domyślny algorytm
                int size = data.size();
                if (size<=10){
                    algorithm = "bubble";
                }
                else if(size<=100)
                    algorithm = "selection";
                else if (size<=1000)
                    algorithm = "insertion";
                else if (size<=5000)
                    algorithm = "counting";
                else if (size<=10000)
                    algorithm = "merge";
                else
                    algorithm = "quick";
            }

            String direction = param.getDirections();
            if (direction == null || direction.isBlank()) {
                throw new IllegalArgumentException("Sorting direction must be specified.");
            }

            int maxIterations = (param.getMaxIterations() != null)
                    ? param.getMaxIterations()
                    : ((globalMaxIterations != null) ? globalMaxIterations : 0);

            List<Map<String, String>> inputDataCopy = new ArrayList<>(data);

            for (String key : keysToSort) {
                Map<String, Object> result = sortingMadness.sortData(inputDataCopy, key, algorithm, direction, maxIterations);
                finalResult.put(algorithm + "-" + key, result);

                System.out.println("Result for algorithm " + algorithm + " on key " + key + ": " + result);
            }
        }

        return finalResult;
    }
}

