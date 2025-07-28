package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.request.PersonInfoDto;
import com.mr486.safetynet.dto.response.ChildAlertResponseDto;
import com.mr486.safetynet.dto.response.CoverageResponseDto;
import com.mr486.safetynet.dto.response.FireResponseDto;
import com.mr486.safetynet.dto.response.FloodStationsResponseDto;
import com.mr486.safetynet.service.business.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * Controller for handling business logic related to fire stations, child alerts, phone alerts, fire information, and flood information.
 * It provides endpoints to retrieve coverage information, children living at an address, phone numbers for residents served by a fire station,
 * fire information for a specific address, and flood information for specified fire stations.
 */
@RestController
@RequiredArgsConstructor
public class BuisnessController {

  private final FireStationConverageService fireStationConverageService;
  private final ChildAlertService childAlertService;
  private final PhoneAlertService phoneAlertService;
  private final FireService fireService;
  private final FloodService floodService;
  private final PersonInfoService personInfoService;
  private final CommunityEmailService communityEmailService;

  /**
   * Retrieves coverage information for a specific fire station.
   *
   * @param stationNumber the number of the fire station for which to retrieve coverage
   * @return a ResponseEntity containing the coverage information or a 404 Not Found status if no coverage is found
   */
  @GetMapping(path = "/firestation", produces = "application/json")
  public ResponseEntity<CoverageResponseDto> getCoverage(@RequestParam int stationNumber) {
    CoverageResponseDto coverageResponse = fireStationConverageService.getCoverageByStationNumber(stationNumber);
    if (coverageResponse.getPersons().isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(coverageResponse);
  }

  /**
   * Retrieves a list of children living at a specified address.
   *
   * @param address the address to search for children
   * @return a ResponseEntity containing ChildAlertResponseDto with children details
   */
  @GetMapping(path = "/childAlert", produces = "application/json")
  public ResponseEntity<ChildAlertResponseDto> getChildrenByAddress(@RequestParam String address) {
    return ResponseEntity.ok(childAlertService.getChildrenByAddress(address));
  }

  /**
   * Retrieves phone numbers for residents served by a specific fire station.
   *
   * @param firestation the number of the fire station to retrieve phone numbers for
   * @return a ResponseEntity containing a set of phone numbers
   */
  @GetMapping(path = "/phoneAlert", produces = "application/json")
  public ResponseEntity<Set<String>> getPhonesByStation(@RequestParam int firestation) {
    return ResponseEntity.ok(phoneAlertService.getPhonesByStation(firestation));
  }

  /**
   * Retrieves fire information for a specified address.
   *
   * @param address the address to search for fire information
   * @return a ResponseEntity containing FireResponseDto with fire details
   */
  @GetMapping(path = "/fire", produces = "application/json")
  public ResponseEntity<FireResponseDto> getFireInfo(@RequestParam String address) {
    return ResponseEntity.ok(fireService.getFireInfoByAddress(address));
  }

  /**
   * Retrieves flood information for specified fire stations.
   *
   * @param stations a list of fire station numbers to retrieve flood information for
   * @return a ResponseEntity containing FloodStationsResponseDto with flood details
   */
  @GetMapping(path = "/flood/stations", produces = "application/json")
  public ResponseEntity<FloodStationsResponseDto> getFloodInfo(@RequestParam List<Integer> stations) {
    return ResponseEntity.ok(floodService.getHouseholdsByStations(stations));
  }

  /**
   * Retrieves person information by last name.
   *
   * @param personInfolastName the last name of the person to search for
   * @return a ResponseEntity containing a list of PersonInfoDto with person details
   */
  @GetMapping(value = "/persons", produces = "application/json")
  public ResponseEntity<List<PersonInfoDto>> getPersonInfo(@RequestParam String personInfolastName) {
    return ResponseEntity.ok(personInfoService.getPersonInfoByLastName(personInfolastName));
  }

  /**
   * Retrieves a set of email addresses for residents in a specified city.
   *
   * @param city the city to search for email addresses
   * @return a ResponseEntity containing a set of email addresses
   */
  @GetMapping(path = "/communityEmail", produces = "application/json")
  public ResponseEntity<Set<String>> getCommunityEmails(@RequestParam String city) {
    return ResponseEntity.ok(communityEmailService.getEmailsByCity(city));
  }
}
