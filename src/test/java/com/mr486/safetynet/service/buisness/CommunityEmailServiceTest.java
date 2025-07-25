package com.mr486.safetynet.service.buisness;

import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommunityEmailServiceTest {

  @Mock
  private PersonService personService;

  @InjectMocks
  private CommunityEmailService communityEmailService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getEmailsByCity_returnsEmailsWhenPersonsFound() {
    String city = "Paris";
    Person p1 = new Person("Jean", "Dupont", "1 rue", city, "75000", "0102030405", "jean@ex.com");
    Person p2 = new Person("Marie", "Martin", "2 rue", city, "75000", "0102030406", "marie@ex.com");
    when(personService.findByCity(city)).thenReturn(List.of(p1, p2));

    Set<String> emails = communityEmailService.getEmailsByCity(city);

    assertEquals(2, emails.size());
    assertTrue(emails.contains("jean@ex.com"));
    assertTrue(emails.contains("marie@ex.com"));
  }

  @Test
  void getEmailsByCity_returnsEmptyWhenNoPersons() {
    String city = "Lyon";
    when(personService.findByCity(city)).thenReturn(List.of());

    Set<String> emails = communityEmailService.getEmailsByCity(city);

    assertTrue(emails.isEmpty());
  }
}