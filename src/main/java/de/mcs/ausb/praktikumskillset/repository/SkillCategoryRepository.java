package de.mcs.ausb.praktikumskillset.repository;

import de.mcs.ausb.praktikumskillset.model.SkillCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillCategoryRepository extends JpaRepository<SkillCategory, Long> {
}
