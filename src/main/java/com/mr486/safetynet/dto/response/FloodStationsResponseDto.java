package com.mr486.safetynet.dto.response;

import com.mr486.safetynet.dto.request.FloodHouseholdDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO representing the response for the Flood Stations API.
 * Contains a list of households affected by flooding.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloodStationsResponseDto {
  private List<FloodHouseholdDto> households;
}