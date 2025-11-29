package de.mcs.ausb.praktikumskillset.repository;

import de.mcs.ausb.praktikumskillset.model.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkill, Long> {
  boolean existsByEmployee_Id(Long employeeId);
  boolean existsBySkill_Id(Long skillId);
  java.util.List<EmployeeSkill> findByEmployee_IdInAndSkill_IdIn(java.util.Collection<Long> employeeIds,
                                                                 java.util.Collection<Long> skillIds);
}
