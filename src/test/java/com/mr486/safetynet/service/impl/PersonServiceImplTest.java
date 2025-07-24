package com.mr486.safetynet.service.impl;

import com.mr486.safetynet.dto.request.PersonDto;
import com.mr486.safetynet.exception.EntityAlreadyExistsException;
import com.mr486.safetynet.exception.EntityNotFoundException;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceImplTest {

  @Mock
  private PersonRepository personRepository;

  @InjectMocks
  private PersonServiceImpl personServiceImpl;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void findByFirstNameAndLastName_shouldReturnPerson_whenExists() {
    Person person = new Person("John", "Doe", "address", "city", "zip", "phone", "email");
    when(personRepository.exists("John", "Doe")).thenReturn(true);
    when(personRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.of(person));

    Person result = personServiceImpl.findByFirstNameAndLastName("John", "Doe");

    assertEquals("John", result.getFirstName());
    assertEquals("Doe", result.getLastName());
  }

  @Test
  void findByFirstNameAndLastName_shouldThrowException_whenNotExists() {
    when(personRepository.exists("Jane", "Smith")).thenReturn(false);

    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> personServiceImpl.findByFirstNameAndLastName("Jane", "Smith"));

    assertEquals("Person not found: Jane Smith", exception.getMessage());
  }

  @Test
  void save_shouldAddPerson_whenNotExists() {
    Person person = new Person("Alice", "Wonderland", "address", "city", "zip", "phone", "email");
    when(personRepository.exists("Alice", "Wonderland")).thenReturn(false);
    when(personRepository.save(person)).thenReturn(person);

    Person result = personServiceImpl.save(person);

    assertEquals(person, result);
  }

  @Test
  void save_shouldThrowException_whenPersonAlreadyExists() {
    Person person = new Person("John", "Doe", "address", "city", "zip", "phone", "email");
    when(personRepository.exists("John", "Doe")).thenReturn(true);

    EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class,
            () -> personServiceImpl.save(person));

    assertEquals("Person already exists: John Doe", exception.getMessage());
  }

  @Test
  void delete_shouldRemovePerson_whenExists() {
    when(personRepository.exists("John", "Doe")).thenReturn(true);

    personServiceImpl.delete("John", "Doe");
    verify(personRepository, times(1)).delete("John", "Doe");
  }

  @Test
  void delete_shouldThrowException_whenNotExists() {
    when(personRepository.exists("Ghost", "Person")).thenReturn(false);

    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> personServiceImpl.delete("Ghost", "Person"));

    assertEquals("Person not found: Ghost Person", exception.getMessage());
  }

  @Test
  void update_shouldUpdatePerson_whenExists() {
    Person existing = new Person("John", "Doe", "address", "city", "zip", "phone", "email");
    PersonDto dto = new PersonDto("newAddress", "newCity", "newZip", "newPhone", "newEmail");
    Person updated = new Person("John", "Doe", "newAddress", "newCity", "newZip", "newPhone", "newEmail");

    when(personRepository.exists("John", "Doe")).thenReturn(true);
    when(personRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.of(existing));
    when(personRepository.save(any(Person.class))).thenReturn(updated);

    Person result = personServiceImpl.update("John", "Doe", dto);

    assertEquals("newAddress", result.getAddress());
    assertEquals("newCity", result.getCity());
    assertEquals("newZip", result.getZip());
    assertEquals("newPhone", result.getPhone());
    assertEquals("newEmail", result.getEmail());
  }

  @Test
  void update_shouldThrowException_whenNotExists() {
    PersonDto dto = new PersonDto("address", "city", "zip", "phone", "email");
    when(personRepository.exists("Ghost", "Person")).thenReturn(false);

    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> personServiceImpl.update("Ghost", "Person", dto));

    assertEquals("Person not found: Ghost Person", exception.getMessage());
  }

  @Test
  void findAll_shouldReturnAllPersons() {
    Person person1 = new Person("John", "Doe", "address", "city", "zip", "phone", "email");
    Person person2 = new Person("Jane", "Smith", "address2", "city2", "zip2", "phone2", "email2");
    when(personRepository.findAll()).thenReturn(List.of(person1, person2));

    List<Person> result = personServiceImpl.findAll();

    assertEquals(2, result.size());
    assertTrue(result.contains(person1));
    assertTrue(result.contains(person2));
  }

  @Test
  void findByAddress_shouldReturnPersonsWithGivenAddress() {
    Person person1 = new Person("John", "Doe", "123 Street", "city", "zip", "phone", "email");
    Person person2 = new Person("Jane", "Smith", "123 Street", "city", "zip", "phone", "email");
    when(personRepository.findByAddress("123 Street")).thenReturn(List.of(person1, person2));

    List<Person> result = personServiceImpl.findByAddress("123 Street");

    assertEquals(2, result.size());
    assertTrue(result.contains(person1));
    assertTrue(result.contains(person2));
  }

  @Test
  void findByAddress_shouldReturnEmptyList_whenNoPersonWithAddress() {
    when(personRepository.findByAddress("No Address")).thenReturn(List.of());

    List<Person> result = personServiceImpl.findByAddress("No Address");

    assertTrue(result.isEmpty());
  }
}