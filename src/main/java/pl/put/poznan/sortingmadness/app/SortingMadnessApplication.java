package pl.put.poznan.sortingmadness.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Główna klasa aplikacji dla Sorting Madness.
 * Umożliwia uruchomienie aplikacji w trybie Spring Boot.
 * <p>
 * Klasa oznaczona adnotacją {@code @SpringBootApplication}, co pozwala na:
 * <ul>
 *     <li>Automatyczne skanowanie komponentów w pakietach zdefiniowanych przez {@code scanBasePackages}.</li>
 *     <li>Automatyczną konfigurację aplikacji na podstawie zależności.</li>
 *     <li>Zachowanie prostoty konfiguracji dzięki kombinacji adnotacji {@code @Configuration}, {@code @EnableAutoConfiguration} oraz {@code @ComponentScan}.</li>
 * </ul>
 */
@SpringBootApplication(scanBasePackages = {"pl.put.poznan.sortingmadness.rest"})
public class SortingMadnessApplication {

    /**
     * Główna metoda uruchamiająca aplikację Sorting Madness.
     * <p>
     * Metoda używa {@link SpringApplication#run(Class, String...)} do uruchomienia aplikacji
     * oraz załadowania kontekstu Spring Framework.
     *
     * @param args argumenty wiersza poleceń, które mogą być przekazane do aplikacji
     */
    public static void main(String[] args) {
        SpringApplication.run(SortingMadnessApplication.class, args);
    }
}
