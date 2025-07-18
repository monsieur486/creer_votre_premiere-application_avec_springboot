package com.mr486.safetynet.service;

import com.mr486.safetynet.dto.FirePersonDto;
import com.mr486.safetynet.dto.FireResponseDto;
import com.mr486.safetynet.exception.EntityNotFoundException;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.FireStation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FireService {

  private final PersonService personService;
  private final MedicalRecordService medicalRecordService;
  private final FireStationService fireStationService;

  public FireResponseDto getFireInfoByAddress(String address) {
    FireStation station = fireStationService.findByAddress(address);
    if (station == null) {
      throw new EntityNotFoundException("Fire station with address " + address + " not found");
    }
    int stationNumber = station.getStation();
    List<Person> persons = personService.findByAddress(address);

    List<FirePersonDto> residents = persons.stream().map(person -> {
      MedicalRecord record = medicalRecordService.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
      int age = medicalRecordService.getAge(person);
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