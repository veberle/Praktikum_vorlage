package de.mcs.ausb.praktikumskillset.controller;

import de.mcs.ausb.praktikumskillset.model.SkillCategory;
import de.mcs.ausb.praktikumskillset.service.SkillCategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/skillcategories")
public class SkillCategoryController {

  private final SkillCategoryService service;

  public SkillCategoryController(SkillCategoryService service) {
    this.service = service;
  }

  @GetMapping
  public String list(Model model) {
    model.addAttribute("categories", service.findAll());
    return "skillcategories/list";
  }

  @GetMapping("/new")
  public String newForm(Model model) {
    model.addAttribute("category", new SkillCategory());
    return "skillcategories/form";
  }

  @GetMapping("/{id}/edit")
  public String edit(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
    var cat = service.findById(id);
    if (cat == null) {
      redirectAttributes.addFlashAttribute("msgError", "Kategorie nicht gefunden.");
      return "redirect:/skillcategories";
    }
    model.addAttribute("category", cat);
    return "skillcategories/form";
  }

  @PostMapping
  public String save(@Valid @ModelAttribute("category") SkillCategory category,
                     BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "skillcategories/form";
    }
    service.save(category);
    return "redirect:/skillcategories";
  }

  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    try {
      service.deleteById(id);
      redirectAttributes.addFlashAttribute("msgSuccess", "Kategorie wurde gelöscht.");
    } catch (IllegalStateException ex) {
      redirectAttributes.addFlashAttribute("msgError", ex.getMessage());
    }
    return "redirect:/skillcategories";
  }
}
