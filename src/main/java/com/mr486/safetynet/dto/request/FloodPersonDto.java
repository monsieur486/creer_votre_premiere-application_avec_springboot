package com.mr486.safetynet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

  /**
   * Constructor to create a FloodPersonDto with first name, last name, phone number, and age.
   * Initializes the medications and allergies lists as empty lists.
   *
   * @param firstName the first name of the person
   * @param lastName  the last name of the person
   * @param phone     the phone number of the person
   * @param age       the age of the person
   */
  public FloodPersonDto(String firstName, String lastName, String phone, int age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.age = age;
    this.medications = new ArrayList<>();
    this.allergies = new  ArrayList<>();
  }

}