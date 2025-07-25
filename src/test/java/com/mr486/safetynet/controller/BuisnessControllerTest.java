package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.response.ChildAlertResponseDto;
import com.mr486.safetynet.dto.response.CoverageResponseDto;
import com.mr486.safetynet.service.buisness.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BuisnessControllerTest {

  @Mock
  FireStationConverageService fireStationConverageService;

  @Mock
  ChildAlertService childAlertService;

  @Mock
  PhoneAlertService phoneAlertService;

  @Mock
  FireService fireService;

  @Mock
  FloodService floodService;

  @Mock
  PersonInfoService personInfoService;

  @Mock
  CommunityEmailService communityEmailService;

  @InjectMocks
  private BuisnessController buisnessControllerTest;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getCoverageReturnsOkWithPersons() {
    int stationNumber = 2;
    CoverageResponseDto responseDto = new CoverageResponseDto();
    responseDto.setPersons(List.of(new Object())); // Remplacez par un vrai DTO si besoin

    when(fireStationConverageService.getCoverageByStationNumber(stationNumber)).thenReturn(responseDto);

    ResponseEntity<CoverageResponseDto> response = buisnessControllerTest.getCoverage(stationNumber);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(responseDto, response.getBody());
  }

  @Test
  void getChildrenByAddressReturnsOkWithChildren() {
    String address = "1 rue de Paris";
    ChildAlertResponseDto responseDto = new ChildAlertResponseDto();
    responseDto.setChildren(responseDto); // Remplacez par un vrai DTO si besoin

    when(childAlertService.getChildrenByAddress(address)).thenReturn(responseDto);

    ResponseEntity<ChildAlertResponseDto> response = buisnessControllerTest.getChildrenByAddress(address);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(responseDto, response.getBody());
  }

  @Test
  void getPhonesByStationReturnsOkWithPhones() {
    int firestation = 3;
    var phones = Set.of("0102030405", "0607080910");

    when(phoneAlertService.getPhonesByStation(firestation)).thenReturn(phones);

    ResponseEntity<Set<String>> response = buisnessControllerTest.getPhonesByStation(firestation);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(phones, response.getBody());
  }

  @Test
  void getFireInfoReturnsOkWithFireResponse() {
    String address = "2 avenue de Lyon";
    FireResponseDto responseDto = new FireResponseDto();

    when(fireService.getFireInfoByAddress(address)).thenReturn(responseDto);

    ResponseEntity<FireResponseDto> response = buisnessControllerTest.getFireInfo(address);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(responseDto, response.getBody());
  }

  @Test
  void getFloodInfoReturnsOkWithHouseholds() {
    List<Integer> stations = List.of(1, 2);
    FloodStationsResponseDto responseDto = new FloodStationsResponseDto();

    when(floodService.getHouseholdsByStations(stations)).thenReturn(responseDto);

    ResponseEntity<FloodStationsResponseDto> response = buisnessControllerTest.getFloodInfo(stations);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(responseDto, response.getBody());
  }

  @Test
  void getPersonInfoReturnsOkWithPersons() {
    String lastName = "Dupont";
    List<PersonInfoDto> persons = List.of(new PersonInfoDto());

    when(personInfoService.getPersonInfoByLastName(lastName)).thenReturn(persons);

    ResponseEntity<List<PersonInfoDto>> response = buisnessControllerTest.getPersonInfo(lastName);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(persons, response.getBody());
  }

  @Test
  void getCommunityEmailsReturnsOkWithEmails() {
    String city = "Paris";
    Set<String> emails = Set.of("a@b.com", "c@d.com");

    when(communityEmailService.getEmailsByCity(city)).thenReturn(emails);

    ResponseEntity<Set<String>> response = buisnessControllerTest.getCommunityEmails(city);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(emails, response.getBody());
  }

}