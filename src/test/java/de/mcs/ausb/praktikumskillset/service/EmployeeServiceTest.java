package de.mcs.ausb.praktikumskillset.service;

import de.mcs.ausb.praktikumskillset.model.Employee;
import de.mcs.ausb.praktikumskillset.repository.EmployeeRepository;
import de.mcs.ausb.praktikumskillset.repository.EmployeeSkillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

  @Mock EmployeeRepository employeeRepository;
  @Mock EmployeeSkillRepository employeeSkillRepository;

  @InjectMocks EmployeeService employeeService;

  @Test
  void deleteById_throwsWhenEmployeeHasSkills() {
    when(employeeSkillRepository.existsByEmployee_Id(5L)).thenReturn(true);
    assertThrows(IllegalStateException.class, () -> employeeService.deleteById(5L));
    verify(employeeRepository, never()).deleteById(anyLong());
  }

  @Test
  void deleteById_deletesWhenNoSkills() {
    when(employeeSkillRepository.existsByEmployee_Id(5L)).thenReturn(false);
    employeeService.deleteById(5L);
    verify(employeeRepository).deleteById(5L);
  }
}
