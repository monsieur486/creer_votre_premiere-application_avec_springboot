package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.request.PersonDto;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/person")
public class PersonController {

  private final PersonService personService;

  @GetMapping(path = "/all", produces = "application/json")
  public ResponseEntity<Iterable<Person>> getAllPersons() {
    Iterable<Person> persons = personService.findAll();
    return ResponseEntity.ok(persons);
  }

  @PostMapping(path = "", produces = "application/json")
  public ResponseEntity<Person> addPerson(@RequestBody @Valid Person person) {
    Person savedPerson = personService.save(person);
    return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(savedPerson);
  }

  @PutMapping(path = "/firstname/{firstName}/lastname/{lastName}", produces = "application/json")
  public ResponseEntity<Person> updatePerson(
          @PathVariable String firstName,
          @PathVariable String lastName,
          @RequestBody PersonDto personDto) {
    Person updatedPerson = personService.update(firstName, lastName, personDto);
    return ResponseEntity.ok(updatedPerson);
  }

  @DeleteMapping(path = "/firstname/{firstName}/lastname/{lastName}", produces = "application/json")
  public ResponseEntity<Void> deletePerson(
          @PathVariable String firstName,
          @PathVariable String lastName) {
    personService.delete(firstName, lastName);
    return ResponseEntity.noContent().build();
  }

  @GetMapping(path = "/firstname/{firstName}/lastname/{lastName}", produces = "application/json")
  public ResponseEntity<Person> getPersonByName(
          @PathVariable String firstName,
          @PathVariable String lastName) {
    Person person = personService.findByFirstNameAndLastName(firstName, lastName);
    return ResponseEntity.ok(person);
  }

}
