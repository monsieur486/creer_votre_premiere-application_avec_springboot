package com.mr486.safetynet.configuration;


import com.mr486.safetynet.configuration.exception.EntityAlreadyExistsException;
import com.mr486.safetynet.configuration.exception.EntityNotFoundException;
import com.mr486.safetynet.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global exception handler for the application.
 * Provides methods to handle specific and generic exceptions,
 * returning standardized HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles MethodArgumentNotValidException.
   * Returns an HTTP 400 (Bad Request) response with validation error details.
   *
   * @param ex the thrown exception
   * @return ResponseEntity containing a map of field errors
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
    );
    String errorMessage = errors.entrySet().stream()
            .map(e -> e.getKey() + ": " + e.getValue())
            .collect(Collectors.joining(", "));
    ErrorResponseDto errorResponse = new ErrorResponseDto(
            errorMessage,
            400
    );
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errorResponse);
  }

  /**
   * Handles EntityAlreadyExistsException.
   * Returns an HTTP 400 (Bad Request) response with an error message.
   *
   * @param ex the thrown exception
   * @return ResponseEntity containing an ApiResponse with error details
   */
  @ExceptionHandler(EntityAlreadyExistsException.class)
  public ResponseEntity<ErrorResponseDto> handleAlreadyExists(EntityAlreadyExistsException ex) {
    ErrorResponseDto errorResponse = new ErrorResponseDto(
            "DUPLICATE " + ex.getMessage(),
            400
    );
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errorResponse);
  }

  /**
   * Handles EntityNotFoundException.
   * Returns an HTTP 404 (Not Found) response with an error message.
   *
   * @param ex the thrown exception
   * @return ResponseEntity containing an ApiResponse with error details
   */
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponseDto> handleNotFound(EntityNotFoundException ex) {
    ErrorResponseDto errorResponse = new ErrorResponseDto(
            "NOT FOUND " + ex.getMessage(),
            404
    );
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(errorResponse);
  }

  /**
   * Handles 404 errors when no handler is found for a request.
   * Returns an HTTP 404 (Not Found) response with an error message.
   *
   * @param ex the thrown exception
   * @return ResponseEntity containing an error message
   */
  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ErrorResponseDto> handleNoHandlerFoundException(NoHandlerFoundException ex) {
    ErrorResponseDto errorResponse = new ErrorResponseDto(
            "PAGE NOT FOUND " + ex.getRequestURL(),
            404
    );
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(errorResponse);
  }


  /**
   * Handles generic exceptions (Exception).
   * Returns an HTTP 500 (Internal Server Error) response with an error message.
   *
   * @param ex the thrown exception
   * @return ResponseEntity containing an ApiResponse with error details
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDto> handleException(Exception ex) {
    ErrorResponseDto errorResponse = new ErrorResponseDto(
            "INTERNAL SERVER ERROR " + ex.getMessage(),
            500
    );
    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorResponse);
  }
}