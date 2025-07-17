package com.mr486.safetynet.service;

import com.mr486.safetynet.dto.CoverageResponseDto;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FireStationConverageService {

  private final FireStationService fireStationService;
  private final PersonService personService;
  private final MedicalRecordService medicalRecordService;

  /**
   * Retrieves the coverage information for a specific fire station.
   *
   * @param stationNumber the number of the fire station
   * @return a CoverageResponseDto containing the coverage details
   */
  public CoverageResponseDto getCoverageByStationNumber(int stationNumber) {

    CoverageResponseDto responseDto = new CoverageResponseDto();

    log.debug("Retrieving coverage for fire station number: {}", stationNumber);
    List<FireStation> fireStations = fireStationService.findByStationNumber(stationNumber);

    List<String> addresses = fireStations.stream()
            .map(FireStation::getAddress)
            .toList();

    log.debug("Retrieving addresses for fire station number {}: {}", stationNumber, addresses);
    for (String address : addresses) {
      List<Person> persons = personService.findByAddress(address);
      log.debug("Retrieving persons for address {}: {}", address, persons);
      getResponse(persons, responseDto);
    }

    log.info("Returning coverage for fire station number: {}", stationNumber);
    return responseDto;
  }

  private void getResponse(List<Person> persons, CoverageResponseDto response) {

    for (Person person : persons) {
      CoverageResponseDto.PersonInfo personInfo = new CoverageResponseDto.PersonInfo(
              person.getFirstName(),
              person.getLastName(),
              person.getAddress(),
              person.getPhone()
      );
      response.getPersons().add(personInfo);

      if (medicalRecordService.isAdult(person)) {
        response.setAdultCount(response.getAdultCount() + 1);
      } else {
        response.setChildCount(response.getChildCount() + 1);
      }
    }

  }

}
