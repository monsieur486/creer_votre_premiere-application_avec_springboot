package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.FloodStationsResponseDto;
import com.mr486.safetynet.service.FloodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/flood/stations")
public class FloodController {

  private final FloodService floodService;

  @GetMapping(produces = "application/json")
  public ResponseEntity<FloodStationsResponseDto> getFloodInfo(@RequestParam List<Integer> stations) {
    return ResponseEntity.ok(floodService.getHouseholdsByStations(stations));
  }
}