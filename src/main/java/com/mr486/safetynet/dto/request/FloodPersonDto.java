package com.mr486.safetynet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO representing a person in the flood alert system.
 * Contains personal information such as name, phone number, age,
 * medications, and allergies.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloodPersonDto {
  private String firstName;
  private String lastName;
  private String phone;
  private int age;
  private List<String> medications;
  private List<String> allergies;
}