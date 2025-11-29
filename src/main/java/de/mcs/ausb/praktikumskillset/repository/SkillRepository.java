package de.mcs.ausb.praktikumskillset.repository;

import de.mcs.ausb.praktikumskillset.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
  boolean existsBySkillCategory_Id(Long categoryId);
}
