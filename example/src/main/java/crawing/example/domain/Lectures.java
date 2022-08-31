package crawing.example.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Lectures {
   @Id
   @GeneratedValue(strategy = AUTO)
   private Long id;
   private String lecture;
   private String professor;
   private String location;
}
