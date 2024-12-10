package pl.put.poznan.sortingmadness.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Klasa {@code GlobalExceptionHandler} zapewnia globalną obsługę wyjątków w aplikacji Spring.
 * <p>
 * Przeznaczona do obsługi wyjątków w aplikacji, takich jak {@code IllegalArgumentException},
 * w celu ustandaryzowanego zwracania komunikatów błędów w odpowiedzi HTTP.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Obsługuje wyjątek {@code IllegalArgumentException}, który może wystąpić w czasie działania aplikacji.
     * <p>
     * Tworzy standardowy format odpowiedzi błędu zawierający informacje o typie błędu oraz jego szczegółach.
     *
     * @param ex wyjątek {@code IllegalArgumentException}, który został przechwycony.
     * @return mapa zawierająca szczegóły błędu w formacie JSON, z polami:
     *         <ul>
     *             <li>{@code error}: opis ogólny błędu, np. "Invalid Request".</li>
     *             <li>{@code message}: szczegółowa wiadomość wyciągnięta z wyjątku.</li>
     *         </ul>
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Invalid Request");
        errorResponse.put("message", ex.getMessage());
        return errorResponse;
    }
}
