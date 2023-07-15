package uc.mei.is.server.entity;

import java.time.LocalDate;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;

@Data
@Builder
@Table("student")
public class Student {
  @Id
  public Long id;
  @Column("name")
  public String name;
  @Column("birthdate")
  public LocalDate birthdate;
  @Column("completed_credits")
  public Integer credits;
  @Column("gpa")
  public Double gpa;
}
