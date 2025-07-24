package com.mr486.safetynet.service.buisness;

import com.mr486.safetynet.dto.request.FirePersonDto;
import com.mr486.safetynet.dto.response.FireResponseDto;
import com.mr486.safetynet.exception.EntityNotFoundException;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.service.FireStationService;
import com.mr486.safetynet.service.MedicalRecordService;
import com.mr486.safetynet.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for handling fire-related operations.
 * It retrieves fire station information and associated residents based on the address provided.
 */
@Service
@RequiredArgsConstructor
public class FireService {

  private final PersonService personService;
  private final MedicalRecordService medicalRecordService;
  private final FireStationService fireStationService;

  /**
   * Retrieves fire station information and associated residents for a given address.
   *
   * @param address the address to search for fire station and residents
   * @return a FireResponseDto containing the fire station number and a list of residents
   * @throws EntityNotFoundException if the fire station with the specified address is not found
   */
  public FireResponseDto getFireInfoByAddress(String address) {
    FireStation station = fireStationService.findByAddress(address);
    if (station == null) {
      throw new EntityNotFoundException("Fire station with address " + address + " not found");
    }
    int stationNumber = station.getStation();
    List<Person> persons = personService.findByAddress(address);

    List<FirePersonDto> residents = persons.stream().map(person -> {
      MedicalRecord record = medicalRecordService.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
      int age = medicalRecordService.getAge(record);
      return new FirePersonDto(
              person.getLastName(),
              person.getPhone(),
              age,
              record.getMedications(),
              record.getAllergies()
      );
    }).collect(Collectors.toList());

    return new FireResponseDto(stationNumber, residents);
  }
}