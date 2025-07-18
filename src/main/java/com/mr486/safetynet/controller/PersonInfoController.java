package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.PersonInfoDto;
import com.mr486.safetynet.service.PersonInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonInfoController {

  private final PersonInfoService personInfoService;

  @GetMapping(value = "/personInfolastName", produces = "application/json")
  public ResponseEntity<List<PersonInfoDto>> getPersonInfo(@RequestParam String lastName) {
    return ResponseEntity.ok(personInfoService.getPersonInfoByLastName(lastName));
  }
}