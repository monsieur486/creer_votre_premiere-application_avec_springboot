package com.mr486.safetynet.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ErrorResponseDto is a Data Transfer Object (DTO) that represents an error response.
 * It contains a message, an error code, and a timestamp indicating when the error occurred.
 */
@Data
@NoArgsConstructor
public class ErrorResponseDto {
  String message;
  int errorCode;
  String timestamp;

  /**
   * Constructs an ErrorResponseDto with the specified message and error code.
   *
   * @param message   the error message
   * @param errorCode the error code
   */
  public ErrorResponseDto(String message, int errorCode) {
    this.message = message;
    this.errorCode = errorCode;
    this.timestamp = java.time.LocalDateTime.now().toString();
  }
}
