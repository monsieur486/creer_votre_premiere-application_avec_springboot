package com.mr486.safetynet.dto;

import com.mr486.safetynet.model.MedicalRecord;
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

  /**
   * Converts this DTO to a MedicalRecord entity.
   *
   * @param firstName The first name of the person.
   * @param lastName  The last name of the person.
   * @return A MedicalRecord object containing the data from this DTO.
   */
  public MedicalRecord toMedicalRecord(String firstName, String lastName) {
    return new MedicalRecord(firstName, lastName, birthdate, medications, allergies);
  }
}