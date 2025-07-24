package com.mr486.safetynet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO representing a child in the Child Alert feature.
 * Contains the child's first name, last name, age, and a list of household members.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChildAlertChildDto {
  private String firstName;
  private String lastName;
  private int age;
  private List<HouseholdMemberDto> householdMembers;

  /**
   * Constructor to create a ChildAlertChildDto with first name, last name, and age.
   * Initializes the household members list as an empty list.
   *
   * @param firstName the first name of the child
   * @param lastName  the last name of the child
   * @param age       the age of the child
   */
  public ChildAlertChildDto(String firstName, String lastName, int age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.householdMembers = new ArrayList<>();
  }
}
