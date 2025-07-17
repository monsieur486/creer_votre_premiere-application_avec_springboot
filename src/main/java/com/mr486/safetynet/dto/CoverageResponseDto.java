package com.mr486.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CoverageResponseDto {
  private List<PersonInfo> persons;
  private int adultCount;
  private int childCount;

  public CoverageResponseDto() {
    this.persons = new ArrayList<>();
    this.adultCount = 0;
    this.childCount = 0;
  }

  @Data
  @AllArgsConstructor
  public static class PersonInfo {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
  }
}
