package com.mr486.safetynet.dto.response;

import com.mr486.safetynet.dto.request.FirePersonDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO representing the response for the Fire API.
 * Contains a station number and a list of residents with their details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FireResponseDto {
  private int stationNumber;
  private List<FirePersonDto> residents;
}