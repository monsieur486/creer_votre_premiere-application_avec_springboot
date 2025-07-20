package com.mr486.safetynet.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a fire station entity with an address and a station number.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FireStation {

  /**
   * The address of the fire station.
   * This field cannot be blank.
   */
  @NotBlank(message = "Address cannot be blank")
  private String address;

  /**
   * The station number of the fire station.
   * This field cannot be null.
   */
  @NotNull(message = "Station number cannot be null")
  @Min(value = 1, message = "Station number must be greater than or equal to 1")
  private Integer station;
}