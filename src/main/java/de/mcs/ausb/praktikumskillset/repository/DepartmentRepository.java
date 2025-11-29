package de.mcs.ausb.praktikumskillset.repository;

import de.mcs.ausb.praktikumskillset.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
