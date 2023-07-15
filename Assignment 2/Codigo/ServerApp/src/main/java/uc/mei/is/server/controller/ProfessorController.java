package uc.mei.is.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.*;
import uc.mei.is.server.network.EmulateNetworkDelays;
import uc.mei.is.server.entity.Professor;
import uc.mei.is.server.handler.CreateProfessor;
import uc.mei.is.server.repository.ProfessorRepository;

@RestController
@RequestMapping(value="/api/professor",produces = "application/json", method = {RequestMethod.GET, RequestMethod.PUT,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PATCH})
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorRepository professorRepository;
    private final Logger logger = LoggerFactory.getLogger(ProfessorController.class);

    // CRUD: Create one
    @PutMapping(value = "add")
    public Mono<Professor> createProfessor(@RequestBody CreateProfessor createProfessor) {
        Professor professor = Professor.builder()
            .name(createProfessor.getName())
            .build();

        this.logger.info("CRUD - Create " + professor.getName());

        return this.professorRepository.save(professor).delayElement(EmulateNetworkDelays.emulate());
    }

    // CRUD: Read all
    @GetMapping(value = "getAll")
    public Flux<Professor> getAllProfessors() {
        this.logger.info("CRUD - Read All");

        return this.professorRepository.findAll().delaySequence(EmulateNetworkDelays.emulate());
    }

    // CRUD: Read by name
    @GetMapping(value = "getByName/{name}")
    public Mono<Professor> getByName(@PathVariable String name) {
        this.logger.info("CRUD - Read By Name " + name);

        return this.professorRepository.findByName(name).delayElement(EmulateNetworkDelays.emulate());
    }

    // CRUD: Read by id
    @GetMapping(value = "{id}")
    public Mono<Professor> getById(@PathVariable Long id) {
        this.logger.info("CRUD - Read By ID " + id);

        return this.professorRepository.findById(id).delayElement(EmulateNetworkDelays.emulate());
    }

    // CRUD: Update by id
    @PatchMapping(value = "update/{id}")
    public Mono<Professor> updateProfessor(@PathVariable Long id, @RequestBody CreateProfessor createProfessor){
        Professor student = Professor.builder()
            .id(id)
            .name(createProfessor.getName())
            .build();

        this.logger.info("CRUD - Update By ID " + id);

        return this.professorRepository.save(student).delayElement(EmulateNetworkDelays.emulate());
    }

    // CRUD: Delete by id
    @DeleteMapping(value = "delete/{id}")
    public Mono<Void> deleteById(@PathVariable Long id){
        this.logger.info("CRUD - Delete By ID " + id);

        return this.professorRepository.deleteById(id).delayElement(EmulateNetworkDelays.emulate());
    }

}
