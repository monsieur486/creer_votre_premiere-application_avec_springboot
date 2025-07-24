package com.mr486.safetynet.dto.response;

import com.mr486.safetynet.dto.request.FloodHouseholdDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO representing the response for the Flood Stations API.
 * Contains a list of households affected by flooding.
 */
@Data
@AllArgsConstructor
public class FloodStationsResponseDto {
  private List<FloodHouseholdDto> households;

  /**
   * Constructor to create a FloodStationsResponseDto with an empty list of households.
   * Initializes the households list as an empty list.
   */
  public FloodStationsResponseDto() {
    this.households = new ArrayList<>();
  }

}