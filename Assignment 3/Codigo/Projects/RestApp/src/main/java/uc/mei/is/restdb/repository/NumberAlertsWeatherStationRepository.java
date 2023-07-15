package uc.mei.is.restdb.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import uc.mei.is.restdb.models.NumberAlertsWeatherStation;


public interface NumberAlertsWeatherStationRepository extends R2dbcRepository<NumberAlertsWeatherStation,Long> {

}
