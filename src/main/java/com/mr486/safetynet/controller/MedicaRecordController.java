package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.MedicalRecordDto;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.service.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medicalRecord")
public class MedicaRecordController {

  private final MedicalRecordService medicalRecordService;

  @Profile("dev")
  @GetMapping(path = "/all", produces = "application/json")
  public ResponseEntity<Iterable<MedicalRecord>> getAllMedicalRecords() {
    Iterable<MedicalRecord> medicalRecords = medicalRecordService.findAll();
    return ResponseEntity.ok(medicalRecords);
  }

  @Profile("dev")
  @GetMapping(path = "/firstname/{firstName}/lastname/{lastName}", produces = "application/json")
  public ResponseEntity<MedicalRecord> getMedicalRecordByName(
          String firstName, String lastName) {
    MedicalRecord medicalRecord = medicalRecordService.findByFirstNameAndLastName(firstName, lastName);
    return ResponseEntity.ok(medicalRecord);
  }

  @PostMapping(path = "", produces = "application/json")
  public ResponseEntity<MedicalRecord> addMedicalRecord(MedicalRecord medicalRecord) {
    MedicalRecord savedMedicalRecord = medicalRecordService.save(medicalRecord);
    return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(savedMedicalRecord);
  }

  @PutMapping(path = "/firstname/{firstName}/lastname/{lastName}", produces = "application/json")
  public ResponseEntity<MedicalRecord> updateMedicalRecord(
          @PathVariable String firstName,
          @PathVariable String lastName,
          @Valid @RequestBody MedicalRecordDto medicalRecordDto) {
    MedicalRecord updatedMedicalRecord = medicalRecordService.update(firstName, lastName, medicalRecordDto);
    return ResponseEntity.ok(updatedMedicalRecord);
  }

  @DeleteMapping(path = "/firstname/{firstName}/lastname/{lastName}", produces = "application/json")
  public ResponseEntity<Void> deleteMedicalRecord(
          @PathVariable String firstName,
          @PathVariable String lastName) {
    medicalRecordService.delete(firstName, lastName);
    return ResponseEntity.noContent().build();
  }
}
