package com.mr486.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object representing an other household member.
 * This DTO contains the first name and last name of a household member.
 * It is used for transferring household member data between layers of the application.
 */
@Data
@AllArgsConstructor
public class OtherHouseholdMemberDto {
  /**
   * Default constructor for OtherHouseholdMemberDto.
   */
  private String firstName;
  // The first name of the household member.
  private String lastName;
}
