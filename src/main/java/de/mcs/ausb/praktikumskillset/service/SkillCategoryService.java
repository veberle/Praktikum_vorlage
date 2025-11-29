package de.mcs.ausb.praktikumskillset.service;

import de.mcs.ausb.praktikumskillset.model.SkillCategory;
import de.mcs.ausb.praktikumskillset.repository.SkillCategoryRepository;
import de.mcs.ausb.praktikumskillset.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillCategoryService {

  private final SkillCategoryRepository repository;
  private final SkillRepository skillRepository;

  public SkillCategoryService(SkillCategoryRepository repository, SkillRepository skillRepository) {
    this.repository = repository;
    this.skillRepository = skillRepository;
  }

  public List<SkillCategory> findAll() {
    return repository.findAll();
  }

  public SkillCategory findById(Long id) {
    return repository.findById(id).orElse(null);
  }

  public SkillCategory save(SkillCategory skillCategory) {
    return repository.save(skillCategory);
  }

  public void deleteById(Long id) {
    if (skillRepository.existsBySkillCategory_Id(id)) {
      throw new IllegalStateException("Kategorie kann nicht gelöscht werden: Es sind noch Skills zugeordnet.");
    }
    repository.deleteById(id);
  }
}
