package cl.villegas.mybatis.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import cl.villegas.mybatis.model.Person;
import cl.villegas.mybatis.service.PersonServiceImpl;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@RequestMapping(path = "/personas")
public class PersonController {
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonServiceImpl service;

    @ApiOperation(value = "Elimina una persona mediante su id")
    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable(value = "id") long id) {
        service.delete(id);
    }

    @ApiOperation(value = "Retorna una lista de personas en formato json/xml", response = List.class)
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public List<Person> findAll() {
        logger.info("personas list ejecutada");
        return service.findAll();
    }

    @ApiOperation(value = "Obtiene una persona mediante su id")
    @GetMapping(path = "{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public Person findById(@PathVariable(value = "id") long id) {
        Person person = service.findById(id);
        return person;
    }

    @ApiOperation(value = "Registra una persona")
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody Person person) {
        service.save(person);
    }

    @ApiOperation(value = "Actualiza una persona mediante un json")
    @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody Person person) {
        service.update(person);
    }
}