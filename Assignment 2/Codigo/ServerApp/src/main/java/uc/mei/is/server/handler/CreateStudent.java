package uc.mei.is.server.handler;

import java.time.LocalDate;
import lombok.*;

@Data
public class CreateStudent {
  private Long id;
  private String name;
  private LocalDate birthdate;
  private int credits;
  private double gpa;
}
