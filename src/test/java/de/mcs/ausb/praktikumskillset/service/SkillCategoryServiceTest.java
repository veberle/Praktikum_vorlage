package de.mcs.ausb.praktikumskillset.service;

import de.mcs.ausb.praktikumskillset.repository.SkillCategoryRepository;
import de.mcs.ausb.praktikumskillset.repository.SkillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SkillCategoryServiceTest {

  @Mock SkillCategoryRepository skillCategoryRepository;
  @Mock SkillRepository skillRepository;

  @InjectMocks SkillCategoryService service;

  @Test
  void deleteById_throwsWhenSkillsExist() {
    when(skillRepository.existsBySkillCategory_Id(10L)).thenReturn(true);
    assertThrows(IllegalStateException.class, () -> service.deleteById(10L));
    verify(skillCategoryRepository, never()).deleteById(anyLong());
  }

  @Test
  void deleteById_deletesWhenNoSkills() {
    when(skillRepository.existsBySkillCategory_Id(10L)).thenReturn(false);
    service.deleteById(10L);
    verify(skillCategoryRepository).deleteById(10L);
  }
}
