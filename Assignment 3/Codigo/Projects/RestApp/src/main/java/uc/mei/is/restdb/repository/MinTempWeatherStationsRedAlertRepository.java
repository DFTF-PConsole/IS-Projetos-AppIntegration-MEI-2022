package uc.mei.is.restdb.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import uc.mei.is.restdb.models.MinTempWeatherStationsRedAlert;


public interface MinTempWeatherStationsRedAlertRepository extends R2dbcRepository<MinTempWeatherStationsRedAlert,Long> {

}
