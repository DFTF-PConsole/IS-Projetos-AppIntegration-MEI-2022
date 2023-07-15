package uc.mei.is.admin.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;

import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class  NumberAlertsType implements Serializable {

  
  private Integer id;
  private String type;
  private double numberOfAlerts;
  private ZonedDateTime createdAt;
}
