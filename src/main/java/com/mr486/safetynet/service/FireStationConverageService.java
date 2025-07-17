package com.mr486.safetynet.service;

import com.mr486.safetynet.dto.CoverageResponseDto;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FireStationConverageService {

  private final FireStationService fireStationService;
  private final PersonService personService;
  private final CoverageResponseService coverageResponseService;

  /**
   * Retrieves the coverage information for a specific fire station.
   *
   * @param stationNumber the number of the fire station
   * @return a CoverageResponseDto containing the coverage details
   */
  public CoverageResponseDto getCoverageByStationNumber(int stationNumber) {

    CoverageResponseDto responseDto = new CoverageResponseDto();

    List<FireStation> fireStations = fireStationService.findByStationNumber(stationNumber);

    List<String> addresses = fireStations.stream()
            .map(FireStation::getAddress)
            .toList();


    for (String address : addresses) {
      List<Person> persons = personService.findByAddress(address);
      responseDto = coverageResponseService.getResponse(persons, responseDto);
    }

    return responseDto;
  }

}
