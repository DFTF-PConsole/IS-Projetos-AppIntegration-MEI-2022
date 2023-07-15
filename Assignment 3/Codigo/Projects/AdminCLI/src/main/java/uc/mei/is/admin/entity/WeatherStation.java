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
public class WeatherStation implements Serializable {

  private Integer id;

  private String station;

  private String location;

  private ZonedDateTime createdAt;
  
}
