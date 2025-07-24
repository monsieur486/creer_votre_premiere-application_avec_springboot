package com.mr486.safetynet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

  /**
   * Constructor to create a PersonInfoDto with first name, last name, address, age, and email.
   * Initializes the medications and allergies lists as empty lists.
   *
   * @param firstName the first name of the person
   * @param lastName  the last name of the person
   * @param address   the address of the person
   * @param age       the age of the person
   * @param email     the email of the person
   */
  public PersonInfoDto(String firstName, String lastName, String address, int age, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.age = age;
    this.email = email;
    this.medications = new ArrayList<>();
    this.allergies = new  ArrayList<>();
  }
}