package pl.put.poznan.sortingmadness.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class SortingRequest {

    @JsonProperty("keysToSort")
    private List<String> keysToSort;

    @JsonProperty("sortingParameters")
    private List<SortingParameter> sortingParameters;

    @JsonProperty("data")
    private List<Map<String, String>> data;

    @JsonProperty("globalMaxIterations")
    private Integer globalMaxIterations;

    // Gettery i settery
    public List<String> getKeysToSort() {
        return keysToSort;
    }

    public void setKeysToSort(List<String> keysToSort) {
        this.keysToSort = keysToSort;
    }

    public List<SortingParameter> getSortingParameters() {
        return sortingParameters;
    }

    public void setSortingParameters(List<SortingParameter> sortingParameters) {
        this.sortingParameters = sortingParameters;
    }

    public List<Map<String, String>> getData() {
        return data;
    }

    public void setData(List<Map<String, String>> data) {
        this.data = data;
    }

    public Integer getGlobalMaxIterations() {
        return globalMaxIterations;
    }

    public void setGlobalMaxIterations(Integer globalMaxIterations) {
        this.globalMaxIterations = globalMaxIterations;
    }

    public static class SortingParameter {
        @JsonProperty("sortingAlgorithms")
        private String sortingAlgorithms;

        @JsonProperty("maxIterations")
        private Integer maxIterations;

        @JsonProperty("directions")
        private String directions;

        public String getSortingAlgorithms() {
            return sortingAlgorithms;
        }

        public void setSortingAlgorithms(String sortingAlgorithms) {
            this.sortingAlgorithms = sortingAlgorithms;
        }

        public Integer getMaxIterations() {
            return maxIterations;
        }

        public void setMaxIterations(Integer maxIterations) {
            this.maxIterations = maxIterations;
        }

        public String getDirections() {
            return directions;
        }

        public void setDirections(String directions) {
            this.directions = directions;
        }
    }
}
