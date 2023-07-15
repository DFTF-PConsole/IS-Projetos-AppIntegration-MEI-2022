package uc.mei.is.server.entity;

import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;

@Data
@Builder
@Table("professor")
public class Professor {
  @Id
  private Long id;
  @Column("name")
  private String name;
}
