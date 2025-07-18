package com.mr486.safetynet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO representing a household affected by a flood.
 * Contains the address and a list of residents.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloodHouseholdDto {
  private String address;
  private List<FloodPersonDto> residents;
}