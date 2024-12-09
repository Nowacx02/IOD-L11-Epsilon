package pl.put.poznan.sortingmadness.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * Klasa reprezentująca żądanie sortowania.
 * Zawiera informacje potrzebne do przeprowadzenia sortowania, takie jak klucze do sortowania, parametry sortowania, dane wejściowe oraz maksymalna liczba iteracji.
 */
public class SortingRequest {

    @JsonProperty("keysToSort")
    private List<String> keysToSort;

    @JsonProperty("sortingParameters")
    private List<SortingParameter> sortingParameters;

    @JsonProperty("data")
    private List<Map<String, String>> data;

    @JsonProperty("globalMaxIterations")
    private Integer globalMaxIterations;

    /**
     * Pobiera klucze używane do sortowania.
     *
     * @return lista kluczy używanych do sortowania
     */
    public List<String> getKeysToSort() {
        return keysToSort;
    }

    /**
     * Ustawia klucze używane do sortowania.
     *
     * @param keysToSort lista kluczy używanych do sortowania
     */
    public void setKeysToSort(List<String> keysToSort) {
        this.keysToSort = keysToSort;
    }

    /**
     * Pobiera parametry sortowania.
     *
     * @return lista parametrów sortowania
     */
    public List<SortingParameter> getSortingParameters() {
        return sortingParameters;
    }

    /**
     * Ustawia parametry sortowania.
     *
     * @param sortingParameters lista parametrów sortowania
     */
    public void setSortingParameters(List<SortingParameter> sortingParameters) {
        this.sortingParameters = sortingParameters;
    }

    /**
     * Pobiera dane wejściowe do sortowania.
     *
     * @return lista map zawierających dane do sortowania
     */
    public List<Map<String, String>> getData() {
        return data;
    }

    /**
     * Ustawia dane wejściowe do sortowania.
     *
     * @param data lista map zawierających dane do sortowania
     */
    public void setData(List<Map<String, String>> data) {
        this.data = data;
    }

    /**
     * Pobiera maksymalną liczbę iteracji sortowania.
     *
     * @return maksymalna liczba iteracji
     */
    public Integer getGlobalMaxIterations() {
        return globalMaxIterations;
    }

    /**
     * Ustawia maksymalną liczbę iteracji sortowania.
     *
     * @param globalMaxIterations maksymalna liczba iteracji
     */
    public void setGlobalMaxIterations(Integer globalMaxIterations) {
        this.globalMaxIterations = globalMaxIterations;
    }

    /**
     * Klasa reprezentująca parametry sortowania.
     * Zawiera informacje o algorytmie sortowania, liczbie iteracji i kierunku sortowania.
     */
    public static class SortingParameter {
        @JsonProperty("sortingAlgorithms")
        private String sortingAlgorithms;

        @JsonProperty("maxIterations")
        private Integer maxIterations;

        @JsonProperty("directions")
        private String directions;

        /**
         * Pobiera algorytm sortowania.
         *
         * @return algorytm sortowania
         */
        public String getSortingAlgorithms() {
            return sortingAlgorithms;
        }

        /**
         * Ustawia algorytm sortowania.
         *
         * @param sortingAlgorithms algorytm sortowania
         */
        public void setSortingAlgorithms(String sortingAlgorithms) {
            this.sortingAlgorithms = sortingAlgorithms;
        }

        /**
         * Pobiera liczbę iteracji dla sortowania.
         *
         * @return liczba iteracji
         */
        public Integer getMaxIterations() {
            return maxIterations;
        }

        /**
         * Ustawia liczbę iteracji dla sortowania.
         *
         * @param maxIterations liczba iteracji
         */
        public void setMaxIterations(Integer maxIterations) {
            this.maxIterations = maxIterations;
        }

        /**
         * Pobiera kierunek sortowania.
         *
         * @return kierunek sortowania ("asc" lub "desc")
         */
        public String getDirections() {
            return directions;
        }

        /**
         * Ustawia kierunek sortowania.
         *
         * @param directions kierunek sortowania ("asc" lub "desc")
         */
        public void setDirections(String directions) {
            this.directions = directions;
        }
    }
}
