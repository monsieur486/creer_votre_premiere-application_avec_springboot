package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.request.MedicalRecordDto;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.service.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

  private final MedicalRecordService medicalRecordService;

  @GetMapping(path = "/all", produces = "application/json")
  public Iterable<MedicalRecord> getAllMedicalRecords() {
    return medicalRecordService.findAll();
  }

  @PostMapping(path = "/firstname/{firstName}/lastname/{lastName}", produces = "application/json")
  public MedicalRecord addMedicalRecord(
          @PathVariable String firstName,
          @PathVariable String lastName,
          @RequestBody @Valid MedicalRecordDto medicalRecordDto) {
    return medicalRecordService.save(firstName, lastName, medicalRecordDto);
  }

  @PutMapping(path = "/firstname/{firstName}/lastname/{lastName}", produces = "application/json")
  public MedicalRecord updateMedicalRecord(
          @PathVariable String firstName,
          @PathVariable String lastName,
          @RequestBody @Valid MedicalRecordDto medicalRecordDto) {
    return medicalRecordService.update(firstName, lastName, medicalRecordDto);
  }

  @DeleteMapping(path = "/firstname/{firstName}/lastname/{lastName}", produces = "application/json")
  public void deleteMedicalRecord(
          @PathVariable String firstName,
          @PathVariable String lastName) {
    medicalRecordService.delete(firstName, lastName);
  }

  @GetMapping(path = "/firstname/{firstName}/lastname/{lastName}", produces = "application/json")
  public MedicalRecord getMedicalRecordByFirstNameAndLastName(
          @PathVariable String firstName,
          @PathVariable String lastName) {
    return medicalRecordService.findByFirstNameAndLastName(firstName, lastName);
  }
}
