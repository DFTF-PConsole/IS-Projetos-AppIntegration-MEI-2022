package uc.mei.is.server.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;
import uc.mei.is.server.entity.Professor;

public interface ProfessorRepository extends R2dbcRepository<Professor,Long>{
    Mono<Professor> findByName(String name);
}
