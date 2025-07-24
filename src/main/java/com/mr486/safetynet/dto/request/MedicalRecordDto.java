package com.mr486.safetynet.dto.request;

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

  @NotBlank(message = "birthdate cannot be blank")
  private String birthdate;
  private ArrayList<String> medications;
  private ArrayList<String> allergies;

  /**
   * Constructs a MedicalRecordDto with the specified birthdate.
   * Initializes medications and allergies as empty lists.
   *
   * @param birthdate the birthdate of the person in the format "MM/dd/yyyy"
   */
  public MedicalRecordDto(String birthdate) {
    this.birthdate = birthdate;
    this.medications = new ArrayList<>();
    this.allergies = new ArrayList<>();
  }


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