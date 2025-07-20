package com.mr486.safetynet.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * Represents a medical record containing personal and medical information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecord {

  /**
   * The first name of the person.
   */
  @NotBlank(message = "firstname cannot be blank")
  private String firstName;

  /**
   * The last name of the person.
   */
  @NotBlank(message = "lastname cannot be blank")
  private String lastName;

  /**
   * The birthdate of the person in the format "MM/dd/yyyy".
   */
  @NotBlank(message = "birthdate cannot be blank")
  private String birthdate;

  /**
   * A list of medications the person is taking.
   */
  private ArrayList<String> medications = new ArrayList<>();

  /**
   * A list of allergies the person has.
   */
  private ArrayList<String> allergies = new ArrayList<>();
}