package com.mr486.safetynet.service.buisness;

import com.mr486.safetynet.dto.request.ChildAlertDto;
import com.mr486.safetynet.dto.request.HouseholdMemberDto;
import com.mr486.safetynet.dto.response.ChildAlertResponseDto;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.service.MedicalRecordService;
import com.mr486.safetynet.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for handling child alert operations.
 * It retrieves children and their household members based on the address provided.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ChildAlertService {

  private final PersonService personService;
  private final MedicalRecordService medicalRecordService;

  /**
   * Retrieves a list of children and their household members for a given address.
   *
   * @param address the address to search for children
   * @return a ChildAlertResponseDto containing the list of children and their household members
   */
  public ChildAlertResponseDto getChildrenByAddress(String address) {
    List<Person> persons = personService.findByAddress(address);
    List<ChildAlertDto> children = new ArrayList<>();

    log.debug("Retrieving children for address: {}", address);
    for (Person person : persons) {
      MedicalRecord record = medicalRecordService.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
      if (record == null) {
        continue; // Skip if no medical record is found
      }
      if (!medicalRecordService.isAdult(record)) {
        int age = medicalRecordService.getAge(record);
        List<HouseholdMemberDto> householdMembers = persons.stream()
            .filter(p -> !p.getFirstName().equals(person.getFirstName()) || !p.getLastName().equals(person.getLastName()))
            .map(p -> new HouseholdMemberDto(p.getFirstName(), p.getLastName()))
            .collect(Collectors.toList());
        children.add(new ChildAlertDto(person.getFirstName(), person.getLastName(), age, householdMembers));
      }
    }
    return new ChildAlertResponseDto(children);
  }
}
