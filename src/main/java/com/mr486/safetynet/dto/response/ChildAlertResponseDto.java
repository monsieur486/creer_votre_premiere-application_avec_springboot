package com.mr486.safetynet.dto.response;

import com.mr486.safetynet.dto.request.ChildAlertChildDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO representing the response for the Child Alert API.
 * Contains a list of children with their details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChildAlertResponseDto {
  private List<ChildAlertChildDto> children;
}
