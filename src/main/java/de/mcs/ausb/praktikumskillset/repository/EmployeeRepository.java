package de.mcs.ausb.praktikumskillset.repository;

import de.mcs.ausb.praktikumskillset.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  boolean existsByDepartment_Id(Long departmentId);
}
