package com.mr486.safetynet.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO for coverage response containing a list of persons and their counts.
 * This class is used to encapsulate the response data for coverage-related requests.
 */
@Data
@AllArgsConstructor
public class CoverageResponseDto {
  private List<PersonInfo> persons;
  private int adultCount;
  private int childCount;

  /**
   * Default constructor initializing the persons list and counts.
   * This constructor is used to create an empty response object.
   */
  public CoverageResponseDto() {
    this.persons = new ArrayList<>();
    this.adultCount = 0;
    this.childCount = 0;
  }

  /**
   * Adds a person to the coverage response.
   * <p>
   * personInfo The person information to be added.
   */
  @Data
  @AllArgsConstructor
  public static class PersonInfo {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
  }
}
