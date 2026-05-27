package co.edu.usb.cali.reservasCanchamax.exception;

import co.edu.usb.cali.reservasCanchamax.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import io.swagger.v3.oas.annotations.Hidden;

import java.time.LocalDateTime;

// Esta anotación convierte la clase en un radar que intercepta cualquier error.
// ¡AQUÍ ESTÁ LA CORREA! Le agregamos basePackages para que solo vigile NUESTROS Controladores y deje a Swagger en paz.
@Hidden
@RestControllerAdvice(basePackages = "co.edu.usb.cali.reservasCanchamax.controller")
public class GlobalExceptionHandler {

    // 1. Atrapa los errores de validación que programamos a mano en los Servicios ("throw new Exception...")
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value()) // Código 400
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage()) // Muestra el texto exacto que pusimos ("Ese correo ya existe", etc.)
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 2. Atrapa el error cuando nos envían un JSON vacío (Como cuando probó Bruno y olvidó el Body)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotReadable(HttpServletRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("El cuerpo de la petición (JSON) está vacío o mal formateado.")
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 3. Atrapa el error de PostgreSQL cuando intenta borrar algo que tiene reservas amarradas
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(HttpServletRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value()) // Código 409: Conflicto
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message("No se puede completar la operación porque el registro está siendo usado por otra tabla en la base de datos (Ej: Tiene reservas asociadas).")
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    // 4. ¡EL NUEVO ESCUDO! Atrapa los errores del @Valid (cuando el DTO no cumple las reglas de @NotBlank o @Email)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {

        // Extraemos el primer mensaje de error que falló en el DTO (ej: "El nombre completo es requerido y no puede estar vacío")
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(errorMessage)
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}