package com.mr486.safetynet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponseDto {
  String message;
  int errorCode;
  String timestamp;

  public ErrorResponseDto(String message, int errorCode) {
    this.message = message;
    this.errorCode = errorCode;
    this.timestamp = java.time.LocalDateTime.now().toString();
  }
}
