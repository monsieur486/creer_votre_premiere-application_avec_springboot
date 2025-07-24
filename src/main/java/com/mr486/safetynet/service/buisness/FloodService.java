package com.mr486.safetynet.service.buisness;

import com.mr486.safetynet.dto.request.FloodHouseholdDto;
import com.mr486.safetynet.dto.request.FloodPersonDto;
import com.mr486.safetynet.dto.response.FloodStationsResponseDto;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.service.FireStationService;
import com.mr486.safetynet.service.MedicalRecordService;
import com.mr486.safetynet.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for handling flood-related operations.
 * It retrieves households and their residents based on the fire station numbers provided.
 */
@Service
@RequiredArgsConstructor
public class FloodService {

  private final FireStationService fireStationService;
  private final PersonService personService;
  private final MedicalRecordService medicalRecordService;

  /**
   * Retrieves households and their residents based on the provided fire station numbers.
   *
   * @param stationNumbers a list of fire station numbers
   * @return a FloodStationsResponseDto containing the households and their residents
   * @throws IllegalArgumentException if any station number is invalid (null or negative)
   */
  public FloodStationsResponseDto getHouseholdsByStations(List<Integer> stationNumbers) {
    Set<String> addresses = new HashSet<>();
    for (Integer stationNumber : stationNumbers) {
      if (stationNumber == null || stationNumber < 0) {
        throw new IllegalArgumentException("Invalid station number: " + stationNumber);
      }
      List<FireStation> fireStations = fireStationService.findByStationNumber(stationNumber);
      for (FireStation fireStation : fireStations) {
        addresses.add(fireStation.getAddress());
      }

    }

    List<FloodHouseholdDto> households = new ArrayList<>();
    for (String address : addresses) {
      List<Person> persons = personService.findByAddress(address);
      List<FloodPersonDto> residents = persons.stream().map(person -> {
        MedicalRecord record = medicalRecordService.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
        int age = medicalRecordService.getAge(record);
        return new FloodPersonDto(
                person.getFirstName(),
                person.getLastName(),
                person.getPhone(),
                age,
                record.getMedications(),
                record.getAllergies()
        );
      }).collect(Collectors.toList());
      households.add(new FloodHouseholdDto(address, residents));
    }
    return new FloodStationsResponseDto(households);
  }
}