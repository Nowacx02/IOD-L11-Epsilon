package pl.put.poznan.sortingmadness.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.sortingmadness.logic.SortingMadness;

import java.util.Comparator;
import java.util.HashMap;
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
    public Map<String, Object> sort(@RequestBody SortingRequest request) {
        logger.debug("Received request: {}", request);

        List<Map<String, String>> data = request.getData();
        List<String> keysToSort = request.getKeysToSort();
        Map<String, Object> result = new HashMap<>();

        for (SortingRequest.SortingParameter param : request.getSortingParameters()) {
            String algorithm = param.getSortingAlgorithms();
            String direction = param.getDirections();
            int maxIterations = param.getMaxIterations();

            for (String key : keysToSort) {
                result = sortingMadness.sortData(data, key, algorithm, direction, maxIterations);
                data = (List<Map<String, String>>) result.get("sortedData");
            }
        }

        return result;
    }
}

