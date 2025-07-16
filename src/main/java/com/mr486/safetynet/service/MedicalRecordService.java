package com.mr486.safetynet.service;

import com.mr486.safetynet.dto.MedicalRecordDto;
import com.mr486.safetynet.exception.EntityAlreadyExistsException;
import com.mr486.safetynet.exception.EntityNotFoundException;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.repository.MedicalRecordRepository;
import com.mr486.safetynet.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class MedicalRecordService {

  private final MedicalRecordRepository medicalRecordRepository;
  private final PersonRepository personRepository;

  public List<MedicalRecord> findAll() {
    log.info("Retrieving all medical records");
    return medicalRecordRepository.findAll();
  }

  public MedicalRecord findByFirstNameAndLastName(String firstName, String lastName) {

    if (!exists(firstName, lastName)) {
      log.error("Medical record not found for: {} {}", firstName, lastName);
      throw medicalRecordNotFoundException(firstName, lastName);
    }

    log.info("Finding medical record for: {} {}", firstName, lastName);
    return medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName)
            .orElseThrow(() -> medicalRecordNotFoundException(firstName, lastName));
  }

  public MedicalRecord save(String firstName, String lastName, MedicalRecordDto medicalRecordDto) {

    if (!personRepository.exists(firstName, lastName)) {
      log.error("Attempt to save a medical record for a non-existing person: {} {}", firstName, lastName);
      throw peronNotFoundException(firstName, lastName);
    }

    if (exists(firstName, lastName)) {
      log.error("Attempt to save an existing medical record for: {} {}", firstName, lastName);
      throw medicalRecordAlreadyExists(firstName, lastName);
    }

    Person person = personRepository.findByFirstNameAndLastName(firstName, lastName)
            .orElseThrow(() -> peronNotFoundException(firstName, lastName));

    log.info("Saving medical record for: {} {}", person.getFirstName(), person.getLastName());
    MedicalRecord medicalRecord = medicalRecordDto.toMedicalRecord(person.getFirstName(), person.getLastName());
    return medicalRecordRepository.save(medicalRecord);

  }

  public void delete(String firstName, String lastName) {
    if (!exists(firstName, lastName)) {
      log.error("Attempt to delete a non-existing medical record for: {} {}", firstName, lastName);
      throw medicalRecordNotFoundException(firstName, lastName);
    }
    log.info("Deleting medical record for: {} {}", firstName, lastName);
    medicalRecordRepository.delete(firstName, lastName);
  }

  public MedicalRecord update(String firstName, String lastName, MedicalRecordDto medicalRecordDto) {
    if (!exists(firstName, lastName)) {
      log.error("Attempt to update a non-existing medical record for: {} {}", firstName, lastName);
      throw medicalRecordNotFoundException(firstName, lastName);
    }

    log.info("Updating medical record for: {} {}", firstName, lastName);
    MedicalRecord existingMedicalRecord = medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName)
            .orElseThrow(() -> medicalRecordNotFoundException(firstName, lastName));
    MedicalRecord updatedMedicalRecord = medicalRecordDto.toMedicalRecord(
            existingMedicalRecord.getFirstName(),
            existingMedicalRecord.getLastName());

    log.debug("Deleting existing medical record for: {} {}", firstName, lastName);
    medicalRecordRepository.delete(firstName, lastName);

    log.debug("Saving medical record for: {} {}", firstName, lastName);
    return medicalRecordRepository.save(updatedMedicalRecord);
  }


  private boolean exists(String firstName, String lastName) {
    return medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName).isPresent();
  }

  // private methods to create exceptions

  private EntityNotFoundException medicalRecordNotFoundException(String firstName, String lastName) {
    return new EntityNotFoundException("Medical record for: " + firstName + " " + lastName + " not exists.");
  }

  private EntityAlreadyExistsException medicalRecordAlreadyExists(String firstName, String lastName) {
    return new EntityAlreadyExistsException("Medical record for: " + firstName + " " + lastName + " already exists.");
  }

  private EntityNotFoundException peronNotFoundException(String firstName, String lastName) {
    return new EntityNotFoundException("Person with name " + firstName + " " + lastName + " does not exist.");
  }
}
