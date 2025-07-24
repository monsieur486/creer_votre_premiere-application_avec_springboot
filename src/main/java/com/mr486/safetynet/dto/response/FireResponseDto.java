package com.mr486.safetynet.dto.response;

import com.mr486.safetynet.dto.request.FirePersonDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

  /**
   * Constructor to create a FireResponseDto with a station number and an empty list of residents.
   *
   * @param stationNumber the station number associated with the response
   */
  public FireResponseDto(int stationNumber) {
    this.stationNumber = stationNumber;
    this.residents = new ArrayList<>();
  }

}