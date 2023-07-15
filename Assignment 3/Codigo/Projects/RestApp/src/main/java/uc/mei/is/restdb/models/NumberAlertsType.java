package uc.mei.is.restdb.models;

import java.io.Serializable;
import java.time.ZonedDateTime;

import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;

@Data
@Builder
@Table("req6")
public class  NumberAlertsType implements Serializable {
  @Id
  private Integer id;

  @Column("alert_type")
  private String type;
  
  @Column("number_alerts")
  private double numberOfAlerts;

  @Column("created_at")
  private ZonedDateTime createdAt;
}
