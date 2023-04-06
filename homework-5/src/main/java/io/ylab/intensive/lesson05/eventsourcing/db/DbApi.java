package io.ylab.intensive.lesson05.eventsourcing.db;

import io.ylab.intensive.lesson05.eventsourcing.Person;

public interface DbApi{

    void insertPerson(Person person);

    void deletePerson(Long personId);
}
