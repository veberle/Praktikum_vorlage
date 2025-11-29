package de.mcs.ausb.praktikumskillset.controller;

import de.mcs.ausb.praktikumskillset.model.Department;
import de.mcs.ausb.praktikumskillset.service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

  private final DepartmentService service;

  public DepartmentController(DepartmentService service) {
    this.service = service;
  }

  @GetMapping
  public String list(Model model) {
    model.addAttribute("departments", service.findAll());
    return "departments/list";
  }

  @GetMapping("/new")
  public String newForm(Model model) {
    model.addAttribute("department", new Department());
    return "departments/form";
  }

  @GetMapping("/{id}/edit")
  public String edit(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
    var dep = service.findById(id);
    if (dep == null) {
      redirectAttributes.addFlashAttribute("msgError", "Abteilung nicht gefunden.");
      return "redirect:/departments";
    }
    model.addAttribute("department", dep);
    return "departments/form";
  }

  @PostMapping
  public String save(@Valid @ModelAttribute("department") Department department,
                     BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "departments/form";
    }
    service.save(department);
    return "redirect:/departments";
  }

  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    try {
      service.deleteById(id);
      redirectAttributes.addFlashAttribute("msgSuccess", "Abteilung wurde gelöscht.");
    } catch (IllegalStateException ex) {
      redirectAttributes.addFlashAttribute("msgError", ex.getMessage());
    }
    return "redirect:/departments";
  }
}
