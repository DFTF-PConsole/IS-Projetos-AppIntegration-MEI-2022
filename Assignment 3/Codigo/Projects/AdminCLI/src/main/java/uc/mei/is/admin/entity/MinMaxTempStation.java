package uc.mei.is.admin.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class  MinMaxTempStation implements Serializable {
  private Integer id;
  private String station;
  private double minimumTemperature;
  private double maximumTemperature;
  private ZonedDateTime createdAt;
}
