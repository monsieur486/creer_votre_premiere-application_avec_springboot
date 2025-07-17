package com.mr486.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FireStationCoverageResponseDto {
  private List<PersonCovered> persons;
  private int adultCount;
  private int childCount;

  @Data
  @AllArgsConstructor
  public static class PersonCovered {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
  }
}
