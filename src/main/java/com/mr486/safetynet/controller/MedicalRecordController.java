package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.request.MedicalRecordDto;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.service.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * MedicalRecordController handles HTTP requests related to medical records.
 * It provides endpoints for creating, updating, deleting, and retrieving medical records.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

  private final MedicalRecordService medicalRecordService;

  /**
   * Retrieves all medical records.
   *
   * @return an iterable of all MedicalRecord objects.
   */
  @GetMapping(path = "/all", produces = "application/json")
  public Iterable<MedicalRecord> getAllMedicalRecords() {
    return medicalRecordService.findAll();
  }

  /**
   * Adds a new medical record for a person identified by firstName and lastName.
   *
   * @param firstName        the first name of the person.
   * @param lastName         the last name of the person.
   * @param medicalRecordDto the DTO containing medical record data.
   * @return the saved MedicalRecord object.
   */
  @PostMapping(path = "/firstname/{firstName}/lastname/{lastName}", produces = "application/json")
  public MedicalRecord addMedicalRecord(
          @PathVariable String firstName,
          @PathVariable String lastName,
          @RequestBody @Valid MedicalRecordDto medicalRecordDto) {
    return medicalRecordService.save(firstName, lastName, medicalRecordDto);
  }

  /**
   * Updates an existing medical record for a person identified by firstName and lastName.
   *
   * @param firstName        the first name of the person.
   * @param lastName         the last name of the person.
   * @param medicalRecordDto the DTO containing updated medical record data.
   * @return the updated MedicalRecord object.
   */
  @PutMapping(path = "/firstname/{firstName}/lastname/{lastName}", produces = "application/json")
  public MedicalRecord updateMedicalRecord(
          @PathVariable String firstName,
          @PathVariable String lastName,
          @RequestBody @Valid MedicalRecordDto medicalRecordDto) {
    return medicalRecordService.update(firstName, lastName, medicalRecordDto);
  }

  /**
   * Deletes a medical record for a person identified by firstName and lastName.
   *
   * @param firstName the first name of the person.
   * @param lastName  the last name of the person.
   */
  @DeleteMapping(path = "/firstname/{firstName}/lastname/{lastName}", produces = "application/json")
  public void deleteMedicalRecord(
          @PathVariable String firstName,
          @PathVariable String lastName) {
    medicalRecordService.delete(firstName, lastName);
  }

  /**
   * Retrieves a medical record by the first name and last name of the person.
   *
   * @param firstName the first name of the person.
   * @param lastName  the last name of the person.
   * @return the MedicalRecord object if found, or null if not found.
   */
  @GetMapping(path = "/firstname/{firstName}/lastname/{lastName}", produces = "application/json")
  public MedicalRecord getMedicalRecordByFirstNameAndLastName(
          @PathVariable String firstName,
          @PathVariable String lastName) {
    return medicalRecordService.findByFirstNameAndLastName(firstName, lastName);
  }
}
