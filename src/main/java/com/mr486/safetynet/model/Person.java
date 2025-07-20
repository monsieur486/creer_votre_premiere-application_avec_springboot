package com.mr486.safetynet.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a person with personal information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

  /**
   * The person's first name.
   */
  @NotBlank(message = "firstname cannot be blank")
  private String firstName;

  /**
   * The person's last name.
   */
  @NotBlank(message = "lastname cannot be blank")
  private String lastName;

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