package de.mcs.ausb.praktikumskillset.service;

import de.mcs.ausb.praktikumskillset.model.EmployeeSkill;
import de.mcs.ausb.praktikumskillset.repository.EmployeeSkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeSkillService {

  private final EmployeeSkillRepository repository;

  public EmployeeSkillService(EmployeeSkillRepository repository) {
    this.repository = repository;
  }

  public List<EmployeeSkill> findAll() {
    return repository.findAll();
  }

  public EmployeeSkill findById(Long id) {
    return repository.findById(id).orElse(null);
  }

  public EmployeeSkill save(EmployeeSkill employeeSkill) {
    return repository.save(employeeSkill);
  }

  public void deleteById(Long id) {
    repository.deleteById(id);
  }
}
