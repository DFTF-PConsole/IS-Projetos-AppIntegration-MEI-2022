package uc.mei.is.server.entity;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;

@Data
@Builder
@Table("professor_student")
public class Relationship {
  @Column("professor_id")
  private Long professorId;
  @Column("student_id")
  private Long studentId;
}
