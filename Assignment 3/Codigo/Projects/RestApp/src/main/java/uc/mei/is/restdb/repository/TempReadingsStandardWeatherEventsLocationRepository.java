package uc.mei.is.restdb.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uc.mei.is.restdb.models.TempReadingsStandardWeatherEventsLocation;


public interface TempReadingsStandardWeatherEventsLocationRepository extends R2dbcRepository<TempReadingsStandardWeatherEventsLocation,Long> {

    Mono<TempReadingsStandardWeatherEventsLocation> findById(Integer id);
    Flux<TempReadingsStandardWeatherEventsLocation> findByLocation(String location);

    
}
