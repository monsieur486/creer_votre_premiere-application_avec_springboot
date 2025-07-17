package com.mr486.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object representing a child.
 * This DTO contains the first name, last name, and age of a child.
 * It is used for transferring child data between layers of the application.
 */
@Data
@AllArgsConstructor
public class ChildDto {
  /**
   * Default constructor for ChildDto.
   * Initializes an empty ChildDto with no data.
   */
  private String firstName;
  // The first name of the child.
  private String lastName;
  // The last name of the child.
  private Integer age;
}
