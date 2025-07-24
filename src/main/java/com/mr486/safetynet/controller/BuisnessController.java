package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.response.CoverageResponseDto;
import com.mr486.safetynet.service.buisness.FireStationConverageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BuisnessController {

  private final FireStationConverageService fireStationConverageService;

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
}
