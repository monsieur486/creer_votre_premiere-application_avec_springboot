package com.mr486.safetynet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

  /**
   * Constructor to create a FirePersonDto with last name, phone number, and age.
   * Initializes the medications and allergies lists as empty lists.
   *
   * @param lastName the last name of the person
   * @param phone    the phone number of the person
   * @param age      the age of the person
   */
  public FirePersonDto(String lastName, String phone, int age) {
    this.lastName = lastName;
    this.phone = phone;
    this.age = age;
    this.medications = new ArrayList<>();
    this.allergies = new ArrayList<>();
  }
}