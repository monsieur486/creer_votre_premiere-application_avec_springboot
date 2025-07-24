package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.response.ChildAlertResponseDto;
import com.mr486.safetynet.dto.response.CoverageResponseDto;
import com.mr486.safetynet.service.buisness.ChildAlertService;
import com.mr486.safetynet.service.buisness.FireStationConverageService;
import com.mr486.safetynet.service.buisness.PhoneAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class BuisnessController {

  private final FireStationConverageService fireStationConverageService;
  private final ChildAlertService childAlertService;
  private final PhoneAlertService phoneAlertService;

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
}
