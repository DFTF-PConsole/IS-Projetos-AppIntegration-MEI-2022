package uc.mei.is.admin.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TempReadingsStandardWeatherEventsLocation implements Serializable {

  private Integer id;

  private String location;

  private Integer numberTemperatureReadings;
  
  private ZonedDateTime createdAt;
}
