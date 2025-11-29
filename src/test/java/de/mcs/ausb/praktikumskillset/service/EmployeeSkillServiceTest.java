package de.mcs.ausb.praktikumskillset.service;

import de.mcs.ausb.praktikumskillset.model.EmployeeSkill;
import de.mcs.ausb.praktikumskillset.repository.EmployeeSkillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeSkillServiceTest {

  @Mock EmployeeSkillRepository repository;

  @InjectMocks EmployeeSkillService service;

  @Test
  void save_delegateToRepository() {
    EmployeeSkill es = new EmployeeSkill();
    when(repository.save(es)).thenReturn(es);
    service.save(es);
    verify(repository).save(es);
  }

  @Test
  void delete_delegateToRepository() {
    service.deleteById(7L);
    verify(repository).deleteById(7L);
  }
}
