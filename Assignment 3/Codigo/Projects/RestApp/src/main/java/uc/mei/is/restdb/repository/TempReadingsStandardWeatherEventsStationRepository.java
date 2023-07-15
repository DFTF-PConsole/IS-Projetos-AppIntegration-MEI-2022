package uc.mei.is.restdb.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uc.mei.is.restdb.models.TempReadingsStandardWeatherEventsStation;


public interface TempReadingsStandardWeatherEventsStationRepository extends R2dbcRepository<TempReadingsStandardWeatherEventsStation,Long> {

    Mono<TempReadingsStandardWeatherEventsStation> findById(Integer id);
    Flux<TempReadingsStandardWeatherEventsStation> findByStation(String station);

    
}
