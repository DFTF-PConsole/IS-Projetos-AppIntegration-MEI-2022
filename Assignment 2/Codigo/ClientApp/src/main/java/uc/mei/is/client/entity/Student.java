package uc.mei.is.client.entity;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student{
  private Long id;
  private String name;
  private LocalDate birthdate;
  private Integer credits;
  private Double gpa;
  @JsonIgnore
  public static final String PATH = "student";
}
