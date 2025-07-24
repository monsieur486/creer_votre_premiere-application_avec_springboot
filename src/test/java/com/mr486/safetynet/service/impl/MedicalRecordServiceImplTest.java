package com.mr486.safetynet.service.impl;

import com.mr486.safetynet.configuration.AppConfiguation;
import com.mr486.safetynet.dto.request.MedicalRecordDto;
import com.mr486.safetynet.exception.EntityAlreadyExistsException;
import com.mr486.safetynet.exception.EntityNotFoundException;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.repository.PersonRepository;
import com.mr486.safetynet.repository.impl.MedicalRecordRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedicalRecordServiceImplTest {

  @Mock
  private MedicalRecordRepositoryImpl medicalRecordRepository;

  @Mock
  private PersonRepository personRepository;

  @InjectMocks
  private MedicalRecordServiceImpl medicalRecordService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setFirstName("John");
    medicalRecord.setLastName("Doe");
    medicalRecord.setBirthdate("01/01/2000");
    ArrayList<String> medications = new ArrayList<>();
    medications.add("Aspirin");
    ArrayList<String> allergies = new ArrayList<>();
    allergies.add("Peanuts");
    medicalRecord.setMedications(medications);
    medicalRecord.setAllergies(allergies);
    when(medicalRecordRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.of(medicalRecord));
    when(medicalRecordRepository.exists("John", "Doe")).thenReturn(true);
    when(personRepository.exists("John", "Doe")).thenReturn(true);
  }

  @Test
  void testFindAll() {
    when(medicalRecordRepository.findAll()).thenReturn(new ArrayList<>());
    List<MedicalRecord> medicalRecords = medicalRecordService.findAll();
    assertNotNull(medicalRecords);
    assertTrue(medicalRecords.isEmpty());
    verify(medicalRecordRepository, times(1)).findAll();
  }

  @Test
  void testFindByFirstNameAndLastName() {
    MedicalRecord medicalRecord = medicalRecordService.findByFirstNameAndLastName("John", "Doe");
    assertNotNull(medicalRecord);
    assertEquals("John", medicalRecord.getFirstName());
    assertEquals("Doe", medicalRecord.getLastName());
    verify(medicalRecordRepository, times(1)).findByFirstNameAndLastName("John", "Doe");
  }

  @Test
  void testSave() {
    String firstName = "John";
    String lastName = "Smith";
    MedicalRecordDto medicalRecordDto = new MedicalRecordDto();
    medicalRecordDto.setBirthdate("01/01/2000");
    ArrayList<String> medications = new ArrayList<>();
    medications.add("Aspirin");
    ArrayList<String> allergies = new ArrayList<>();
    allergies.add("Peanuts");
    medicalRecordDto.setMedications(medications);
    medicalRecordDto.setAllergies(allergies);

    when(medicalRecordRepository.exists(firstName, lastName)).thenReturn(false);
    when(personRepository.exists(firstName, lastName)).thenReturn(true);

    MedicalRecord expected = new MedicalRecord();
    expected.setFirstName(firstName);
    expected.setLastName(lastName);
    expected.setBirthdate(medicalRecordDto.getBirthdate());
    expected.setMedications(medications);
    expected.setAllergies(allergies);

    when(medicalRecordRepository.save(any(MedicalRecord.class))).thenReturn(expected);

    MedicalRecord savedMedicalRecord = medicalRecordService.save(firstName, lastName, medicalRecordDto);
    assertNotNull(savedMedicalRecord);
    assertEquals(firstName, savedMedicalRecord.getFirstName());
    assertEquals(lastName, savedMedicalRecord.getLastName());
    assertEquals(medicalRecordDto.getBirthdate(), savedMedicalRecord.getBirthdate());
  }

  @Test
  void testSave_shouldThrowException_whenMedicalRecordExists() {
    String firstName = "John";
    String lastName = "Doe";
    MedicalRecordDto dto = new MedicalRecordDto();
    when(medicalRecordRepository.exists(firstName, lastName)).thenReturn(true);

    assertThrows(EntityAlreadyExistsException.class, () ->
            medicalRecordService.save(firstName, lastName, dto)
    );
  }

  @Test
  void testSave_shouldThrowException_whenPersonNotFound() {
    String firstName = "Jane";
    String lastName = "Smith";
    MedicalRecordDto dto = new MedicalRecordDto();
    when(medicalRecordRepository.exists(firstName, lastName)).thenReturn(false);
    when(personRepository.exists(firstName, lastName)).thenReturn(false);

    assertThrows(EntityNotFoundException.class, () ->
            medicalRecordService.save(firstName, lastName, dto)
    );
  }

  @Test
  void testFindByFirstNameAndLastName_shouldThrowException_whenNotFound() {
    when(medicalRecordRepository.findByFirstNameAndLastName("Jane", "Smith")).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () ->
            medicalRecordService.findByFirstNameAndLastName("Jane", "Smith")
    );
  }

  @Test
  void testUpdate() {
    String firstName = "John";
    String lastName = "Doe";
    MedicalRecordDto dto = new MedicalRecordDto();
    dto.setBirthdate("02/02/1990");
    dto.setMedications(new ArrayList<>(List.of("Ibuprofen")));
    dto.setAllergies(new ArrayList<>(List.of("None")));

    MedicalRecord existing = new MedicalRecord();
    existing.setFirstName(firstName);
    existing.setLastName(lastName);
    existing.setBirthdate("01/01/2000");
    existing.setMedications(new ArrayList<>(List.of("Aspirin")));
    existing.setAllergies(new ArrayList<>(List.of("Peanuts")));

    when(medicalRecordRepository.exists(firstName, lastName)).thenReturn(true);
    when(medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(Optional.of(existing));
    when(medicalRecordRepository.save(any(MedicalRecord.class))).thenAnswer(invocation -> invocation.getArgument(0));

    MedicalRecord updated = medicalRecordService.update(firstName, lastName, dto);

    assertEquals("02/02/1990", updated.getBirthdate());
    assertEquals(List.of("Ibuprofen"), updated.getMedications());
    assertEquals(List.of("None"), updated.getAllergies());
  }

  @Test
  void testUpdate_shouldThrowException_whenNotFound() {
    String firstName = "Jane";
    String lastName = "Smith";
    MedicalRecordDto dto = new MedicalRecordDto();
    when(medicalRecordRepository.exists(firstName, lastName)).thenReturn(false);

    assertThrows(EntityNotFoundException.class, () ->
            medicalRecordService.update(firstName, lastName, dto)
    );
  }

  @Test
  void testDelete() {
    String firstName = "John";
    String lastName = "Doe";
    when(medicalRecordRepository.exists(firstName, lastName)).thenReturn(true);

    medicalRecordService.delete(firstName, lastName);

    verify(medicalRecordRepository, times(1)).delete(firstName, lastName);
  }

  @Test
  void testGetAge_validDate() {
    MedicalRecord record = new MedicalRecord();
    record.setBirthdate(LocalDate.now().minusYears(30).format(DateTimeFormatter.ofPattern(AppConfiguation.DATE_FORMAT)));
    int age = medicalRecordService.getAge(record);
    assertTrue(age >= 30 && age <= 31);
  }

  @Test
  void testGetAge_invalidDate() {
    MedicalRecord record = new MedicalRecord();
    record.setBirthdate("invalid-date");
    assertThrows(IllegalArgumentException.class, () -> medicalRecordService.getAge(record));
  }

  @Test
  void testIsAdult_true() {
    MedicalRecord record = new MedicalRecord();
    record.setBirthdate(LocalDate.now().minusYears(AppConfiguation.ADULT_AGE + 1)
            .format(DateTimeFormatter.ofPattern(AppConfiguation.DATE_FORMAT)));
    assertTrue(medicalRecordService.isAdult(record));
  }

  @Test
  void testIsAdult_false() {
    MedicalRecord record = new MedicalRecord();
    record.setBirthdate(LocalDate.now().minusYears(AppConfiguation.ADULT_AGE - 1)
            .format(DateTimeFormatter.ofPattern(AppConfiguation.DATE_FORMAT)));
    assertFalse(medicalRecordService.isAdult(record));
  }
}