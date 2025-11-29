package de.mcs.ausb.praktikumskillset.service;

import de.mcs.ausb.praktikumskillset.model.Department;
import de.mcs.ausb.praktikumskillset.repository.DepartmentRepository;
import de.mcs.ausb.praktikumskillset.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

  @Mock
  DepartmentRepository departmentRepository;

  @Mock
  EmployeeRepository employeeRepository;

  @InjectMocks
  DepartmentService departmentService;

  Department sample;

  @BeforeEach
  void setUp() {
    sample = new Department();
    sample.setId(1L);
    sample.setName("IT");
    sample.setDescription("Desc");
  }

  @Test
  void findAll_delegatesToRepository() {
    when(departmentRepository.findAll()).thenReturn(List.of(sample));
    var list = departmentService.findAll();
    assertEquals(1, list.size());
    assertEquals("IT", list.getFirst().getName());
    verify(departmentRepository).findAll();
  }

  @Test
  void findById_returnsEntityOrNull() {
    when(departmentRepository.findById(1L)).thenReturn(Optional.of(sample));
    when(departmentRepository.findById(2L)).thenReturn(Optional.empty());

    assertNotNull(departmentService.findById(1L));
    assertNull(departmentService.findById(2L));
  }

  @Test
  void save_persistsEntity() {
    when(departmentRepository.save(sample)).thenReturn(sample);
    var saved = departmentService.save(sample);
    assertEquals("IT", saved.getName());
    verify(departmentRepository).save(sample);
  }

  @Test
  void deleteById_throwsWhenEmployeesExist() {
    when(employeeRepository.existsByDepartment_Id(1L)).thenReturn(true);
    var ex = assertThrows(IllegalStateException.class, () -> departmentService.deleteById(1L));
    assertTrue(ex.getMessage().contains("nicht gelöscht"));
    verify(departmentRepository, never()).deleteById(anyLong());
  }

  @Test
  void deleteById_deletesWhenNoEmployees() {
    when(employeeRepository.existsByDepartment_Id(1L)).thenReturn(false);
    departmentService.deleteById(1L);
    verify(departmentRepository).deleteById(1L);
  }
}
