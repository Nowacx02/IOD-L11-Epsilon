package pl.put.poznan.sortingmadness.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Główna klasa aplikacji dla Sorting Madness.
 * Umożliwia uruchomienie aplikacji w trybie Spring Boot.
 * Klasa jest oznaczona adnotacją @SpringBootApplication, która łączy adnotacje @Configuration, @EnableAutoConfiguration i @ComponentScan.
 * Jest to punkt wejścia aplikacji Spring Boot.
 */
@SpringBootApplication(scanBasePackages = {"pl.put.poznan.sortingmadness.rest"})
public class SortingMadnessApplication {

    /**
     * Główna metoda uruchamiająca aplikację.
     * Używa SpringApplication.run do uruchomienia aplikacji i załadowania kontekstu Spring.
     *
     * @param args argumenty wiersza poleceń
     */
    public static void main(String[] args) {
        SpringApplication.run(SortingMadnessApplication.class, args);
    }
}
