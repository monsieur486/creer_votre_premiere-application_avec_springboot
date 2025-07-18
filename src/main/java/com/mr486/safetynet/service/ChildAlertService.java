package com.mr486.safetynet.service;

import com.mr486.safetynet.dto.request.ChildAlertChildDto;
import com.mr486.safetynet.dto.request.HouseholdMemberDto;
import com.mr486.safetynet.dto.response.ChildAlertResponseDto;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChildAlertService {

  private final PersonService personService;
  private final MedicalRecordService medicalRecordService;

  public ChildAlertResponseDto getChildrenByAddress(String address) {
    List<Person> persons = personService.findByAddress(address);

    List<ChildAlertChildDto> children = persons.stream()
            .map(person -> {
              MedicalRecord record = medicalRecordService.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
              if (!medicalRecordService.isAdult(person)) {
                int age = medicalRecordService.getAge(person);
                List<HouseholdMemberDto> householdMembers = persons.stream()
                        .filter(p -> !p.equals(person))
                        .map(p -> new HouseholdMemberDto(p.getFirstName(), p.getLastName()))
                        .collect(Collectors.toList());
                return new ChildAlertChildDto(person.getFirstName(), person.getLastName(), age, householdMembers);
              }
              return null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

    return new ChildAlertResponseDto(children);
  }
}
