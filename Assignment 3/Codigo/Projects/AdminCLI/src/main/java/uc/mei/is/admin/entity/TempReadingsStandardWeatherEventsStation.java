package uc.mei.is.admin.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;

import lombok.*;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TempReadingsStandardWeatherEventsStation implements Serializable {

  private Integer id;
  private String station;
  private Integer numberTemperatureReadings;
  private ZonedDateTime createdAt;
  
}
