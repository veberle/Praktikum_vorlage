package de.mcs.ausb.praktikumskillset.service;

import de.mcs.ausb.praktikumskillset.model.Employee;
import de.mcs.ausb.praktikumskillset.repository.EmployeeRepository;
import de.mcs.ausb.praktikumskillset.repository.EmployeeSkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

  private final EmployeeRepository repository;
  private final EmployeeSkillRepository employeeSkillRepository;

  public EmployeeService(EmployeeRepository repository, EmployeeSkillRepository employeeSkillRepository) {
    this.repository = repository;
    this.employeeSkillRepository = employeeSkillRepository;
  }

  public List<Employee> findAll() {
    return repository.findAll();
  }

  public Employee findById(Long id) {
    return repository.findById(id).orElse(null);
  }

  public Employee save(Employee employee) {
    return repository.save(employee);
  }

  public void deleteById(Long id) {
    if (employeeSkillRepository.existsByEmployee_Id(id)) {
      throw new IllegalStateException("Mitarbeiter kann nicht gelöscht werden: Es sind noch Skills zugeordnet.");
    }
    repository.deleteById(id);
  }
}
