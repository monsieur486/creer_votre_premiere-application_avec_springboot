package com.mr486.safetynet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing a household member with first and last name.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseholdMemberDto {
  private String firstName;
  private String lastName;
}
