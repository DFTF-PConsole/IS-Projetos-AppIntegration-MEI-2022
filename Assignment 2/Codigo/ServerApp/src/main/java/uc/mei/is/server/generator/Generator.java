package uc.mei.is.server.generator;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import com.github.javafaker.Faker;
import reactor.core.scheduler.Schedulers;
import uc.mei.is.server.entity.Professor;
import uc.mei.is.server.entity.Relationship;
import uc.mei.is.server.entity.Student;
import uc.mei.is.server.repository.ProfessorRepository;
import uc.mei.is.server.repository.RelationshipRepository;
import uc.mei.is.server.repository.StudentRepository;

public class Generator {

    List<Student> students;
    List<Professor> professors;
    private final long sizeStudent;
    private final long sizeProfessor;
    private final long professorsPerStudents;
    final private Faker fkr;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final RelationshipRepository relationshipRepository;
    private Long completedCourse;

    public Generator(long sizeStudent, long sizeProfessor, long professorsPerStudents , long seed) {
        this.sizeStudent = sizeStudent;
        Random random = new Random(seed);
        this.sizeProfessor = sizeProfessor;
        this.professorsPerStudents=professorsPerStudents;

        this.completedCourse = (sizeStudent>10) ? sizeStudent/10 : 1;

        if(professorsPerStudents>sizeProfessor)
            throw new IllegalArgumentException("Number of professors per students needs to be smaller or equal to the number of professors.\nCurrently, professors is: "+sizeProfessor+" and professors per students is: "+professorsPerStudents+"\n");
        System.out.println("Populator Created!");

        this.students = new ArrayList<>();
        this.fkr = new Faker(Locale.ENGLISH, random);
        this.studentRepository = BeanUtil.getBean(StudentRepository.class);
        this.professorRepository = BeanUtil.getBean(ProfessorRepository.class);
        this.relationshipRepository = BeanUtil.getBean(RelationshipRepository.class);
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public void runStudents(){
        Student s;
        for (int i = 0; i < sizeStudent; i++) {
            Instant n = fkr.date().birthday(16,70).toInstant();
            int credits;
            if(completedCourse>0){
                credits = 180;
                completedCourse-=1;
            }
            else credits = fkr.number().numberBetween(0, 181);
            s = Student.builder()
                       .name(generateName())
                       .birthdate(LocalDate.ofInstant(n, ZoneOffset.UTC))
                       .credits(credits)
                       .gpa(fkr.number().randomDouble(2, 0, 20))
                       .build();
            studentRepository.save(s).subscribe();
        }
        System.out.println("Student Populated!");
    }

    public void runProfessors(){
        Professor s;
        for (int i = 0; i < sizeProfessor; i++) {
            s = Professor.builder()
                       .name(generateName())
                       .build();
            professorRepository.save(s).subscribe();
        }
        System.out.println("Professor Populated!");
    }

    public void setStudents(List<Student> s){
        students = s;
    }

    public void runRelationships(){
        List<Long> assignedProf = new ArrayList<>();
        long profCount = professorRepository.count().block();
        studentRepository.findAll()
                .take(sizeStudent)
                .publishOn(Schedulers.boundedElastic())
                .map(student ->{
                    Relationship s;
                    for (int i = 0; i < professorsPerStudents; i++) {
                        Long n = fkr.number().numberBetween(1, profCount);
                        while(assignedProf.contains(n))
                            n=fkr.number().numberBetween(1, profCount);
                        s = Relationship.builder()
                                .professorId(n)
                                .studentId(student.getId())
                                .build();
                        // System.out.println("The Following relation was attemped\n\n\n\n"+s);
                        relationshipRepository.save(s).subscribe();
                    }
                    return student;
                }).subscribe();
        System.out.println("Relations Populated!");
    }

    private String generateName() {
        return fkr.name().fullName();
    }

}
