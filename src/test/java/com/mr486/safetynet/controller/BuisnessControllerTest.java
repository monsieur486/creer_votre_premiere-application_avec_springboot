package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.request.ChildAlertDto;
import com.mr486.safetynet.dto.request.PersonInfoDto;
import com.mr486.safetynet.dto.response.ChildAlertResponseDto;
import com.mr486.safetynet.dto.response.CoverageResponseDto;
import com.mr486.safetynet.dto.response.FireResponseDto;
import com.mr486.safetynet.dto.response.FloodStationsResponseDto;
import com.mr486.safetynet.service.business.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
  void getCoverage() {
    int stationNumber = 1;
    CoverageResponseDto coverageResponse = new CoverageResponseDto();
    coverageResponse.setAdultCount(1);
    coverageResponse.setChildCount(0);
    List<CoverageResponseDto.PersonInfo> persons = new ArrayList<>();
    persons.add(new CoverageResponseDto.PersonInfo("John", "Doe", "123 Main St", "555-1234"));
    coverageResponse.setPersons(persons);
    // Mock the service call to return the coverage response
    when(fireStationConverageService.getCoverageByStationNumber(stationNumber)).thenReturn(coverageResponse);
    ResponseEntity<CoverageResponseDto> response = buisnessControllerTest.getCoverage(stationNumber);
    assertEquals(coverageResponse, response.getBody());
    assertEquals(200, response.getStatusCodeValue());
  }

  @Test
  void getCoverageNotFound() {
    int stationNumber = 1;
    // Mock the service call to return an empty coverage response
    when(fireStationConverageService.getCoverageByStationNumber(stationNumber)).thenReturn(new CoverageResponseDto());
    ResponseEntity<CoverageResponseDto> response = buisnessControllerTest.getCoverage(stationNumber);
    assertEquals(404, response.getStatusCodeValue());
  }

  @Test
  void getChildrenByAddressReturnsOkWithChildren() {
    String address = "1 rue de Paris";
    ChildAlertResponseDto responseDto = new ChildAlertResponseDto();
    List<ChildAlertDto> children = List.of(new ChildAlertDto());
    responseDto.setChildren(children);

    when(childAlertService.getChildrenByAddress(address)).thenReturn(responseDto);

    ResponseEntity<ChildAlertResponseDto> response = buisnessControllerTest.getChildrenByAddress(address);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(responseDto, response.getBody());
  }

  @Test
  void getPhonesByStationReturnsOkWithPhones() {
    int firestation = 3;
    Set<String> phones = Set.of("0102030405", "0607080910");

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