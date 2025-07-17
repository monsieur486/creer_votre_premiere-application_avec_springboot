package com.mr486.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CoverageResponseDto {
  private List<PersonCoveredDto> persons;
  private int adultCount;
  private int childCount;

  @Data
  @AllArgsConstructor
  public static class PersonCoveredDto {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
  }

  public void addPerson(PersonCoveredDto person) {
    if (persons == null) {
      persons = new ArrayList<>();
    }
    persons.add(person);
  }
}
