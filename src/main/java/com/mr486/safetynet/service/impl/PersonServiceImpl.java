package com.mr486.safetynet.service.impl;

import com.mr486.safetynet.dto.request.PersonDto;
import com.mr486.safetynet.exception.EntityAlreadyExistsException;
import com.mr486.safetynet.exception.EntityNotFoundException;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.repository.PersonRepository;
import com.mr486.safetynet.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing Person entities.
 * This class provides methods to find, save, delete, and update persons,
 * as well as to find persons by their address.
 */
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

  private final PersonRepository personRepository;

  /**
   * Finds a person by their first and last name.
   *
   * @param firstName the first name of the person to find.
   * @param lastName  the last name of the person to find.
   * @return the found Person entity.
   * @throws EntityNotFoundException if the person does not exist.
   */
  public Person findByFirstNameAndLastName(String firstName, String lastName) {
    return personRepository.findByFirstNameAndLastName(firstName, lastName)
            .orElseThrow(() -> personNotFoundException(firstName, lastName));
  }

  /**
   * Saves a new person to the repository.
   *
   * @param person the person to save.
   * @return the saved Person entity.
   */
  public Person save(Person person) {
    if (exists(person.getFirstName(), person.getLastName())) {
      throw personDuplicateException(person.getFirstName(), person.getLastName());
    }
    return personRepository.save(person);
  }

  /**
   * Deletes a person by their first and last name.
   *
   * @param firstName the first name of the person to delete.
   * @param lastName  the last name of the person to delete.
   */
  public void delete(String firstName, String lastName) {
    if (!exists(firstName, lastName)) {
      throw personNotFoundException(firstName, lastName);
    }
    personRepository.delete(firstName, lastName);
  }

  /**
   * Updates an existing person with new details.
   *
   * @param firstName the first name of the person to update.
   * @param lastName  the last name of the person to update.
   * @param personDto the DTO containing updated person details.
   * @return the updated Person entity.
   */
  public Person update(String firstName, String lastName, PersonDto personDto) {
    if (!exists(firstName, lastName)) {
      throw personNotFoundException(firstName, lastName);
    }
    Person existingPerson = findByFirstNameAndLastName(firstName, lastName);
    Person updatedPerson = Person.builder()
            .firstName(existingPerson.getFirstName())
            .lastName(existingPerson.getLastName())
            .address(personDto.getAddress())
            .city(personDto.getCity())
            .zip(personDto.getZip())
            .phone(personDto.getPhone())
            .email(personDto.getEmail())
            .build();
    return personRepository.save(updatedPerson);
  }

  /**
   * Finds persons by their address.
   *
   * @param address the address to search for.
   * @return a list of persons living at the specified address.
   */
  public List<Person> findByAddress(String address) {
    return personRepository.findByAddress(address);
  }

  /**
   * Finds all persons in the repository.
   *
   * @return a list of all persons.
   */
  public List<Person> findAll() {
    return personRepository.findAll();
  }

  /**
   * Finds persons by their last name.
   *
   * @param lastName the last name to search for.
   * @return a list of persons with the specified last name.
   */
  public List<Person> findByLastName(String lastName) {
    return personRepository.findByLastName(lastName);
  }

  /**
   * Finds persons by their city.
   *
   * @param city the city to search for persons.
   * @return a list of persons living in the specified city.
   */
  public List<Person> findByCity(String city) {
    return personRepository.findByCity(city);
  }

  // Private methods for exception handling and existence checks

  private EntityAlreadyExistsException personDuplicateException(String firstName, String lastName) {
    return new EntityAlreadyExistsException(
            "Person already exists: " + firstName + " " + lastName);
  }

  private EntityNotFoundException personNotFoundException(String firstName, String lastName) {
    return new EntityNotFoundException(
            "Person not found: " + firstName + " " + lastName);
  }

  private boolean exists(String firstName, String lastName) {
    return personRepository.exists(firstName, lastName);
  }
}
