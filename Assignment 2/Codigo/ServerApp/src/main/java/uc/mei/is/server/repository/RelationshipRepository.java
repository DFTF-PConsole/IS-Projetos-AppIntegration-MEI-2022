package uc.mei.is.server.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uc.mei.is.server.entity.Relationship;

public interface RelationshipRepository extends R2dbcRepository<Relationship,Long>{
    Flux<Relationship> findAllByProfessorId(Long professorId);
    Flux<Relationship> findAllByStudentId(Long studentId);
    Mono<Relationship> findByStudentIdAndProfessorId(Long studentId, Long professorId);
    Flux<Void> deleteAllByProfessorId(Long professorId);
    Flux<Void> deleteAllByStudentId(Long studentId);
    Mono<Void> deleteByStudentIdAndProfessorId(Long studentId, Long professorId);
}
