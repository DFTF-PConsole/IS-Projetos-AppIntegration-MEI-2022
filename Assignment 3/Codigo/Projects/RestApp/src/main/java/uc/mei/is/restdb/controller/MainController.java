package uc.mei.is.restdb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.*;
import uc.mei.is.restdb.models.MinMaxTempLocation;
import uc.mei.is.restdb.models.MinMaxTempStation;
import uc.mei.is.restdb.models.MinTempWeatherStationsRedAlert;
import uc.mei.is.restdb.models.NumberAlertsType;
import uc.mei.is.restdb.models.NumberAlertsWeatherStation;
import uc.mei.is.restdb.models.TempReadingsStandardWeatherEventsLocation;
import uc.mei.is.restdb.models.TempReadingsStandardWeatherEventsStation;
import uc.mei.is.restdb.models.WeatherStation;
import uc.mei.is.restdb.repository.MinMaxTempLocationRepository;
import uc.mei.is.restdb.repository.MinMaxTempStationRepository;
import uc.mei.is.restdb.repository.MinTempWeatherStationsRedAlertRepository;
import uc.mei.is.restdb.repository.NumberAlertsTypeRepository;
import uc.mei.is.restdb.repository.NumberAlertsWeatherStationRepository;
import uc.mei.is.restdb.repository.TempReadingsStandardWeatherEventsLocationRepository;
import uc.mei.is.restdb.repository.TempReadingsStandardWeatherEventsStationRepository;
import uc.mei.is.restdb.repository.WeatherStationRepository;

@RestController
@RequestMapping(value="/api/rest/",produces = "application/json", method = {RequestMethod.GET, RequestMethod.PUT,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PATCH})
@RequiredArgsConstructor
public class MainController {
    private final WeatherStationRepository stationRepository;
    private final TempReadingsStandardWeatherEventsStationRepository tempReadingsStandardWeatherEventsStationRepository;
    private final TempReadingsStandardWeatherEventsLocationRepository tempReadingsStandardWeatherEventsLocationRepository;
    private final MinMaxTempLocationRepository minMaxTempLocationRepository;
    private final MinMaxTempStationRepository minMaxTempStationRepository;
    private final NumberAlertsWeatherStationRepository numberAlertsWeatherStationRepository;
    private final NumberAlertsTypeRepository alertsTypeRepository;
    private final MinTempWeatherStationsRedAlertRepository minTempWeatherStationsRedAlertRepository;
    private final Logger logger = LoggerFactory.getLogger(MainController.class);


    // CRUD: Read all
    @GetMapping(value = "station/getAll")
    public Flux<WeatherStation> getAllStations() {
        this.logger.info("CRUD - Read All");

        return this.stationRepository.findAll();
    }

    // CRUD: Read by local
    @GetMapping(value = "station/getByLocation/{location}")
    public Flux<WeatherStation> getByLocation( @PathVariable String location) {
        this.logger.info("CRUD - Read By Location " + location);
        return this.stationRepository.findByLocation(location);
    }

    
    // CRUD: Read by name
    @GetMapping(value = "station/getByStation/{Station}")
    public Flux<WeatherStation> getByStation( @PathVariable String station) {
        this.logger.info("CRUD - Read By Station " + station);
        return this.stationRepository.findByStation(station);
    }

        // CRUD: Read by name
    @GetMapping(value = "req1/{Station}")
    public Flux<TempReadingsStandardWeatherEventsStation> getReq1ByStation(@PathVariable String station) {
        this.logger.info("CRUD - Read By Station " + station);
        return this.tempReadingsStandardWeatherEventsStationRepository.findByStation(station);
    }

        // CRUD: Read by name
    @GetMapping(value = "req1/getAll")
    public Flux<TempReadingsStandardWeatherEventsStation> getReq1All() {
        this.logger.info("CRUD - Read All");
        return this.tempReadingsStandardWeatherEventsStationRepository.findAll();
    }

    @GetMapping(value = "req2/{location}")
    public Flux<TempReadingsStandardWeatherEventsLocation> getReq2ByLocation(@PathVariable String location) {
        this.logger.info("CRUD - Read By location " + location);
        return this.tempReadingsStandardWeatherEventsLocationRepository.findByLocation(location);
    }

        // CRUD: Read by name
    @GetMapping(value = "req2/getAll")
    public Flux<TempReadingsStandardWeatherEventsLocation> getReq2All() {
        this.logger.info("CRUD - Read All ");
        return this.tempReadingsStandardWeatherEventsLocationRepository.findAll();
    }

        // CRUD: Read by name
        @GetMapping(value = "req3/getAll")
        public Flux<MinMaxTempStation> getReq3All() {
            this.logger.info("CRUD - Read All ");
            return this.minMaxTempStationRepository.findAll();
        }
        // CRUD: Read by name
        @GetMapping(value = "req4/getAll")
        public Flux<MinMaxTempLocation> getReq4All() {
            this.logger.info("CRUD - Read All ");
            return this.minMaxTempLocationRepository.findAll();
        }    
        // CRUD: Read by name
        @GetMapping(value = "req5/getAll")
        public Flux<NumberAlertsWeatherStation> getReq5All() {
            this.logger.info("CRUD - Read All ");
            return this.numberAlertsWeatherStationRepository.findAll();
        }    
        // CRUD: Read by name
        @GetMapping(value = "req6/getAll")
        public Flux<NumberAlertsType> getReq6All() {
            this.logger.info("CRUD - Read All ");
            return this.alertsTypeRepository.findAll();
        }    
        // CRUD: Read by name
        @GetMapping(value = "req7/getAll")
        public Flux<MinTempWeatherStationsRedAlert> getReq7All() {
            this.logger.info("CRUD - Read All ");
            return this.minTempWeatherStationsRedAlertRepository.findAll();
        }   
        // CRUD: Read by name
        @GetMapping(value = "req8/getAll")
        public Flux<> getReq8All() {
            this.logger.info("CRUD - Read All ");
            return this. .findAll();
        }  
        // CRUD: Read by name
        @GetMapping(value = "req9/getAll")
        public Flux<> getReq9All() {
            this.logger.info("CRUD - Read All ");
            return this. .findAll();
        }  
        // CRUD: Read by name
        @GetMapping(value = "req10/getAll")
        public Flux<> getReq10All() {
            this.logger.info("CRUD - Read All ");
            return this. .findAll();
        }  
        // CRUD: Read by name
        @GetMapping(value = "req11/getAll")
        public Flux<> getReq11All() {
            this.logger.info("CRUD - Read All ");
            return this. .findAll();
        }  
        

}
