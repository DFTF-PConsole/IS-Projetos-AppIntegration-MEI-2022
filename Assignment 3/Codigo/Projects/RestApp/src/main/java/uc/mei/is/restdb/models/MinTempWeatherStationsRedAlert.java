package uc.mei.is.restdb.models;

import java.io.Serializable;
import java.time.ZonedDateTime;

import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;

@Data
@Builder
@Table("req7")
public class MinTempWeatherStationsRedAlert implements Serializable {
  @Id
  private Integer id;

  @Column("weather_station")
  private String station;
  
  @Column("min_temp")
  private double minimumTemperature;

  @Column("created_at")
  private ZonedDateTime createdAt;
}
