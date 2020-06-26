package com.project.endpoint.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.model.Person;
import com.project.repository.PersonRepository;

import java.util.List;

@Service
@Slf4j
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> list (){
        return personRepository.findAll();
    }

    public ResponseEntity<Person> findById(Long id){
        return personRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    public Person create(Person person){
        return personRepository.save(person);
    }

    public ResponseEntity<Person> update(Long id, Person person){
        return personRepository.findById(id)
                .map(record -> {
                    record.setName(person.getName());
                    record.setAddress(person.getAddress());
                    record.setBirthDate(person.getBirthDate());
                    record.setAge(person.getAge());
                    record.setGenre(person.getGenre());

                    Person updated = personRepository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Object> delete(Long id){
        return personRepository.findById(id)
                .map(record -> {
                    personRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
