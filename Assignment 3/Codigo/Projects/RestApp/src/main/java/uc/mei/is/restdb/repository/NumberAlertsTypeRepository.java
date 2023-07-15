package uc.mei.is.restdb.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import uc.mei.is.restdb.models.NumberAlertsType;


public interface NumberAlertsTypeRepository extends R2dbcRepository<NumberAlertsType,Long> {

}
