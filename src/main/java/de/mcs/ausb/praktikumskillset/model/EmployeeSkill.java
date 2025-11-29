package de.mcs.ausb.praktikumskillset.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EmployeeSkill {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "employee_id")
  @NotNull
  private Employee employee;

  @ManyToOne(optional = false)
  @JoinColumn(name = "skill_id")
  @NotNull
  private Skill skill;

  @Min(1)
  @Max(100)
  private int level;

  private LocalDateTime lastUpdate;

  @PrePersist
  @PreUpdate
  void onUpdate() {
    this.lastUpdate = LocalDateTime.now();
  }
}
