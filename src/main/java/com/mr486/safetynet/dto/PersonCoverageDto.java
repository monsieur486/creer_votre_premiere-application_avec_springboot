package com.mr486.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonCoverageDto {
  private String firstName;
  private String lastName;
  private String address;
  private String phone;
}