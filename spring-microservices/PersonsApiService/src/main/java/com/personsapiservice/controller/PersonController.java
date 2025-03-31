package com.personsapiservice.controller;

import com.personsapiservice.dto.PersonDto;
import com.personsapiservice.exception.PersonNotFoundException;
import com.personsapiservice.repository.PersonRepository;
import com.personsapiservice.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;
    private final PersonRepository personRepository;

    public PersonController(PersonService personService, PersonRepository personRepository) {
        this.personService = personService;
        this.personRepository = personRepository;
    }

    @GetMapping
    public List<PersonDto> getAll() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getPersonById(@PathVariable Long id) {
        Optional<PersonDto> person = personService.getPersonById(id);
        return person.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public PersonDto createPerson(@RequestBody PersonDto person) {
        return personService.createPerson(person);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable("id") Long id, @RequestBody PersonDto updates) {
        try {
            PersonDto updatedPerson = personService.updatePerson(id, updates);
            return ResponseEntity.ok(updatedPerson);
        } catch (PersonNotFoundException error) {
            return ResponseEntity.notFound().build();
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonById(@PathVariable Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-company/{companyId}")
    public List<PersonDto> getPersonsByCompanyId(@PathVariable Long companyId) {
        return personService.getPersonsByCompanyId(companyId);
    }
}