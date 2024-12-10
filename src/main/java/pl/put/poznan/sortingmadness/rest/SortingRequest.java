package pl.put.poznan.sortingmadness.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a sorting request.
 * Contains information required for sorting, including sorting keys, sorting parameters,
 * input data, and the maximum number of iterations.
 */
public class SortingRequest {

    @JsonProperty("keysToSort")
    private List<String> keysToSort;

    @JsonProperty("sortingParameters")
    private List<SortingParameter> sortingParameters;

    @JsonProperty("data")
    private List<Map<String, Object>> data;

    @JsonProperty("dataList")
    private List<Object> dataList;

    @JsonProperty("globalMaxIterations")
    private Integer globalMaxIterations;

    /**
     * Retrieves the keys used for sorting.
     *
     * @return a list of keys used for sorting
     */
    public List<String> getKeysToSort() {
        return keysToSort;
    }

    /**
     * Sets the keys used for sorting.
     *
     * @param keysToSort a list of keys used for sorting
     */
    public void setKeysToSort(List<String> keysToSort) {
        this.keysToSort = keysToSort;
    }

    /**
     * Retrieves the sorting parameters.
     *
     * @return a list of sorting parameters
     */
    public List<SortingParameter> getSortingParameters() {
        return sortingParameters;
    }

    /**
     * Sets the sorting parameters.
     *
     * @param sortingParameters a list of sorting parameters
     */
    public void setSortingParameters(List<SortingParameter> sortingParameters) {
        this.sortingParameters = sortingParameters;
    }

    /**
     * Converts a list of maps containing generic objects to a list of maps containing Comparable objects.
     *
     * @param objectList the input list of maps
     * @return a list of maps with Comparable values
     */
    public List<Map<String, Comparable>> mapObjectListToComparableMapList(List<Map<String, Object>> objectList) {
        if (objectList == null) {
            return Collections.emptyList();
        }

        return objectList.stream()
                .map(this::mapObjectToComparableMap)
                .collect(Collectors.toList());
    }

    /**
     * Converts a single map containing generic objects to a map containing Comparable objects.
     *
     * @param objectMap the input map
     * @return a map with Comparable values
     */
    private Map<String, Comparable> mapObjectToComparableMap(Map<String, Object> objectMap) {
        if (objectMap == null) {
            return Collections.emptyMap();
        }

        return objectMap.entrySet().stream()
                .filter(entry -> entry.getValue() instanceof Comparable)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (Comparable) entry.getValue()));
    }

    /**
     * Retrieves the input data for sorting.
     *
     * @return a list of maps with Comparable values, or null/empty list if data is absent
     */
    public List<Map<String, Comparable>> getData() {
        if (data == null) {
            return null;
        }

        if (data.isEmpty()) {
            return Collections.emptyList();
        }

        return mapObjectListToComparableMapList(data);
    }

    /**
     * Sets the input data for sorting.
     *
     * @param data a list of maps containing data to be sorted
     */
    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    /**
     * Converts a list of objects to a list of Comparable objects.
     *
     * @param objectList the input list of objects
     * @return a list of Comparable objects
     */
    public List<Comparable> mapObjectListToComparableList(List<Object> objectList) {
        return objectList.stream()
                .filter(Comparable.class::isInstance)
                .map(Comparable.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the input data for sorting as a list of Comparable objects.
     *
     * @return a list of Comparable objects, or null/empty list if data is absent
     */
    public List<Comparable> getDataList() {
        if (dataList == null) {
            return null;
        }

        if (dataList.isEmpty()) {
            return Collections.emptyList();
        }

        return mapObjectListToComparableList(dataList);
    }

    /**
     * Sets the input data for sorting as a list of Comparable objects.
     *
     * @param dataList a list of data to be sorted
     */
    public void setDataList(List<Object> dataList) {
        this.dataList = dataList;
    }

    /**
     * Retrieves the maximum number of iterations for sorting.
     *
     * @return the maximum number of iterations
     */
    public Integer getGlobalMaxIterations() {
        return globalMaxIterations;
    }

    /**
     * Sets the maximum number of iterations for sorting.
     *
     * @param globalMaxIterations the maximum number of iterations
     */
    public void setGlobalMaxIterations(Integer globalMaxIterations) {
        this.globalMaxIterations = globalMaxIterations;
    }

    /**
     * Represents the sorting parameters.
     * Contains information about the sorting algorithm, number of iterations, and sorting direction.
     */
    public static class SortingParameter {

        @JsonProperty("sortingAlgorithms")
        private String sortingAlgorithms;

        @JsonProperty("maxIterations")
        private Integer maxIterations;

        @JsonProperty("directions")
        private String directions;

        /**
         * Retrieves the sorting algorithm.
         *
         * @return the sorting algorithm
         */
        public String getSortingAlgorithms() {
            return sortingAlgorithms;
        }

        /**
         * Sets the sorting algorithm.
         *
         * @param sortingAlgorithms the sorting algorithm
         */
        public void setSortingAlgorithms(String sortingAlgorithms) {
            this.sortingAlgorithms = sortingAlgorithms;
        }

        /**
         * Retrieves the number of iterations for sorting.
         *
         * @return the number of iterations
         */
        public Integer getMaxIterations() {
            return maxIterations;
        }

        /**
         * Sets the number of iterations for sorting.
         *
         * @param maxIterations the number of iterations
         */
        public void setMaxIterations(Integer maxIterations) {
            this.maxIterations = maxIterations;
        }

        /**
         * Retrieves the sorting direction.
         *
         * @return the sorting direction ("asc" or "desc")
         */
        public String getDirections() {
            return directions;
        }

        /**
         * Sets the sorting direction.
         *
         * @param directions the sorting direction ("asc" or "desc")
         */
        public void setDirections(String directions) {
            this.directions = directions;
        }
    }
}
