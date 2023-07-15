package uc.mei.is.client.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Professor{
  private Long id;
  private String name;

  @JsonIgnore
  public static final String PATH = "professor";
}
