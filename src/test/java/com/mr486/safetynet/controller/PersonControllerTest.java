package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.request.PersonDto;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PersonControllerTest {

  @Mock
  private PersonService personService;

  @InjectMocks
  private PersonController personController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    List<Person> personList = new ArrayList<>();
    Person person = new Person();
    person.setFirstName("John");
    person.setLastName("Doe");
    person.setAddress("123 Main St");
    person.setCity("Paris");
    person.setZip("75000");
    person.setPhone("0102030405");
    person.setEmail("mail@test.com");
    personList.add(person);
    when(personService.findAll()).thenReturn(personList);
    when(personService.findByFirstNameAndLastName("John", "Doe")).thenReturn(person);
  }

  @Test
  void testGetAllPersons() {
    List<Person> persons = (List<Person>) personController.getAllPersons().getBody();
    assertNotNull(persons);
    assertFalse(persons.isEmpty());
    assertEquals(1, persons.size());
    assertEquals("John", persons.get(0).getFirstName());
    assertEquals("Doe", persons.get(0).getLastName());

  }

  @Test
  void testGetPersonByName() {
    Person person = personController.getPersonByName("John", "Doe").getBody();
    assertNotNull(person);
    assertEquals("John", person.getFirstName());
    assertEquals("Doe", person.getLastName());
    assertEquals("123 Main St", person.getAddress());
  }

  @Test
  void testAddPerson() {
    Person person = new Person();
    person.setFirstName("Jane");
    person.setLastName("Doe");
    person.setAddress("456 Elm St");
    person.setCity("Lyon");
    person.setZip("69000");
    person.setPhone("0607080910");
    person.setEmail("mail@test.com");

    when(personService.save(person)).thenReturn(person);
    Person savedPerson = personController.addPerson(person).getBody();
    assertNotNull(savedPerson);
  }

  @Test
  void testUpdatePerson() {
    PersonDto personDto = new PersonDto();
    personDto.setAddress("789 Oak St");
    personDto.setCity("Marseille");
    personDto.setZip("13000");
    personDto.setPhone("0203040506");
    personDto.setEmail("mail2@test.com");

    Person updatedPerson = new Person();
    updatedPerson.setFirstName("John");
    updatedPerson.setLastName("Doe");
    updatedPerson.setAddress("789 Oak St");
    updatedPerson.setCity("Marseille");
    updatedPerson.setZip("13000");
    updatedPerson.setPhone("0203040506");
    updatedPerson.setEmail("mail2@test.com");

    when(personService.update("John", "Doe", personDto)).thenReturn(updatedPerson);

    Person result = personController.updatePerson("John", "Doe", personDto).getBody();
    assertNotNull(result);
    assertEquals("John", result.getFirstName());
    assertEquals("Doe", result.getLastName());
    assertEquals("789 Oak St", result.getAddress());
    assertEquals("Marseille", result.getCity());
    assertEquals("13000", result.getZip());
    assertEquals("0203040506", result.getPhone());
    assertEquals("mail2@test.com", result.getEmail());
  }

  @Test
  void testDeletePerson() {
    String firstName = "John";
    String lastName = "Doe";

    personController.deletePerson(firstName, lastName);

    verify(personService).delete(firstName, lastName);

  }



}