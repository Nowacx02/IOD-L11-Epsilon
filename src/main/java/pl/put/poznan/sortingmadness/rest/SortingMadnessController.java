package pl.put.poznan.sortingmadness.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.sortingmadness.logic.SortingMadness;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sort")
public class SortingMadnessController {

    private static final Logger logger = LoggerFactory.getLogger(SortingMadnessController.class);
    private final SortingMadness sortingMadness;

    public SortingMadnessController() {
        this.sortingMadness = new SortingMadness();
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> sort(@RequestBody SortingRequest request) {
        logger.debug("Received request: {}", request);

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
        Map<String, Object> result = new HashMap<>();

        // Sortowanie dla każdego algorytmu i kierunku
        for (SortingRequest.SortingParameter param : request.getSortingParameters()) {
            String algorithm = param.getSortingAlgorithms();
            String direction = param.getDirections();

            // Walidacja algorytmu
            if (!"bubble".equalsIgnoreCase(algorithm) && !"insertion".equalsIgnoreCase(algorithm)) {
                throw new IllegalArgumentException("Unknown sorting algorithm: " + algorithm);
            }

            int maxIterations = (param.getMaxIterations() != null)
                    ? param.getMaxIterations()
                    : ((globalMaxIterations != null) ? globalMaxIterations : 0); // Default: no limit

            // Sortowanie według wybranego klucza
            for (String key : keysToSort) {
                result = sortingMadness.sortData(data, key, algorithm, direction, maxIterations);
                data = (List<Map<String, String>>) result.get("sortedData");
            }
        }

        return result;
    }
}
