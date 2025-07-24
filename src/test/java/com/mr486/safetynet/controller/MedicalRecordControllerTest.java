package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.request.MedicalRecordDto;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MedicalRecordControllerTest {

  @Mock
  private MedicalRecordService medicalRecordService;

  @InjectMocks
  private MedicalRecordController medicalRecordControllerTest;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    List<MedicalRecord> medicalRecordList = new ArrayList<>();
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setFirstName("John");
    medicalRecord.setLastName("Doe");
    medicalRecord.setBirthdate("01/01/2000");
    ArrayList<String> medications = new ArrayList<>();
    medications.add("Medication1");
    medicalRecord.setMedications(medications);
    ArrayList<String> allergies = new ArrayList<>();
    allergies.add("Allergy1");
    medicalRecord.setAllergies(allergies);
    medicalRecordList.add(medicalRecord);
    when(medicalRecordService.findAll()).thenReturn(medicalRecordList);
  }

  @Test
  void testFindAll() {
    Iterable<MedicalRecord> result = medicalRecordControllerTest.getAllMedicalRecords();
    assertNotNull(result);
    List<MedicalRecord> medicalRecords = (List<MedicalRecord>) result;
    assertFalse(medicalRecords.isEmpty());
    assertEquals(1, medicalRecords.size());
  }

  @Test
  void testGetMedicalRecordByFirstNameAndLastName() {
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setFirstName("Jane");
    medicalRecord.setLastName("Smith");
    medicalRecord.setBirthdate("02/02/1980");
    ArrayList<String> medications = new ArrayList<>();
    medications.add("Medication2");
    medicalRecord.setMedications(medications);
    ArrayList<String> allergies = new ArrayList<>();
    allergies.add("Allergy2");
    medicalRecord.setAllergies(allergies);

    when(medicalRecordService.findByFirstNameAndLastName("Jane", "Smith")).thenReturn(medicalRecord);

    MedicalRecord result = medicalRecordControllerTest.getMedicalRecordByFirstNameAndLastName("Jane", "Smith");
    assertNotNull(result);
    assertEquals("Jane", result.getFirstName());
    assertEquals("Smith", result.getLastName());
  }

  @Test
  void testAddMedicalRecord() {
    String firstName = "Alice";
    String lastName = "Brown";
    MedicalRecordDto dto = new MedicalRecordDto();
    dto.setBirthdate("03/03/1975");
    ArrayList<String> medications = new ArrayList<>();
    medications.add("Medication3");
    dto.setMedications(medications);
    ArrayList<String> allergies = new ArrayList<>();
    allergies.add("Allergy3");
    dto.setAllergies(allergies);
    MedicalRecord savedMedicalRecord = new MedicalRecord();
    savedMedicalRecord.setFirstName(firstName);
    savedMedicalRecord.setLastName(lastName);
    savedMedicalRecord.setBirthdate("03/03/1975");
    savedMedicalRecord.setMedications(medications);
    savedMedicalRecord.setAllergies(allergies);

    when(medicalRecordService.save(firstName, lastName, dto)).thenReturn(savedMedicalRecord);

    MedicalRecord result = medicalRecordControllerTest.addMedicalRecord(firstName, lastName, dto);
    assertNotNull(result);
    assertEquals("Alice", result.getFirstName());
    assertEquals("Brown", result.getLastName());
    assertEquals("03/03/1975", result.getBirthdate());
    assertEquals("Medication3", result.getMedications().get(0));
    assertEquals("Allergy3", result.getAllergies().get(0));
  }

  @Test
  void testUpdateMedicalRecord() {
    String firstName = "Bob";
    String lastName = "White";
    MedicalRecordDto dto = new MedicalRecordDto();
    dto.setBirthdate("04/04/1965");
    ArrayList<String> medications = new ArrayList<>();
    medications.add("Medication4");
    dto.setMedications(medications);
    ArrayList<String> allergies = new ArrayList<>();
    allergies.add("Allergy4");
    dto.setAllergies(allergies);
    MedicalRecord updatedMedicalRecord = new MedicalRecord();
    updatedMedicalRecord.setFirstName(firstName);
    updatedMedicalRecord.setLastName(lastName);
    updatedMedicalRecord.setBirthdate("04/04/1965");
    updatedMedicalRecord.setMedications(medications);
    updatedMedicalRecord.setAllergies(allergies);

    when(medicalRecordService.update(firstName, lastName, dto)).thenReturn(updatedMedicalRecord);

    MedicalRecord result = medicalRecordControllerTest.updateMedicalRecord(firstName, lastName, dto);
    assertNotNull(result);
    assertEquals("Bob", result.getFirstName());
    assertEquals("White", result.getLastName());
    assertEquals("04/04/1965", result.getBirthdate());
    assertEquals("Medication4", result.getMedications().get(0));
    assertEquals("Allergy4", result.getAllergies().get(0));
  }

  @Test
  void testDeleteMedicalRecord() {
    String firstName = "Eve";
    String lastName = "Black";
    medicalRecordControllerTest.deleteMedicalRecord(firstName, lastName);
    verify(medicalRecordService).delete(firstName, lastName);
  }


}