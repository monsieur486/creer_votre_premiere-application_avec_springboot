package com.mr486.safetynet.service.business;

import com.mr486.safetynet.dto.response.FireResponseDto;
import com.mr486.safetynet.exception.EntityNotFoundException;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.service.FireStationService;
import com.mr486.safetynet.service.PersonService;
import com.mr486.safetynet.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FireServiceTest {

  @Mock
  private PersonService personService;
  @Mock
  private MedicalRecordService medicalRecordService;
  @Mock
  private FireStationService fireStationService;

  @InjectMocks
  private FireService fireService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getFireInfoByAddress_returnsPersonsWithMedicalInfo() {
    String address = "10 Main St";
    Person person = new Person("John", "Doe", address, "City", "12345", "0102030405", "john@ex.com");
    MedicalRecord record = mock(MedicalRecord.class);
    FireStation fireStation = mock(FireStation.class);
    when(fireStation.getStation()).thenReturn(1);
    when(fireStationService.findByAddress(address)).thenReturn(fireStation);
    when(personService.findByAddress(address)).thenReturn(List.of(person));
    when(medicalRecordService.findByFirstNameAndLastName("John", "Doe")).thenReturn(record);
    when(medicalRecordService.getAge(record)).thenReturn(35);
    ArrayList<String> medications = new ArrayList<>();
    medications.add("med1");
    when(record.getMedications()).thenReturn(medications);
    ArrayList<String> allergies = new ArrayList<>();
    allergies.add("allergy1");
    when(record.getMedications()).thenReturn(medications);
    when(record.getAllergies()).thenReturn(allergies);

    FireResponseDto fireResponseDto = fireService.getFireInfoByAddress(address);
    assertNotNull(fireResponseDto);
    assertEquals(1, fireResponseDto.getResidents().size());
    assertEquals("Doe", fireResponseDto.getResidents().get(0).getLastName());
    assertEquals("0102030405", fireResponseDto.getResidents().get(0).getPhone());
    assertEquals(35, fireResponseDto.getResidents().get(0).getAge());
    assertTrue(fireResponseDto.getResidents().get(0).getMedications().contains("med1"));
    assertTrue(fireResponseDto.getResidents().get(0).getAllergies().contains("allergy1"));
  }

  @Test
  void getFireInfoByAddress_returnsEmptyList_whenNoPersons() {
    String address = "Unknown St";
    when(personService.findByAddress(address)).thenReturn(List.of());
    FireStation fireStation = mock(FireStation.class);
    when(fireStation.getStation()).thenReturn(1);
    when(fireStationService.findByAddress(address)).thenReturn(fireStation);
    FireResponseDto fireResponseDto = fireService.getFireInfoByAddress(address);
    assertNotNull(fireResponseDto);
    assertEquals(0, fireResponseDto.getResidents().size());
  }

  @Test
  void getFireInfoByAddress_throwsException_whenFireStationNotFound() {
    String address = "Nonexistent St";
    when(fireStationService.findByAddress(address)).thenReturn(null);
    Exception exception = assertThrows(EntityNotFoundException.class, () -> {
      fireService.getFireInfoByAddress(address);
    });
    assertEquals("Fire station with address Nonexistent St not found", exception.getMessage());
  }
}