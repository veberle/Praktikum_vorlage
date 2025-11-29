package de.mcs.ausb.praktikumskillset.service;

import de.mcs.ausb.praktikumskillset.model.Department;
import de.mcs.ausb.praktikumskillset.repository.DepartmentRepository;
import de.mcs.ausb.praktikumskillset.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

  private final DepartmentRepository repository;
  private final EmployeeRepository employeeRepository;

  public DepartmentService(DepartmentRepository repository, EmployeeRepository employeeRepository) {
    this.repository = repository;
    this.employeeRepository = employeeRepository;
  }

  public List<Department> findAll() {
    return repository.findAll();
  }

  public Department findById(Long id) {
    return repository.findById(id).orElse(null);
  }

  public Department save(Department department) {
    return repository.save(department);
  }

  public void deleteById(Long id) {
    // Verhindere das Löschen, wenn der Abteilung noch Mitarbeiter zugeordnet sind
    if (employeeRepository.existsByDepartment_Id(id)) {
      throw new IllegalStateException("Abteilung kann nicht gelöscht werden: Es sind noch Mitarbeiter zugeordnet.");
    }
    repository.deleteById(id);
  }
}
