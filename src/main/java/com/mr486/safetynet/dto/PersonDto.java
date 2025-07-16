package com.mr486.safetynet.dto;

import com.mr486.safetynet.model.Person;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
