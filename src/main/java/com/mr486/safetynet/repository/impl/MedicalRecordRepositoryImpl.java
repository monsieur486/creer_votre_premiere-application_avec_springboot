package com.mr486.safetynet.repository.impl;

import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.repository.MedicalRecordRepository;
import com.mr486.safetynet.tools.JsonService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the MedicalRecordRepository interface using JSON data storage.
 * This repository provides methods to manage medical records, including finding,
 * saving, and deleting records.
 */
@Repository
@RequiredArgsConstructor
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {

  private final JsonService jsonService;

  private List<MedicalRecord> medicalRecords;

  /**
   * Initializes the repository by loading medical record data from a JSON file.
   * This method is called after the bean is constructed, ensuring that the
   * medical records list is populated with data before any operations are performed.
   */
  @PostConstruct
  public void init() {
    medicalRecords = jsonService.loadMedicalRecords();
  }

  /**
   * Retrieves all medical records from the repository.
   *
   * @return a list of all medical records.
   */
  @Override
  public List<MedicalRecord> findAll() {
    return medicalRecords;
  }

  /**
   * Finds a medical record by the first name and last name of the person.
   *
   * @param firstName the first name of the person
   * @param lastName  the last name of the person
   * @return an Optional containing the MedicalRecord if found, or empty if not found
   */
  @Override
  public Optional<MedicalRecord> findByFirstNameAndLastName(String firstName, String lastName) {
    return medicalRecords.stream()
            .filter(medicalRecord -> medicalRecord.getFirstName().equalsIgnoreCase(firstName) &&
                    medicalRecord.getLastName().equalsIgnoreCase(lastName))
            .findFirst();
  }

  /**
   * Saves a medical record to the repository.
   *
   * @param medicalRecord the medical record to save
   * @return the saved MedicalRecord entity
   */
  @Override
  public MedicalRecord save(MedicalRecord medicalRecord) {
    medicalRecords.add(medicalRecord);
    return medicalRecord;
  }

  /**
   * Deletes a medical record by the first name and last name of the person.
   *
   * @param firstName the first name of the person
   * @param lastName  the last name of the person
   */
  @Override
  public void delete(String firstName, String lastName) {
    medicalRecords.removeIf(medicalRecord -> medicalRecord.getFirstName().equalsIgnoreCase(firstName) &&
            medicalRecord.getLastName().equalsIgnoreCase(lastName));
  }

  /**
   * Checks if a medical record exists for the specified first name and last name.
   *
   * @param firstName the first name to check
   * @param lastName  the last name to check
   * @return true if a medical record exists for the specified person, false otherwise
   */
  @Override
  public boolean exists(String firstName, String lastName) {
    return medicalRecords.stream()
            .anyMatch(medicalRecord -> medicalRecord.getFirstName().equalsIgnoreCase(firstName) &&
                    medicalRecord.getLastName().equalsIgnoreCase(lastName));
  }
}
