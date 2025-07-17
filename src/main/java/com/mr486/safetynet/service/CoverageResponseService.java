package com.mr486.safetynet.service;

import com.mr486.safetynet.configuration.AppConfiguation;
import com.mr486.safetynet.dto.CoverageResponseDto;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoverageResponseService {

  private final MedicalRecordService medicalRecordService;

  public CoverageResponseDto getResponse(List<Person> persons, CoverageResponseDto response) {

    for (Person person : persons) {
      CoverageResponseDto.PersonInfo personInfo = new CoverageResponseDto.PersonInfo(
              person.getFirstName(),
              person.getLastName(),
              person.getAddress(),
              person.getPhone()
      );
      response.getPersons().add(personInfo);

      if (isAdult(person)) {
        response.setAdultCount(response.getAdultCount() + 1);
      } else {
        response.setChildCount(response.getChildCount() + 1);
      }
    }

    return response;

  }

  private boolean isAdult(Person person) {
    try {
      int age = getAge(person);
      return age >= AppConfiguation.ADULT_AGE;
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Error calculating age for person: " + person.getFirstName() + " " + person.getLastName(), e);
    }
  }

  private int getAge(Person person) {
    MedicalRecord medicalRecord = medicalRecordService.findByFirstNameAndLastName(
            person.getFirstName(),
            person.getLastName());

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AppConfiguation.DATE_FORMAT);
    try {
      LocalDate now = LocalDate.now();
      return Period.between(LocalDate.parse(medicalRecord.getBirthdate(), formatter), now).getYears();
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Invalid birthdate format. Expected format is: " + AppConfiguation.DATE_FORMAT, e);
    }

  }

}
