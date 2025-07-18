package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.request.PersonInfoDto;
import com.mr486.safetynet.service.PersonInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonInfoController {

  private final PersonInfoService personInfoService;

  @GetMapping(value = "/persons", produces = "application/json")
  public ResponseEntity<List<PersonInfoDto>> getPersonInfo(@RequestParam String personInfolastName) {
    return ResponseEntity.ok(personInfoService.getPersonInfoByLastName(personInfolastName));
  }
}