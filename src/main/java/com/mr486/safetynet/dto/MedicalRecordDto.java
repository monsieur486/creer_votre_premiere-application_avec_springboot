package com.mr486.safetynet.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * Represents a medical record containing personal and medical information.
 * This DTO is used to transfer medical record data between layers of the application.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordDto {

  /**
   * The birthdate of the person.
   */
  @NotBlank(message = "birthdate cannot be blank")
  private String birthdate;

  /**
   * A list of medications the person is taking.
   */
  private ArrayList<String> medications;

  /**
   * A list of allergies the person has.
   */
  private ArrayList<String> allergies;
}