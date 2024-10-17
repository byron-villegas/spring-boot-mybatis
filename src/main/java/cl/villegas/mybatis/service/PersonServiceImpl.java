package cl.villegas.mybatis.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.villegas.mybatis.model.Person;
import cl.villegas.mybatis.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository repository;

    @Override
    public void delete(long id) {
        if (id > 0)
            repository.delete(id);
    }

    @Override
    public List<Person> findAll() {
        return repository.findAll();
    }

    @Override
    public Person findById(long id) {
        return repository.findById(id);
    }

    @Override
    public void save(Person person) {
        repository.save(person);
    }

    @Override
    public void update(Person person) {
        repository.update(person);
    }
}