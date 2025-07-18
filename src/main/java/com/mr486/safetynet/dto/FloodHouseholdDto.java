package com.mr486.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloodHouseholdDto {
  private String address;
  private List<FloodPersonDto> residents;
}