package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.ChildAlertResponseDto;
import com.mr486.safetynet.service.ChildAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/childAlert")
public class ChildAlertController {

  private final ChildAlertService childAlertService;


  @GetMapping(path = "", produces = "application/json")
  public ResponseEntity<ChildAlertResponseDto> getChildrenByAddress(@RequestParam String address) {
    return ResponseEntity.ok(childAlertService.getChildrenByAddress(address));
  }

}
