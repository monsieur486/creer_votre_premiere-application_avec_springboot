package com.mr486.safetynet.repository.impl;

import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.tools.JsonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MedicalRecordRepositoryImplTest {

  @Mock
  private JsonService jsonService;

  @InjectMocks
  private MedicalRecordRepositoryImpl medicalRecordRepositoryImpl;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    List<MedicalRecord> initialRecords = new ArrayList<>();
    initialRecords.add(new MedicalRecord(
            "John",
            "Doe",
            "01/01/2000",
            new ArrayList<>(List.of("Aspirin")),
            new ArrayList<>(List.of("Peanuts"))
    ));
    when(jsonService.loadMedicalRecords()).thenReturn(initialRecords);
    medicalRecordRepositoryImpl.init();
  }

  @Test
  void findByFirstNameAndLastName_returnsMedicalRecord_whenExists_caseInsensitive() {
    Optional<MedicalRecord> result = medicalRecordRepositoryImpl.findByFirstNameAndLastName("john", "DOE");
    assertTrue(result.isPresent());
    assertEquals("John", result.get().getFirstName());
    assertEquals("Doe", result.get().getLastName());
  }

  @Test
  void findByFirstNameAndLastName_returnsEmpty_whenNotExists() {
    Optional<MedicalRecord> result = medicalRecordRepositoryImpl.findByFirstNameAndLastName("Jane", "Smith");
    assertFalse(result.isPresent());
  }

  @Test
  void save_addsNewMedicalRecord_whenNotExists() {
    MedicalRecord newRecord = new MedicalRecord(
            "Alice",
            "Wonderland",
            "02/02/1990",
            new ArrayList<>(List.of("Aspirin")),
            new ArrayList<>(List.of("Peanuts"))
    );
    medicalRecordRepositoryImpl.save(newRecord);
    assertTrue(medicalRecordRepositoryImpl.exists("Alice", "Wonderland"));
  }

  @Test
  void save_overwritesMedicalRecord_whenSameNameExists() {
    MedicalRecord updatedRecord = new MedicalRecord(
            "John",
            "Doe",
            "01/01/2001",
            new ArrayList<>(List.of("Paracetamol")),
            new ArrayList<>(List.of("Gluten"))
    );
    medicalRecordRepositoryImpl.delete("John", "Doe");
    medicalRecordRepositoryImpl.save(updatedRecord);
    Optional<MedicalRecord> result = medicalRecordRepositoryImpl.findByFirstNameAndLastName("John", "Doe");
    assertTrue(result.isPresent());
    assertEquals("01/01/2001", result.get().getBirthdate());
    assertTrue(result.get().getMedications().contains("Paracetamol"));
    assertTrue(result.get().getAllergies().contains("Gluten"));
  }

  @Test
  void delete_removesMedicalRecord_whenExists_caseInsensitive() {
    medicalRecordRepositoryImpl.delete("JOHN", "doe");
    assertFalse(medicalRecordRepositoryImpl.exists("John", "Doe"));
  }

  @Test
  void delete_doesNothing_whenMedicalRecordDoesNotExist() {
    int initialSize = medicalRecordRepositoryImpl.findAll().size();
    medicalRecordRepositoryImpl.delete("Jane", "Smith");
    assertEquals(initialSize, medicalRecordRepositoryImpl.findAll().size());
  }

  @Test
  void exists_returnsTrue_whenMedicalRecordExists_caseInsensitive() {
    assertTrue(medicalRecordRepositoryImpl.exists("JOHN", "doe"));
  }

  @Test
  void exists_returnsFalse_whenMedicalRecordDoesNotExist() {
    assertFalse(medicalRecordRepositoryImpl.exists("Jane", "Smith"));
  }

  @Test
  void findAll_returnsAllMedicalRecords() {
    List<MedicalRecord> all = medicalRecordRepositoryImpl.findAll();
    assertEquals(1, all.size());
    assertEquals("John", all.get(0).getFirstName());
  }

}