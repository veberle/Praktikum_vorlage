package de.mcs.ausb.praktikumskillset.controller;

import de.mcs.ausb.praktikumskillset.model.Skill;
import de.mcs.ausb.praktikumskillset.service.SkillCategoryService;
import de.mcs.ausb.praktikumskillset.service.SkillService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/skills")
public class SkillController {

  private final SkillService skillService;
  private final SkillCategoryService skillCategoryService;

  public SkillController(SkillService skillService, SkillCategoryService skillCategoryService) {
    this.skillService = skillService;
    this.skillCategoryService = skillCategoryService;
  }

  @GetMapping
  public String list(Model model) {
    model.addAttribute("skills", skillService.findAll());
    // Für gruppierte Ansicht: Kategorien mitliefern
    model.addAttribute("categories", skillCategoryService.findAll());
    return "skills/list";
  }

  @GetMapping("/new")
  public String newForm(Model model) {
    model.addAttribute("skill", new Skill());
    model.addAttribute("categories", skillCategoryService.findAll());
    return "skills/form";
  }

  @GetMapping("/{id}/edit")
  public String edit(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
    var s = skillService.findById(id);
    if (s == null) {
      redirectAttributes.addFlashAttribute("msgError", "Skill nicht gefunden.");
      return "redirect:/skills";
    }
    model.addAttribute("skill", s);
    model.addAttribute("categories", skillCategoryService.findAll());
    return "skills/form";
  }

  @PostMapping
  public String save(@Valid @ModelAttribute("skill") Skill skill,
                     BindingResult bindingResult,
                     Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("categories", skillCategoryService.findAll());
      return "skills/form";
    }
    skillService.save(skill);
    return "redirect:/skills";
  }

  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    try {
      skillService.deleteById(id);
      redirectAttributes.addFlashAttribute("msgSuccess", "Skill wurde gelöscht.");
    } catch (IllegalStateException ex) {
      redirectAttributes.addFlashAttribute("msgError", ex.getMessage());
    }
    return "redirect:/skills";
  }
}
