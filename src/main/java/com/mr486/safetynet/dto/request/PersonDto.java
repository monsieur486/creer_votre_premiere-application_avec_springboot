package com.mr486.safetynet.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for a person's contact information.
 * This class is used to transfer data related to a person's address, city, zip code,
 * phone number, and email address.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

  /**
   * The person's residential address.
   */
  @NotBlank(message = "address cannot be blank")
  private String address;

  /**
   * The city where the person resides.
   */
  @NotBlank(message = "city cannot be blank")
  private String city;

  /**
   * The postal code of the city.
   */
  @NotBlank(message = "zip cannot be blank")
  private String zip;

  /**
   * The person's phone number.
   */
  @NotBlank(message = "phone cannot be blank")
  private String phone;

  /**
   * The person's email address.
   */
  @NotBlank(message = "email cannot be blank")
  @Email(message = "email should be valid")
  private String email;

}
