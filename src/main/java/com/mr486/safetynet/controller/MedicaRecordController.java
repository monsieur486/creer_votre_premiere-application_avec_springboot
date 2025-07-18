package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.request.MedicalRecordDto;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.service.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing medical records.
 * Provides endpoints to retrieve, add, update, and delete medical records.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/medicalRecord")
public class MedicaRecordController {

  private final MedicalRecordService medicalRecordService;

  /**
   * Retrieves all medical records.
   *
   * @return a ResponseEntity containing an iterable of MedicalRecord objects.
   */
  @GetMapping(path = "/all", produces = "application/json")
  public ResponseEntity<Iterable<MedicalRecord>> getAllMedicalRecords() {
    Iterable<MedicalRecord> medicalRecords = medicalRecordService.findAll();
    return ResponseEntity.ok(medicalRecords);
  }

  /**
   * Retrieves a medical record by first name and last name.
   *
   * @param firstName the first name of the person
   * @param lastName  the last name of the person
   * @return a ResponseEntity containing the MedicalRecord object if found, or an error response if not found
   */
  @GetMapping(path = "/firstname/{firstName}/lastname/{lastName}", produces = "application/json")
  public ResponseEntity<MedicalRecord> getMedicalRecordByName(
          @PathVariable String firstName,
          @PathVariable String lastName) {
    MedicalRecord medicalRecord = medicalRecordService.findByFirstNameAndLastName(firstName, lastName);
    return ResponseEntity.ok(medicalRecord);
  }

  /**
   * Adds a new medical record for a person identified by first name and last name.
   *
   * @param firstName       the first name of the person
   * @param lastName        the last name of the person
   * @param medicalRecordDto the DTO containing medical record data
   * @return a ResponseEntity containing the saved MedicalRecord object
   */
  @PostMapping(path = "/firstname/{firstName}/lastname/{lastName}", produces = "application/json")
  public ResponseEntity<MedicalRecord> addMedicalRecord(@PathVariable String firstName,
                                                        @PathVariable String lastName,
                                                        @Valid @RequestBody MedicalRecordDto medicalRecordDto) {
    MedicalRecord savedMedicalRecord = medicalRecordService.save(firstName, lastName, medicalRecordDto);
    return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(savedMedicalRecord);
  }

  /**
   * Updates an existing medical record for a person identified by first name and last name.
   *
   * @param firstName       the first name of the person
   * @param lastName        the last name of the person
   * @param medicalRecordDto the DTO containing updated medical record data
   * @return a ResponseEntity containing the updated MedicalRecord object
   */
  @PutMapping(path = "/firstname/{firstName}/lastname/{lastName}", produces = "application/json")
  public ResponseEntity<MedicalRecord> updateMedicalRecord(
          @PathVariable String firstName,
          @PathVariable String lastName,
          @Valid @RequestBody MedicalRecordDto medicalRecordDto) {
    MedicalRecord updatedMedicalRecord = medicalRecordService.update(firstName, lastName, medicalRecordDto);
    return ResponseEntity.ok(updatedMedicalRecord);
  }

  /**
   * Deletes a medical record for a person identified by first name and last name.
   *
   * @param firstName the first name of the person
   * @param lastName  the last name of the person
   * @return a ResponseEntity with no content if deletion is successful
   */
  @DeleteMapping(path = "/firstname/{firstName}/lastname/{lastName}", produces = "application/json")
  public ResponseEntity<Void> deleteMedicalRecord(
          @PathVariable String firstName,
          @PathVariable String lastName) {
    medicalRecordService.delete(firstName, lastName);
    return ResponseEntity.noContent().build();
  }
}
