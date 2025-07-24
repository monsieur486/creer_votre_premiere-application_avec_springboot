package com.mr486.safetynet.service.buisness;

import com.mr486.safetynet.dto.request.PersonInfoDto;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.service.MedicalRecordService;
import com.mr486.safetynet.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for retrieving detailed information about persons based on their last name.
 * It combines person details with their medical records to provide a comprehensive view.
 */
@Service
@RequiredArgsConstructor
public class PersonInfoService {

  private final PersonService personService;
  private final MedicalRecordService medicalRecordService;

  /**
   * Retrieves a list of PersonInfoDto objects containing detailed information about persons
   * with the specified last name.
   *
   * @param lastName the last name of the persons to retrieve information for
   * @return a list of PersonInfoDto objects containing person details and medical records
   */
  public List<PersonInfoDto> getPersonInfoByLastName(String lastName) {
    List<Person> persons = personService.findByLastName(lastName);
    return persons.stream().map(person -> {
      MedicalRecord record = medicalRecordService.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
      int age = medicalRecordService.getAge(record);
      return new PersonInfoDto(
              person.getFirstName(),
              person.getLastName(),
              person.getAddress(),
              age,
              person.getEmail(),
              record.getMedications(),
              record.getAllergies()
      );
    }).collect(Collectors.toList());
  }
}