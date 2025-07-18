package com.mr486.safetynet.dto.request;

import com.mr486.safetynet.model.Person;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PersonDto is a Data Transfer Object (DTO) that represents a person's contact information.
 * It includes fields for address, city, zip code, phone number, and email.
 * This DTO is used to transfer person data between layers of the application.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

  @NotBlank(message = "address cannot be blank")
  private String address;

  @NotBlank(message = "city cannot be blank")
  private String city;

  @NotBlank(message = "zip cannot be blank")
  private String zip;

  @NotBlank(message = "phone cannot be blank")
  private String phone;

  @NotBlank(message = "email cannot be blank")
  @Email(message = "email should be valid")
  private String email;

  /**
   * Converts this DTO to a Person entity.
   *
   * @param firstName the first name of the person
   * @param lastName  the last name of the person
   * @return a new Person instance with the provided names and this DTO's data
   */
  public Person toPerson(String firstName, String lastName) {
    return new Person(firstName, lastName, address, city, zip, phone, email);
  }

}
