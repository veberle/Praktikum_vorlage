package de.mcs.ausb.praktikumskillset.service;

import de.mcs.ausb.praktikumskillset.model.Skill;
import de.mcs.ausb.praktikumskillset.repository.EmployeeSkillRepository;
import de.mcs.ausb.praktikumskillset.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

  private final SkillRepository repository;
  private final EmployeeSkillRepository employeeSkillRepository;

  public SkillService(SkillRepository repository, EmployeeSkillRepository employeeSkillRepository) {
    this.repository = repository;
    this.employeeSkillRepository = employeeSkillRepository;
  }

  public List<Skill> findAll() {
    return repository.findAll();
  }

  public Skill findById(Long id) {
    return repository.findById(id).orElse(null);
  }

  public Skill save(Skill skill) {
    return repository.save(skill);
  }

  public void deleteById(Long id) {
    if (employeeSkillRepository.existsBySkill_Id(id)) {
      throw new IllegalStateException("Skill kann nicht gelöscht werden: Es ist noch einem Mitarbeiter zugeordnet.");
    }
    repository.deleteById(id);
  }
}
