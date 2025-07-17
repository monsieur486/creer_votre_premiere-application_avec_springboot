package com.mr486.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Response DTO for child alert information.
 * This class encapsulates the list of children and other household members
 * associated with a specific alert.
 */
@Data
@AllArgsConstructor
public class ChildAlertResponseDto {

  /**
   * Default constructor for ChildAlertResponseDto.
   * Initializes an empty response with no children or other members.
   */
  private List<ChildDto> children;

  /**
   * List of other household members associated with the alert.
   */
  private List<OtherHouseholdMemberDto> otherMembers;
}
