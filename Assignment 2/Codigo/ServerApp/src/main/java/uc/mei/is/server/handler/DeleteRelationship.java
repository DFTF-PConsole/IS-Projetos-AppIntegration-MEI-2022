package uc.mei.is.server.handler;

import lombok.Data;

@Data
public class DeleteRelationship {
  private Long professor_id;
  private Long student_id;
}
