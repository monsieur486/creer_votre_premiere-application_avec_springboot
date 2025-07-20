package com.mr486.safetynet.service.impl;

import com.mr486.safetynet.dto.request.PersonDto;
import com.mr486.safetynet.exception.EntityAlreadyExistsException;
import com.mr486.safetynet.exception.EntityNotFoundException;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing Person entities.
 * This class provides methods to find, save, delete, and update persons,
 * as well as to find persons by their address.
 */
@Service
@RequiredArgsConstructor
public class PersonServiceImpl {

  private final PersonRepository personRepository;

  /**
   * Retrieves all persons from the repository.
   *
   * @return a list of all persons.
   */
  public Person findByFirstNameAndLastName(String firstName, String lastName) {
    if(!exists(firstName, lastName)) {
      throw peronNotFoundException(firstName, lastName);
    }

    return personRepository.findByFirstNameAndLastName(firstName, lastName)
            .orElseThrow(() -> peronNotFoundException(firstName, lastName));
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

  public void delete(String firstName, String lastName) {
    if (!exists(firstName, lastName)) {
      throw peronNotFoundException(firstName, lastName);
    }
    personRepository.delete(firstName, lastName);
  }

  public Person update(String firstName, String lastName, PersonDto personDto) {
    if (!exists(firstName, lastName)) {
      throw peronNotFoundException(firstName, lastName);
    }
    Person updatedPerson = new Person(
            firstName,
            lastName,
            personDto.getAddress(),
            personDto.getCity(),
            personDto.getZip(),
            personDto.getPhone(),
            personDto.getEmail()
    );
    personRepository.delete(firstName, lastName);
    return personRepository.save(updatedPerson);
  }

  public List<Person> findByAddress(String address) {
    return personRepository.findByAddress(address);
  }

  // Private methods for exception handling and existence checks

  private EntityAlreadyExistsException personDuplicateException(String firstName, String lastName) {
    return new EntityAlreadyExistsException(
            "Person already exists: " + firstName + " " + lastName);
  }

  private EntityNotFoundException peronNotFoundException(String firstName, String lastName) {
    return new EntityNotFoundException(
            "Person not found: " + firstName + " " + lastName);
  }

  private boolean exists(String firstName, String lastName) {
    return personRepository.exists(firstName, lastName);
  }
}
