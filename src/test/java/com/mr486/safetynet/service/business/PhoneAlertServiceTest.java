package com.mr486.safetynet.service.business;

import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.service.FireStationService;
import com.mr486.safetynet.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PhoneAlertServiceTest {

  @Mock
  private FireStationService fireStationService;
  @Mock
  private PersonService personService;

  @InjectMocks
  private PhoneAlertService phoneAlertService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getPhonesByStationNumber_returnsPhones_whenPersonsExist() {
    int stationNumber = 1;
    String address = "10 Main St";
    FireStation station = new FireStation(address, stationNumber);
    Person person1 = new Person("John", "Doe", address, "City", "12345", "0102030405", "john@ex.com");
    Person person2 = new Person("Jane", "Smith", address, "City", "12345", "0102030406", "jane@ex.com");

    when(fireStationService.findByStationNumber(stationNumber)).thenReturn(List.of(station));
    when(personService.findByAddress(address)).thenReturn(List.of(person1, person2));

    Set<String> phones = phoneAlertService.getPhonesByStation(stationNumber);

    assertNotNull(phones);
    assertEquals(2, phones.size());
    assertTrue(phones.contains("0102030405"));
    assertTrue(phones.contains("0102030406"));
  }

  @Test
  void getPhonesByStationNumber_returnsEmpty_whenNoPersons() {
    int stationNumber = 2;
    String address = "20 Oak St";
    FireStation station = new FireStation(address, stationNumber);

    when(fireStationService.findByStationNumber(stationNumber)).thenReturn(List.of(station));
    when(personService.findByAddress(address)).thenReturn(List.of());

    Set<String> phones = phoneAlertService.getPhonesByStation(stationNumber);

    assertNotNull(phones);
    assertTrue(phones.isEmpty());
  }

  @Test
  void getPhonesByStationNumber_returnsEmpty_whenNoStation() {
    int stationNumber = 3;
    when(fireStationService.findByStationNumber(stationNumber)).thenReturn(List.of());

    Set<String> phones = phoneAlertService.getPhonesByStation(stationNumber);

    assertNotNull(phones);
    assertTrue(phones.isEmpty());
  }
}