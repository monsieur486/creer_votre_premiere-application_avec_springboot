package com.mr486.safetynet.repository.impl;

import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.repository.MedicalRecordRepository;
import com.mr486.safetynet.tools.JsonDataReader;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MedicalRecordRepositoryJson implements MedicalRecordRepository {

  private final JsonDataReader jsonDataReader;

  private List<MedicalRecord> medicalRecords;

  /**
   * Initializes the repository by loading medical record data from a JSON file.
   * This method is called after the bean is constructed, ensuring that the
   * medical records list is populated with data before any operations are performed.
   */
  @PostConstruct
  public void init() {
    medicalRecords = new ArrayList<>();
    try {
      medicalRecords = jsonDataReader.loadData().getMedicalrecords();
    } catch (Exception e) {
      throw new RuntimeException("Failed to load medical records from JSON", e);
    }
  }

  @Override
  public List<MedicalRecord> findAll() {
    return medicalRecords;
  }

  @Override
  public Optional<MedicalRecord> findByFirstNameAndLastName(String firstName, String lastName) {
    return medicalRecords.stream()
            .filter(medicalRecord -> medicalRecord.getFirstName().equalsIgnoreCase(firstName) &&
                    medicalRecord.getLastName().equalsIgnoreCase(lastName))
            .findFirst();
  }

  @Override
  public MedicalRecord save(MedicalRecord medicalRecord) {
    medicalRecords.add(medicalRecord);
    return medicalRecord;
  }

  @Override
  public void delete(String firstName, String lastName) {
    medicalRecords.removeIf(medicalRecord -> medicalRecord.getFirstName().equalsIgnoreCase(firstName) &&
            medicalRecord.getLastName().equalsIgnoreCase(lastName));
  }

  @Override
  public boolean exists(String firstName, String lastName) {
    return medicalRecords.stream()
            .anyMatch(medicalRecord -> medicalRecord.getFirstName().equalsIgnoreCase(firstName) &&
                    medicalRecord.getLastName().equalsIgnoreCase(lastName));
  }
}
