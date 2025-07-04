package ru.job4j.auth.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.job4j.auth.dto.PasswordUpdateDto;
import ru.job4j.auth.dto.PersonDto;
import ru.job4j.auth.exception.DuplicateEntityException;
import ru.job4j.auth.exception.EntityNotFoundException;
import ru.job4j.auth.marker.Operation;
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
    public ResponseEntity<?> signUp(@Validated(Operation.OnCreate.class) @RequestBody PersonDto dto) {
        if (personRepository.findByLogin(dto.getLogin()).isPresent()) {
            throw new DuplicateEntityException("Login already exists");
        }
        Person person = new Person();
        person.setLogin(dto.getLogin());
        person.setPassword(encoder.encode(dto.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(personRepository.save(person));
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@Validated(Operation.OnUpdate.class) @RequestBody PersonDto dto) {
        if (personRepository.findById(dto.getId()).isEmpty()) {
            throw new EntityNotFoundException("Cannot update: person not found");
        }
        Person person = new Person();
        person.setId(dto.getId());
        person.setLogin(dto.getLogin());
        person.setPassword(encoder.encode(dto.getPassword()));
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

    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable int id, @Valid @RequestBody PasswordUpdateDto dto) {
        var person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person with id " + id + " not found"));
        person.setPassword(encoder.encode(dto.getPassword()));
        personRepository.save(person);
        return ResponseEntity.ok().build();
    }

}
