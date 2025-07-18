package com.mr486.safetynet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChildAlertChildDto {
  private String firstName;
  private String lastName;
  private int age;
  private List<HouseholdMemberDto> householdMembers;
}
