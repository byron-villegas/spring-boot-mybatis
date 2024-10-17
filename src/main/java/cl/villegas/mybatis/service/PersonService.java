package cl.villegas.mybatis.service;

import java.util.List;

import cl.villegas.mybatis.model.Person;

public interface PersonService {
    void delete(long id);

    List<Person> findAll();

    Person findById(long id);

    void save(Person person);

    void update(Person person);
}