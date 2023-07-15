package uc.mei.is.restdb.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import uc.mei.is.restdb.models.MinMaxTempLocation;


public interface MinMaxTempLocationRepository extends R2dbcRepository<MinMaxTempLocation,Long> {

}
