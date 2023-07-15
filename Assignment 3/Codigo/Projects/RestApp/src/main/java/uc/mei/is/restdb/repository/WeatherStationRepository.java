package uc.mei.is.restdb.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uc.mei.is.restdb.models.WeatherStation;


public interface WeatherStationRepository extends R2dbcRepository<WeatherStation,Long> {

    Mono<WeatherStation> findById(Integer id);
    Mono<WeatherStation> findByStationAndLocation(String station, String location);
    Flux<WeatherStation> findByStation(String station);
    Flux<WeatherStation> findByLocation(String location);
}
