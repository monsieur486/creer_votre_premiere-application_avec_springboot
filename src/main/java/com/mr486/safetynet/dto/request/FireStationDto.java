package com.mr486.safetynet.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for a fire station.
 * This class is used to transfer data related to a fire station's station number.
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