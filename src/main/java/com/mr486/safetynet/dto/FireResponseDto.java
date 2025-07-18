package com.mr486.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FireResponseDto {
  private int stationNumber;
  private List<FirePersonDto> residents;
}