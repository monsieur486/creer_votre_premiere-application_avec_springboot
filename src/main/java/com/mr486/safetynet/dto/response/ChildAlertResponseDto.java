package com.mr486.safetynet.dto.response;

import com.mr486.safetynet.dto.request.ChildAlertChildDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO representing the response for the Child Alert API.
 * Contains a list of children with their details.
 */
@Data
@AllArgsConstructor
public class ChildAlertResponseDto {
  private List<ChildAlertChildDto> children;

  /**
   * Constructor to create a ChildAlertResponseDto with an empty list of children.
   * Initializes the children list as an empty list.
   */
  public ChildAlertResponseDto() {
    this.children = new ArrayList<>();
  }

}
