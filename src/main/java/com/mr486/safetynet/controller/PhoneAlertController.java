package com.mr486.safetynet.controller;

import com.mr486.safetynet.service.PhoneAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/phoneAlert")
public class PhoneAlertController {

  private final PhoneAlertService phoneAlertService;

  @GetMapping(path = "", produces = "application/json")
  public ResponseEntity<Set<String>> getPhonesByStation(@RequestParam int firestation) {
    return ResponseEntity.ok(phoneAlertService.getPhonesByStation(firestation));
  }
}
