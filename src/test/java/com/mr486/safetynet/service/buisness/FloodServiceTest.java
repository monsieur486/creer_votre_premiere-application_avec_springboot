package com.mr486.safetynet.service.buisness;

import com.mr486.safetynet.dto.response.FloodStationsResponseDto;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.service.FireStationService;
import com.mr486.safetynet.service.MedicalRecordService;
import com.mr486.safetynet.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FloodServiceTest {

  @Mock
  private PersonService personService;
  @Mock
  private MedicalRecordService medicalRecordService;
  @Mock
  private FireStationService fireStationService;

  @InjectMocks
  private FloodService floodService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getHouseholdsByStations_returnsFloodResponseForMultipleStations() {
    List<Integer> stationNumbers = List.of(1, 2);
    FireStation station1 = new FireStation("10 Main St", 1);
    FireStation station2 = new FireStation("20 Oak St", 2);

    Person person1 = new Person("John", "Doe", "10 Main St", "City", "12345", "0102030405", "john@ex.com");
    Person person2 = new Person("Jane", "Smith", "20 Oak St", "City", "12345", "0102030406", "jane@ex.com");

    MedicalRecord record1 = mock(MedicalRecord.class);
    MedicalRecord record2 = mock(MedicalRecord.class);

    when(fireStationService.findByStationNumber(1)).thenReturn(List.of(station1));
    when(fireStationService.findByStationNumber(2)).thenReturn(List.of(station2));
    when(personService.findByAddress("10 Main St")).thenReturn(List.of(person1));
    when(personService.findByAddress("20 Oak St")).thenReturn(List.of(person2));
    when(medicalRecordService.findByFirstNameAndLastName("John", "Doe")).thenReturn(record1);
    when(medicalRecordService.findByFirstNameAndLastName("Jane", "Smith")).thenReturn(record2);
    when(medicalRecordService.getAge(record1)).thenReturn(40);
    when(medicalRecordService.getAge(record2)).thenReturn(8);
    ArrayList<String> medications1 = new ArrayList<>();
    ArrayList<String> medications2 = new ArrayList<>();
    when(record1.getMedications()).thenReturn(medications1);
    when(record2.getMedications()).thenReturn(medications2);

    FloodStationsResponseDto result = floodService.getHouseholdsByStations(stationNumbers);

    assertNotNull(result);
    assertEquals(2, result.getHouseholds().size());
    assertTrue(result.getHouseholds().stream().anyMatch(h -> h.getAddress().equals("10 Main St")));
    assertTrue(result.getHouseholds().stream().anyMatch(h -> h.getAddress().equals("20 Oak St")));
    assertEquals("John", result.getHouseholds().stream().filter(h -> h.getAddress().equals("10 Main St")).findFirst().get().getResidents().get(0).getFirstName());
    assertEquals(40, result.getHouseholds().stream().filter(h -> h.getAddress().equals("10 Main St")).findFirst().get().getResidents().get(0).getAge());
    assertEquals("Jane", result.getHouseholds().stream().filter(h -> h.getAddress().equals("20 Oak St")).findFirst().get().getResidents().get(0).getFirstName());
    assertEquals(8, result.getHouseholds().stream().filter(h -> h.getAddress().equals("20 Oak St")).findFirst().get().getResidents().get(0).getAge());
  }

  @Test
  void getHouseholdsByStations_returnsEmpty_whenNoStations() {
    List<Integer> stationNumbers = List.of(3, 4);
    when(fireStationService.findByStationNumber(3)).thenReturn(List.of());
    when(fireStationService.findByStationNumber(4)).thenReturn(List.of());

    FloodStationsResponseDto result = floodService.getHouseholdsByStations(stationNumbers);

    assertNotNull(result);
    assertTrue(result.getHouseholds().isEmpty());
  }

  @Test
  void getHouseholdsByStations_returnsEmptyResidentsForAddressWithoutPersons() {
    List<Integer> stationNumbers = List.of(5);
    FireStation station = new FireStation("30 Pine St", 5);

    when(fireStationService.findByStationNumber(5)).thenReturn(List.of(station));
    when(personService.findByAddress("30 Pine St")).thenReturn(List.of());

    FloodStationsResponseDto result = floodService.getHouseholdsByStations(stationNumbers);

    assertNotNull(result);
    assertEquals(1, result.getHouseholds().size());
    assertEquals("30 Pine St", result.getHouseholds().get(0).getAddress());
    assertTrue(result.getHouseholds().get(0).getResidents().isEmpty());
  }

  @Test
  void getHouseholdsByStations_throwsException_whenStationNumberInvalid() {
    List<Integer> stationNumbers = List.of(-1);

    when(fireStationService.findByStationNumber(-1)).thenThrow(new IllegalArgumentException("Invalid station number: -1"));

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      floodService.getHouseholdsByStations(stationNumbers);
    });

    assertTrue(exception.getMessage().contains("Invalid station number"));
  }
}