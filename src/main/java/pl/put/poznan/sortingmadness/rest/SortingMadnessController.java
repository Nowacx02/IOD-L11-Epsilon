package pl.put.poznan.sortingmadness.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.sortingmadness.logic.SortingMadness;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sort")
public class SortingMadnessController {

    private static final Logger logger = LoggerFactory.getLogger(SortingMadnessController.class);
    private final SortingMadness sortingMadness;

    public SortingMadnessController() {
        this.sortingMadness = new SortingMadness();
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public List<Map<String, String>> sort(@RequestBody SortingRequest request) {
        logger.debug("Received request: {}", request);

        List<Map<String, String>> data = request.getData();
        List<String> keysToSort = request.getKeysToSort();

        for (SortingRequest.SortingParameter param : request.getSortingParameters()) {
            String algorithm = param.getSortingAlgorithms();
            String direction = param.getDirections();
            int maxIterations = param.getMaxIterations();

            for (String key : keysToSort) {
                // Przekształć dane na listę intów lub Stringów do posortowania
                List<Map<String, String>> sortedData = sortingMadness.sortData(data, key, algorithm, direction, maxIterations);

                // Nadpisz posortowaną listę
                data = sortedData;
            }
        }

        return data;
    }
}
