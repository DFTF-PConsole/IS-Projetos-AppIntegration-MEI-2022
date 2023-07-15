package uc.mei.is.restdb.models;

import java.io.Serializable;
import java.time.ZonedDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Table("req1")
public class TempReadingsStandardWeatherEventsStation implements Serializable {
  @Id
  private Integer id;

  @Column("weather_station")
  private String station;
  
  @Column("number_temperature_readings")
  private Integer numberTemperatureReadings;

  @Column("created_at")
  private ZonedDateTime createdAt;
}
