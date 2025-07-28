package com.mr486.safetynet.service.business;

import com.mr486.safetynet.dto.response.CoverageResponseDto;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.service.FireStationService;
import com.mr486.safetynet.service.MedicalRecordService;
import com.mr486.safetynet.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing fire station coverage.
 * Provides methods to retrieve coverage information for a specific fire station.
 */
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

      MedicalRecord medicalRecord = medicalRecordService.findByFirstNameAndLastName(
              person.getFirstName(), person.getLastName());

      if (medicalRecordService.isAdult(medicalRecord)) {
        response.setAdultCount(response.getAdultCount() + 1);
      } else {
        response.setChildCount(response.getChildCount() + 1);
      }
    }
  }
}
