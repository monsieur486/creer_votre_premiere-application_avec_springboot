package com.mr486.safetynet.service;

import com.mr486.safetynet.dto.FloodHouseholdDto;
import com.mr486.safetynet.dto.FloodPersonDto;
import com.mr486.safetynet.dto.FloodStationsResponseDto;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.FireStation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FloodService {

  private final FireStationService fireStationService;
  private final PersonService personService;
  private final MedicalRecordService medicalRecordService;

  public FloodStationsResponseDto getHouseholdsByStations(List<Integer> stationNumbers) {
    Set<String> addresses = new HashSet<>();
    for(Integer stationNumber : stationNumbers) {
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
        int age = medicalRecordService.getAge(person);
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