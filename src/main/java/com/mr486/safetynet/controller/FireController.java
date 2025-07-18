package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.response.FireResponseDto;
import com.mr486.safetynet.service.FireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fire")
public class FireController {

  private final FireService fireService;

  @GetMapping(path="", produces = "application/json")
  public ResponseEntity<FireResponseDto> getFireInfo(@RequestParam String address) {
    return ResponseEntity.ok(fireService.getFireInfoByAddress(address));
  }
}