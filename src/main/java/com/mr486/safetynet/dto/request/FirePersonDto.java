package com.mr486.safetynet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO representing a person for the Fire API.
 * Contains personal details such as last name, phone number, age,
 * medications, and allergies.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FirePersonDto {
  private String lastName;
  private String phone;
  private int age;
  private List<String> medications;
  private List<String> allergies;
}