package com.mr486.safetynet.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a fire station entity with an address and a station number.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FireStationDto {

  /**
   * The station number of the fire station.
   * This field cannot be null.
   */
  @NotNull(message = "Station number cannot be null")
  @Min(value = 1, message = "Station number must be greater than or equal to 1")
  private Integer station;
}