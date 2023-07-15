package uc.mei.is.restdb.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import uc.mei.is.restdb.models.MinMaxTempStation;


public interface MinMaxTempStationRepository extends R2dbcRepository<MinMaxTempStation,Long> {

}
