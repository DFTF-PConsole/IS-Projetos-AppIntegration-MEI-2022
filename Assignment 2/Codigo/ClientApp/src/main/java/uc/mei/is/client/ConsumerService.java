package uc.mei.is.client;

import java.io.FileOutputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.javatuples.Pair;
import org.springframework.web.reactive.function.client.WebClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;
import reactor.util.retry.Retry;
import uc.mei.is.client.entity.*;

@Builder
@AllArgsConstructor
@Data
public class ConsumerService {
    private final WebClient webClient;

    private Flux<Student> getAllStudents(){
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/{path}/getAll")
                .build(Student.PATH))
            .retrieve()
            .bodyToFlux(Student.class);
    }

    private Flux<Relationship> getAllRelationships(){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{path}/getAll")
                        .build(Relationship.PATH))
                .retrieve()
                .bodyToFlux(Relationship.class);
    }

    private Flux<Relationship> getAllRelationshipsByStudentId(Long id){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{path}/byStudentId/{id}")
                        .build(Relationship.PATH, id))
                .retrieve()
                .bodyToFlux(Relationship.class);
    }

    private Flux<Relationship> getAllRelationshipsByProfessorId(Long id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{path}/byProfessorId/{id}")
                        .build(Relationship.PATH, id))
                .retrieve()
                .bodyToFlux(Relationship.class);
    }

    private Flux<Professor> getAllProfessor(){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{path}/getAll")
                        .build(Professor.PATH))
                .retrieve()
                .bodyToFlux(Professor.class);
    }

    private Mono<Professor> getProfessorById(Long id){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{path}/{id}")
                        .build(Professor.PATH, id))
                .retrieve()
                .bodyToMono(Professor.class);
    }

    public void requirements(FileOutputStream ... files) {

        // REQ 9: Average number of professors per student. Note that some professors may not have students and vice-versa
        AtomicLong sumProfs = new AtomicLong(0);

        // REQ 6: Average and standard deviations of all student grades
        AtomicReference <Double> sumGpa = new AtomicReference<>(0.0);

        // REQ 8: The name of the eldest student
        AtomicReference <Student> eldestStudent = new AtomicReference<>();

        // REQ 2: Total number of students
        AtomicLong countTotal = new AtomicLong(0);

        // REQ 7: Average and standard deviations of students who have finished their graduation (with 180 credits)
        AtomicReference <Double> sumGpaFinished = new AtomicReference<>(0.0);

        // REQ 6: Average and standard deviations of all student grades
        // REQ 7: Average and standard deviations of students who have finished their graduation (with 180 credits)
        ArrayList<Pair<Double, Boolean>> listaGpa = new ArrayList<>();

        CountDownLatch semaforoReq9 = new CountDownLatch(1);
        CountDownLatch semaforoReq10 = new CountDownLatch(1);

        this.getAllStudents()
                .publishOn(Schedulers.boundedElastic())

                // REQ 11: Complete data of all students, by adding the names of their professors
                .doOnNext(student -> {
                    Printer.print("Student(name=" + student.getName() + ", birthdate=" + student.getBirthdate() + ", credits=" + student.getCredits() + ", gpa=" + student.getGpa() + ", professors=", files[10]);
                    Long sumProfsValue = this.getAllRelationshipsByStudentId(student.getId())
                            .flatMap(relationship ->  this.getProfessorById(relationship.getProfessorId()))
                            .doOnNext(professor -> Printer.print(professor.getName() + ",", files[10]))
                            .count()
                            .block();

                    // REQ 9: Average number of professors per student. Note that some professors may not have students and vice-versa
                    sumProfs.addAndGet(sumProfsValue == null ? 0 : sumProfsValue);
                    Printer.print(")\n", files[10]);
                })

                .doFinally(signalType -> semaforoReq9.countDown())
                .subscribe();


        this.getAllProfessor()
                .publishOn(Schedulers.boundedElastic())

                // REQ 10: Name and number of students per professor, sorted in descending order
                .map(professor -> {
                    Long countProfsValue = this.getAllRelationshipsByProfessorId(professor.getId()).count().block();
                    return Tuples.of(professor, (countProfsValue == null ? 0 : countProfsValue));
                })
                .sort((tupl1, tuple2) -> tupl1.getT2().compareTo(tuple2.getT2()) * -1)
                .doOnNext(tuple -> Printer.print("Professor " + tuple.getT1().getName() + " has " + tuple.getT2() + " students \n", files[9]))

                .doFinally(signalType -> semaforoReq10.countDown())
                .subscribe();


        Mono<Long> countTotalActive = this.getAllStudents()
                .publishOn(Schedulers.boundedElastic())

                // REQ 1: Names and birthdates of all students
                .doOnNext(student -> Printer.print("Student(name=" + student.getName() + ", birthdate=" + student.getBirthdate() + ")\n", files[0]))

                // REQ 4: Total number of courses completed for all students, knowing that each course is worth 6 credits.
                .doOnNext(student -> Printer.print(student + " Cadeira completas - " + student.getCredits()/6 + "\n", files[3]))

                // REQ 8: The name of the eldest student
                .doOnNext(student -> {
                    if (eldestStudent.get() == null || student.getBirthdate().isBefore(eldestStudent.get().getBirthdate())) {
                        eldestStudent.set(student);
                    }
                })

                // REQ 2: Total number of students
                .doOnNext(student ->  countTotal.incrementAndGet())

                // REQ 6: Average and standard deviations of all student grades
                .doOnNext(student -> {
                    sumGpa.set(sumGpa.get() + student.getGpa());
                    listaGpa.add(Pair.with(student.getGpa(), student.getCredits() == 180));
                })

                // REQ 7: Average and standard deviations of students who have finished their graduation (with 180 credits)
                .doOnNext(student -> {
                    if(student.getCredits() == 180) {
                        sumGpaFinished.set(sumGpaFinished.get() + student.getGpa());
                    }
                })

                // REQ 5: Data of students that are in the last year of their graduation (i.e., whose credits are at least 120 and less than 180). This list should be sorted, such that students closer to completion should come first. These data do not need to include the professors
                .filter(student -> student.getCredits()<180)
                .sort(Comparator.comparing(Student::getCredits).reversed())
                .doOnNext(student -> {
                    if(student.getCredits() >= 120){
                        Printer.print(student + "\n", files[4]);
                    }
                })

                // REQ 3: Total number of students that are active (i.e., that have less than 180 credits)
                .count();

        // REQ 3: Total number of students that are active (i.e., that have less than 180 credits)
        Long countTotalActiveValue = countTotalActive.block();
        countTotalActiveValue = countTotalActiveValue == null ? 0 : countTotalActiveValue;
        Printer.print(countTotalActiveValue, files[2]);

        // REQ 2: Total number of students
        long countTotalValue = countTotal.get();
        Printer.print(countTotalValue, files[1]);

        // REQ 8: The name of the eldest student
        Printer.print(eldestStudent.get().getName(), files[7]);

        // REQ 6: Average and standard deviations of all student grades
        double sumGpaValue = sumGpa.get();
        double avgGpa = countTotalValue == 0 ? 0.0 : (sumGpaValue / countTotalValue);
        double stdGpa = 0.0;

        // REQ 7: Average and standard deviations of students who have finished their graduation (with 180 credits)
        double sumGpaFinishedValue = sumGpaFinished.get();
        double avgGpaFinished = (countTotalValue - countTotalActiveValue) == 0 ? 0.0 : (sumGpaFinishedValue / (countTotalValue - countTotalActiveValue));
        double stdGpaFinished = 0.0;

        for (Pair<Double, Boolean> par : listaGpa) {
            // REQ 6: Average and standard deviations of all student grades
            stdGpa += Math.pow((par.getValue0() - avgGpa), 2);

            // REQ 7: Average and standard deviations of students who have finished their graduation (with 180 credits)
            if (Boolean.TRUE.equals(par.getValue1())) {
                stdGpaFinished += Math.pow((par.getValue0() - avgGpaFinished), 2);
            }
        }

        // REQ 6: Average and standard deviations of all student grades
        stdGpa = Math.sqrt(countTotalValue == 0 ? 0.0 : (stdGpa / countTotalValue));
        Printer.print("Average: " + avgGpa + ", Standard Deviation: " + stdGpa, files[5]);

        // REQ 7: Average and standard deviations of students who have finished their graduation (with 180 credits)
        stdGpaFinished = Math.sqrt((countTotalValue - countTotalActiveValue) == 0 ? 0.0 : (stdGpaFinished / (countTotalValue - countTotalActiveValue)));
        Printer.print("Average: " + avgGpaFinished + ", Standard Deviation: " + stdGpaFinished, files[6]);

        // Esperar pela conclusão do REQ 11 e 9
        try {
            semaforoReq9.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // REQ 9: Average number of professors per student. Note that some professors may not have students and vice-versa
        Printer.print(countTotalValue == 0 ? 0.0 : (sumProfs.get()/countTotalValue), files[8]);

        // Esperar pela conclusão do REQ 10
        try {
            semaforoReq10.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Special Query: Tolerate network failures
    public void specialQueryToTolerateNetworkFailures(FileOutputStream file) {

        AtomicLong timerStart = new AtomicLong(System.currentTimeMillis());
        this.getAllRelationships()
                    .doOnNext(relationship -> Printer.print(relationship + "\n", file))
                    .retryWhen(
                        Retry.backoff(3, Duration.ofSeconds(5))
                        .doAfterRetry(sig -> {
                            Printer.print("\nTIME TRY: "+(System.currentTimeMillis()-timerStart.get())+"ms\n", System.out);
                            sig.failure().printStackTrace();
                        })
                    )
                    .blockLast();
    }
}
