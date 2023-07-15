package uc.mei.is.server.controller;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerErrorException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uc.mei.is.server.network.EmulateNetworkDelays;
import uc.mei.is.server.entity.Relationship;
import uc.mei.is.server.handler.CreateRelationship;
import uc.mei.is.server.repository.RelationshipRepository;

@RestController
@RequestMapping(value="/api/relationship",produces = "application/json", method = {RequestMethod.GET, RequestMethod.PUT,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PATCH})
@RequiredArgsConstructor
public class RelationshipController {
    private static final AtomicInteger tryCount = new AtomicInteger(2);
    private final RelationshipRepository relationshipRepository;
    private final Logger logger = LoggerFactory.getLogger(RelationshipController.class);

    // CRUD: Create one
    @PutMapping(value = "add")
    public Mono<Relationship> addRelation(@RequestBody CreateRelationship createRelationship) {
        Relationship relationship = Relationship.builder()
            .studentId(createRelationship.getStudentId())
            .professorId(createRelationship.getProfessorId())
            .build();

        this.logger.info("CRUD - Create Professor ID" + relationship.getProfessorId() + " And Student ID " + relationship.getStudentId());

        return this.relationshipRepository.save(relationship).delayElement(EmulateNetworkDelays.emulate());
    }

    // Special Query: Tolerate network failures
    // CRUD: Read all
    @GetMapping(value = "getAll")
    public Flux<Relationship> getAllRelationship(){
        this.logger.info("CRUD - Read All");

        if(tryCount.get()>0) {
            tryCount.addAndGet(-1);
            return Flux.error(new RuntimeException ("\n\n\nIntentional exception\n\n\n"));        
        }
        else return this.relationshipRepository.findAll().delaySequence(EmulateNetworkDelays.emulate());

    }

    // CRUD: Read by professor id
    @GetMapping(value = "byProfessorId/{id}")
    public Flux<Relationship> getByProfessorId(@PathVariable Long id) {
        this.logger.info("CRUD - Read By Professor ID " + id);

        return this.relationshipRepository.findAllByProfessorId(id).delaySequence(EmulateNetworkDelays.emulate());
    }

    // CRUD: Read by student id
    @GetMapping(value = "byStudentId/{id}")
    public Flux<Relationship> getByStudentId(@PathVariable Long id) {
        this.logger.info("CRUD - Read By Student ID " + id);

        return this.relationshipRepository.findAllByStudentId(id).delaySequence(EmulateNetworkDelays.emulate());
    }

    // CRUD: Read by student id and professor id
    @GetMapping(value = "byStudentId/{sid}/byProfessorId/{pid}")
    public Mono<Relationship> getByBothId(@PathVariable Long sid, @PathVariable Long pid) {
        this.logger.info("CRUD - Read By Student ID " + sid + " And Professor ID " + pid);

        return this.relationshipRepository.findByStudentIdAndProfessorId(sid, pid).delayElement(EmulateNetworkDelays.emulate());
    }

    // CRUD: Delete by professor id
    @DeleteMapping(value = "delete/byProfessorId/{id}")
    public Flux<Void> deleteByProfessorId(@PathVariable Long id) {
        this.logger.info("CRUD - Delete By Professor ID " + id);

        return this.relationshipRepository.deleteAllByProfessorId(id).delaySequence(EmulateNetworkDelays.emulate());
    }


    // CRUD: Delete by student id
    @DeleteMapping(value = "delete/byStudentId/{id}")
    public Flux<Void> deleteByStudentId(@PathVariable Long id) {
        this.logger.info("CRUD - Delete By Student ID " + id);

        return this.relationshipRepository.deleteAllByStudentId(id).delaySequence(EmulateNetworkDelays.emulate());
    }

    // CRUD: Delete by student id and professor id
    @DeleteMapping(value = "delete/byStudentId/{sid}/byProfessorId/{pid}")
    public Mono<Void> deleteByBothId(@PathVariable Long sid,@PathVariable Long pid) {
        this.logger.info("CRUD - Delete By Student ID " + sid + " And Professor ID " + pid);

        return this.relationshipRepository.deleteByStudentIdAndProfessorId(sid, pid).delayElement(EmulateNetworkDelays.emulate());
    }
}
