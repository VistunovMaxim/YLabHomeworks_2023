package io.ylab.intensive.lesson04.eventsourcing.api;

import java.io.IOException;
import java.util.List;

import io.ylab.intensive.lesson04.eventsourcing.Person;

public interface PersonApi {
  void deletePerson(Long personId) throws IOException;

  void savePerson(Long personId, String firstName, String lastName, String middleName) throws IOException;

  Person findPerson(Long personId);

  List<Person> findAll();
}
