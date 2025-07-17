package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.CoverageResponseDto;
import com.mr486.safetynet.service.FireStationConverageService;
import com.mr486.safetynet.service.FireStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/firestation")
public class FireStationConverageController {

  private final FireStationConverageService service;

  @GetMapping(path = "", produces = "application/json")
  public ResponseEntity<CoverageResponseDto> getCoverage(@RequestParam int stationNumber) {
    CoverageResponseDto coverageResponse = service.getCoverageByStationNumber(stationNumber);
    if (coverageResponse.getPersons().isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(coverageResponse);
  }
}
