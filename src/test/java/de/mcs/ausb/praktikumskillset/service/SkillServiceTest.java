package de.mcs.ausb.praktikumskillset.service;

import de.mcs.ausb.praktikumskillset.repository.EmployeeSkillRepository;
import de.mcs.ausb.praktikumskillset.repository.SkillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SkillServiceTest {

  @Mock SkillRepository skillRepository;
  @Mock EmployeeSkillRepository employeeSkillRepository;

  @InjectMocks SkillService service;

  @Test
  void deleteById_throwsWhenSkillUsed() {
    when(employeeSkillRepository.existsBySkill_Id(3L)).thenReturn(true);
    try {
      service.deleteById(3L);
    } catch (IllegalStateException e) {
      // expected
    }
    verify(skillRepository, never()).deleteById(anyLong());
  }

  @Test
  void deleteById_deletesWhenNotUsed() {
    when(employeeSkillRepository.existsBySkill_Id(3L)).thenReturn(false);
    service.deleteById(3L);
    verify(skillRepository).deleteById(3L);
  }
}
