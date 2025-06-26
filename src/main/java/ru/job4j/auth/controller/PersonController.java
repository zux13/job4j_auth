package ru.job4j.auth.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.auth.exception.DuplicateEntityException;
import ru.job4j.auth.exception.EntityNotFoundException;
import ru.job4j.auth.model.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonRepository personRepository;
    private final PasswordEncoder encoder;

    @GetMapping("/")
    public List<Person> findAll() {
        return (List<Person>) personRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        return personRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Person with id " + id + " not found"));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody Person person) {
        if (personRepository.findByLogin(person.getLogin()).isPresent()) {
            throw new DuplicateEntityException("Login already exists");
        }
        person.setPassword(encoder.encode(person.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(personRepository.save(person));
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        if (person.getId() == 0 || personRepository.findById(person.getId()).isEmpty()) {
            throw new EntityNotFoundException("Cannot update: person not found");
        }
        personRepository.save(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (personRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Cannot delete: person not found");
        }
        personRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
