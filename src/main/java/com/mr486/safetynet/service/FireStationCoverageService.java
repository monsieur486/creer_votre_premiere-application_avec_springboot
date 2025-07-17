package com.mr486.safetynet.service;

import com.mr486.safetynet.configuration.AppConfiguation;
import com.mr486.safetynet.dto.FireStationCoverageResponseDto;
import com.mr486.safetynet.dto.PersonCoverageDto;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FireStationCoverageService {

  private final FireStationService fireStationService;
  private final PersonService personService;
  private final MedicalRecordService medicalRecordService;

  public FireStationCoverageResponseDto getCoverByStationNumber(int stationNumber) {
    List<FireStation> stations = fireStationService.findByStationNumber(stationNumber);
    List<String> addresses = stations.stream()
            .map(FireStation::getAddress)
            .toList();

    List<PersonCoverageDto> persons = new ArrayList<>();
    int adults = 0, children = 0;

    for (String address : addresses) {
      List<Person> people = personService.findByAddress(address);
      for (Person person : people) {
        MedicalRecord record = medicalRecordService.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
        int age = record.getAge();
        if (age > AppConfiguation.ADULT_AGE) adults++;
        else children++;
        persons.add(new PersonCoverageDto(
                person.getFirstName(),
                person.getLastName(),
                person.getAddress(),
                person.getPhone()
        ));
      }
    }

    return new FireStationCoverageResponseDto(persons, adults, children);
  }

  private int getAge(MedicalRecord record) {

}
