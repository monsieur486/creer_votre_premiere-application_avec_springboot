package com.mr486.safetynet.repository;

import com.mr486.safetynet.model.MedicalRecord;

import java.util.List;
import java.util.Optional;

public interface MedicalRecordRepository {

  List<MedicalRecord> findAll();
  Optional<MedicalRecord> findByFirstNameAndLastName(String firstName, String lastName);
  MedicalRecord save(MedicalRecord medicalRecord);
  void delete(String firstName, String lastName);
  boolean exists(String firstName, String lastName);
}
