package uc.mei.is.server.handler;

import lombok.*;

@Data
public class CreateRelationship {
  private Long professorId;
  private Long studentId;
}
