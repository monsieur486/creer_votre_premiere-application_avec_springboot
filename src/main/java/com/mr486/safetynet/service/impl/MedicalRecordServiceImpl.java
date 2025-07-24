package com.mr486.safetynet.service.impl;

import com.mr486.safetynet.configuration.AppConfiguation;
import com.mr486.safetynet.dto.request.MedicalRecordDto;
import com.mr486.safetynet.exception.EntityAlreadyExistsException;
import com.mr486.safetynet.exception.EntityNotFoundException;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.repository.MedicalRecordRepository;
import com.mr486.safetynet.repository.PersonRepository;
import com.mr486.safetynet.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Implementation of the MedicalRecordService interface.
 * Provides methods to manage medical records, including finding, saving, updating, and deleting records.
 */
@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

  private final MedicalRecordRepository medicalRecordRepository;
  private final PersonRepository personRepository;

  /**
   * Retrieves all medical records.
   *
   * @return a list of all medical records.
   */
  public List<MedicalRecord> findAll() {
    return  medicalRecordRepository.findAll();
  }

  /**
   * Finds a medical record by first and last name.
   *
   * @param firstName the first name of the person.
   * @param lastName  the last name of the person.
   * @return the medical record for the specified person.
   * @throws EntityNotFoundException if no medical record is found for the given names.
   */
  public MedicalRecord findByFirstNameAndLastName(String firstName, String lastName) {
    return medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName)
            .orElseThrow(() -> medicalRecordNotFoundException(firstName, lastName));

  }

  /**
   * Saves a new medical record or updates an existing one.
   *
   * @param firstName       the first name of the person.
   * @param lastName        the last name of the person.
   * @param medicalRecordDto the DTO containing medical record data.
   * @return the saved or updated medical record.
   * @throws EntityAlreadyExistsException if a medical record already exists for the given names.
   * @throws EntityNotFoundException if no person is found for the given names.
   */
  public MedicalRecord save(String firstName, String lastName, MedicalRecordDto medicalRecordDto) {
    if (exists(firstName, lastName)) {
      throw medicalRecordDuplicateException(firstName, lastName);
    }

    if (!personRepository.exists(firstName, lastName)) {
      throw personNotFoundException(firstName, lastName);
    }

    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setFirstName(firstName);
    medicalRecord.setLastName(lastName);
    medicalRecord.setBirthdate(medicalRecordDto.getBirthdate());
    medicalRecord.setMedications(medicalRecordDto.getMedications());
    medicalRecord.setAllergies(medicalRecordDto.getAllergies());

    return medicalRecordRepository.save(medicalRecord);
  }

  /**
   * Deletes a medical record by first and last name.
   *
   * @param firstName the first name of the person.
   * @param lastName  the last name of the person.
   * @throws EntityNotFoundException if no medical record is found for the given names.
   */
  public void delete(String firstName, String lastName) {
    if (!exists(firstName, lastName)) {
      throw medicalRecordNotFoundException(firstName, lastName);
    }

    medicalRecordRepository.delete(firstName, lastName);

  }

  /**
   * Updates an existing medical record with new data.
   *
   * @param firstName       the first name of the person.
   * @param lastName        the last name of the person.
   * @param medicalRecordDto the DTO containing updated medical record data.
   * @return the updated medical record.
   * @throws EntityNotFoundException if no medical record is found for the given names.
   */
  public MedicalRecord update(String firstName, String lastName, MedicalRecordDto medicalRecordDto) {
    if (!exists(firstName, lastName)) {
      throw medicalRecordNotFoundException(firstName, lastName);
    }

    MedicalRecord medicalRecord = findByFirstNameAndLastName(firstName, lastName);
    medicalRecord.setBirthdate(medicalRecordDto.getBirthdate());
    medicalRecord.setMedications(medicalRecordDto.getMedications());
    medicalRecord.setAllergies(medicalRecordDto.getAllergies());

    return medicalRecordRepository.save(medicalRecord);
  }

/**
   * Checks if a person is an adult based on their medical record.
   *
   * @param birthdate the birthdate of the person in the format defined in AppConfiguation.DATE_FORMAT.
   * @return true if the person is an adult, false otherwise.
   */
  public boolean isAdult(String birthdate) {
    return getAge(birthdate) >= AppConfiguation.ADULT_AGE;
  }

  /**
   * Calculates the age of a person based on their birthdate.
   *
   * @param birthdate the birthdate of the person in the format defined in AppConfiguation.DATE_FORMAT.
   * @return the age of the person in years.
   * @throws IllegalArgumentException if the birthdate format is invalid.
   */
  public int getAge(String birthdate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AppConfiguation.DATE_FORMAT);
    try {
      LocalDate now = LocalDate.now();
      return Period.between(LocalDate.parse(birthdate, formatter), now).getYears();
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Invalid birthdate format. Expected format is: " + AppConfiguation.DATE_FORMAT, e);
    }

  }

  private EntityNotFoundException medicalRecordNotFoundException(String firstName, String lastName) {
    return new EntityNotFoundException("Medical record not found for " + firstName + " " + lastName);
  }

  private EntityAlreadyExistsException medicalRecordDuplicateException(String firstName, String lastName) {
    return new EntityAlreadyExistsException("Medical record already exists for " + firstName + " " + lastName);
  }

  private EntityNotFoundException personNotFoundException(String firstName, String lastName) {
    return new EntityNotFoundException("Person not found for " + firstName + " " + lastName);
  }

  private boolean exists(String firstName, String lastName) {
    return medicalRecordRepository.exists(firstName, lastName);
  }
}
