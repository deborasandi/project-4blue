package com.project.endpoint.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.endpoint.service.PersonService;
import com.project.model.Person;

import java.util.List;

@RestController
@RequestMapping({"/person"})
@Slf4j
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> list(){
        return personService.list();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Person> findById(@PathVariable long id){
        return personService.findById(id);
    }

    @PostMapping
    public Person create(@RequestBody Person person){
        return personService.create(person);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Person> update(@PathVariable("id") long id,
                                 @RequestBody Person person) {
        return personService.update(id, person);
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<Object> delete(@PathVariable long id) {
        return personService.delete(id);
    }
}