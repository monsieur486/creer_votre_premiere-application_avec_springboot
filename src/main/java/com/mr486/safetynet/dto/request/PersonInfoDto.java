package com.mr486.safetynet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO representing detailed information about a person.
 * Contains personal details such as name, address, age, email,
 * medications, and allergies.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonInfoDto {
  private String firstName;
  private String lastName;
  private String address;
  private int age;
  private String email;
  private List<String> medications;
  private List<String> allergies;
}