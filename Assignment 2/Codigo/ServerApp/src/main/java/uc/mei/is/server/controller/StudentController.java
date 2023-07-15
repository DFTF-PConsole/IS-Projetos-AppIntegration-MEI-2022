package uc.mei.is.server.controller;

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
import uc.mei.is.server.entity.Student;
import uc.mei.is.server.handler.CreateStudent;
import uc.mei.is.server.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value="/api/student",produces = "application/json", method = {RequestMethod.GET, RequestMethod.PUT,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PATCH})
@RequiredArgsConstructor
public class StudentController {
    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentController.class);

    // CRUD: Create one
    @PutMapping(value = "add")
    public Mono<Student> createStudent(@RequestBody CreateStudent createStudent){
        Student student = Student.builder()
            .name(createStudent.getName())
            .birthdate(createStudent.getBirthdate())
            .credits(createStudent.getCredits())
            .gpa(createStudent.getGpa())
            .build();

        this.logger.info("CRUD - Create " + student.getName());

        return this.studentRepository.save(student).delayElement(EmulateNetworkDelays.emulate());
    }

    // CRUD: Read all
    @GetMapping(value = "getAll")
    public Flux<Student> getAllStudents(){
        this.logger.info("CRUD - Read All");

        return this.studentRepository.findAll().delaySequence(EmulateNetworkDelays.emulate());
    }

    // CRUD: Read by name
    @GetMapping(value = "getByName/{name}")
    public Mono<Student> getByName(@PathVariable String name){
        this.logger.info("CRUD - Read By Name " + name);

        return this.studentRepository.findByName(name).delayElement(EmulateNetworkDelays.emulate());
    }

    // CRUD: Read by id
    @GetMapping(value = "{id}")
    public Mono<Student> getById(@PathVariable Long id){
        this.logger.info("CRUD - Read By ID " + id);

        return this.studentRepository.findById(id).delayElement(EmulateNetworkDelays.emulate());
    }

    // CRUD: Update by id
    @PatchMapping(value = "update/{id}")
    public Mono<Student> updateStudent(@PathVariable Long id, @RequestBody CreateStudent createStudent){
        Student student = Student.builder()
            .id(id)
            .name(createStudent.getName())
            .birthdate(createStudent.getBirthdate())
            .credits(createStudent.getCredits())
            .gpa(createStudent.getGpa())
            .build();

        this.logger.info("CRUD - Update By ID " + id);

        return this.studentRepository.save(student).delayElement(EmulateNetworkDelays.emulate());
    }

    // CRUD: Delete by id
    @DeleteMapping(value = "delete/{id}")
    public Mono<Void> deleteById(@PathVariable Long id){
        this.logger.info("CRUD - Delete By ID " + id);

        return this.studentRepository.deleteById(id).delayElement(EmulateNetworkDelays.emulate());
    }

}
