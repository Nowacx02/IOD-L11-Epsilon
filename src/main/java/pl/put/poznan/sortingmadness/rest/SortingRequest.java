package pl.put.poznan.sortingmadness.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Reprezentuje żądanie sortowania.
 * Zawiera informacje wymagane do sortowania, w tym klucze sortowania, parametry sortowania,
 * dane wejściowe oraz maksymalną liczbę iteracji.
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

    @JsonProperty("removeDuplicates")
    private boolean removeDuplicates;

    public boolean isRemoveDuplicates() {
        return removeDuplicates;
    }

    public void setRemoveDuplicates(boolean removeDuplicates) {
        this.removeDuplicates = removeDuplicates;
    }


    /**
     * Pobiera klucze, które mają być użyte do sortowania.
     *
     * @return lista kluczy do sortowania
     */
    public List<String> getKeysToSort() {
        return keysToSort;
    }

    /**
     * Ustawia klucze, które mają być użyte do sortowania.
     *
     * @param keysToSort lista kluczy do sortowania
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
     * Konwertuje listę map zawierających obiekty generyczne na listę map zawierających obiekty implementujące interfejs Comparable.
     *
     * @param objectList lista map obiektów
     * @return lista map z wartościami implementującymi interfejs Comparable
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
     * Konwertuje pojedynczą mapę zawierającą obiekty generyczne na mapę zawierającą obiekty implementujące interfejs Comparable.
     *
     * @param objectMap mapa obiektów
     * @return mapa z wartościami implementującymi interfejs Comparable
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
     * Pobiera dane wejściowe do sortowania.
     *
     * @return lista map z wartościami implementującymi Comparable, lub null/pusta lista, jeśli dane są nieobecne
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
     * Ustawia dane wejściowe do sortowania.
     *
     * @param data lista map zawierających dane do posortowania
     */
    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    /**
     * Konwertuje listę obiektów na listę obiektów implementujących interfejs Comparable.
     *
     * @param objectList lista obiektów
     * @return lista obiektów implementujących Comparable
     */
    public List<Comparable> mapObjectListToComparableList(List<Object> objectList) {
        return objectList.stream()
                .filter(Comparable.class::isInstance)
                .map(Comparable.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Pobiera dane wejściowe do sortowania jako lista obiektów implementujących Comparable.
     *
     * @return lista obiektów implementujących Comparable, lub null/pusta lista, jeśli dane są nieobecne
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
     * Ustawia dane wejściowe do sortowania jako lista obiektów.
     *
     * @param dataList lista danych do posortowania
     */
    public void setDataList(List<Object> dataList) {
        this.dataList = dataList;
    }

    /**
     * Pobiera maksymalną liczbę iteracji dla sortowania.
     *
     * @return maksymalna liczba iteracji
     */
    public Integer getGlobalMaxIterations() {
        return globalMaxIterations;
    }

    /**
     * Ustawia maksymalną liczbę iteracji dla sortowania.
     *
     * @param globalMaxIterations maksymalna liczba iteracji
     */
    public void setGlobalMaxIterations(Integer globalMaxIterations) {
        this.globalMaxIterations = globalMaxIterations;
    }

    /**
     * Reprezentuje parametry sortowania.
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
