package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.request.PersonDto;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * PersonController handles HTTP requests related to Person entities.
 * It provides endpoints for creating, updating, deleting, and retrieving persons.
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
   * Adds a new person.
   *
   * @param person the Person object to be added.
   * @return a ResponseEntity containing the saved Person object.
   */
  @PostMapping(path = "", produces = "application/json")
  public ResponseEntity<Person> addPerson(@RequestBody @Valid Person person) {
    Person savedPerson = personService.save(person);
    return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(savedPerson);
  }

  /**
   * Updates an existing person identified by firstName and lastName.
   *
   * @param firstName the first name of the person to update.
   * @param lastName  the last name of the person to update.
   * @param personDto the PersonDto containing updated information.
   * @return a ResponseEntity containing the updated Person object.
   */
  @PutMapping(path = "/firstname/{firstName}/lastname/{lastName}", produces = "application/json")
  public ResponseEntity<Person> updatePerson(
          @PathVariable String firstName,
          @PathVariable String lastName,
          @RequestBody PersonDto personDto) {
    Person updatedPerson = personService.update(firstName, lastName, personDto);
    return ResponseEntity.ok(updatedPerson);
  }

  /**
   * Deletes a person identified by firstName and lastName.
   *
   * @param firstName the first name of the person to delete.
   * @param lastName  the last name of the person to delete.
   * @return a ResponseEntity with no content.
   */
  @DeleteMapping(path = "/firstname/{firstName}/lastname/{lastName}", produces = "application/json")
  public ResponseEntity<Void> deletePerson(
          @PathVariable String firstName,
          @PathVariable String lastName) {
    personService.delete(firstName, lastName);
    return ResponseEntity.noContent().build();
  }

  /**
   * Retrieves a person by their first name and last name.
   *
   * @param firstName the first name of the person.
   * @param lastName  the last name of the person.
   * @return a ResponseEntity containing the Person object.
   */
  @GetMapping(path = "/firstname/{firstName}/lastname/{lastName}", produces = "application/json")
  public ResponseEntity<Person> getPersonByName(
          @PathVariable String firstName,
          @PathVariable String lastName) {
    Person person = personService.findByFirstNameAndLastName(firstName, lastName);
    return ResponseEntity.ok(person);
  }

}
