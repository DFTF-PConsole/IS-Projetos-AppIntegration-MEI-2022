package uc.mei.is.client.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Relationship {
  private Long professorId;
  private Long studentId;

  @JsonIgnore
  public static final String PATH = "relationship";
}
