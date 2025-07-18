package com.mr486.safetynet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
