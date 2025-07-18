package com.mr486.safetynet.controller;

import com.mr486.safetynet.service.CommunityEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/communityEmail")
public class CommunityEmailController {

  private final CommunityEmailService communityEmailService;

  @GetMapping(produces = "application/json")
  public ResponseEntity<Set<String>> getCommunityEmails(@RequestParam String city) {
    return ResponseEntity.ok(communityEmailService.getEmailsByCity(city));
  }
}