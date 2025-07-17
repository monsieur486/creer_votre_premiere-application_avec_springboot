package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.PersonDto;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing persons.
 * Provides endpoints to retrieve, add, update, and delete persons.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/person")
public class PersonController {

  private final PersonService personService;

  /**
   * Retrieves all persons.
   *
   * @return a ResponseEntity containing an iterable of Person objects.
   */
  @GetMapping(path = "/all", produces = "application/json")
  public ResponseEntity<Iterable<Person>> getAllPersons() {
    Iterable<Person> persons = personService.findAll();
    return ResponseEntity.ok(persons);
  }

  /**
   * Retrieves a person by first name and last name.
   *
   * @param firstName the first name of the person
   * @param lastName  the last name of the person
   * @return a ResponseEntity containing the Person object if found, or an error response if not found
   */
  @GetMapping(path = "/firstname/{firstName}/lastname/{lastName}", produces = "application/json")
  public ResponseEntity<Person> getPersonByName(
          @PathVariable String firstName,
          @PathVariable String lastName) {
    Person person = personService.findByFirstNameAndLastName(firstName, lastName);
    return ResponseEntity.ok(person);
  }

  /**
   * Adds a new person.
   *
   * @param person the Person object to be added
   * @return a ResponseEntity containing the saved Person object
   */
  @PostMapping(path = "", produces = "application/json")
  public ResponseEntity<Person> addPerson(@RequestBody @Valid Person person) {
    Person savedPerson = personService.save(person);
    return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(savedPerson);
  }

  /**
   * Updates an existing person identified by first name and last name.
   *
   * @param firstName the first name of the person
   * @param lastName  the last name of the person
   * @param personDto the DTO containing updated person data
   * @return a ResponseEntity containing the updated Person object
   */
  @PutMapping(path = "/firstname/{firstName}/lastname/{lastName}", produces = "application/json")
  public ResponseEntity<Person> updatePerson(
          @PathVariable String firstName,
          @PathVariable String lastName,
          @RequestBody @Valid PersonDto personDto) {
    Person updatedPerson = personService.update(firstName, lastName, personDto);
    return ResponseEntity.ok(updatedPerson);
  }

  /**
   * Deletes a person identified by first name and last name.
   *
   * @param firstName the first name of the person
   * @param lastName  the last name of the person
   * @return a ResponseEntity with no content if deletion is successful
   */
  @DeleteMapping(path = "/firstname/{firstName}/lastname/{lastName}", produces = "application/json")
  public ResponseEntity<Void> deletePerson(
          @PathVariable String firstName,
          @PathVariable String lastName) {
    personService.delete(firstName, lastName);
    return ResponseEntity.noContent().build();
  }

}
