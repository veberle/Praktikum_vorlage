package de.mcs.ausb.praktikumskillset.controller;

import de.mcs.ausb.praktikumskillset.model.EmployeeSkill;
import de.mcs.ausb.praktikumskillset.service.DepartmentService;
import de.mcs.ausb.praktikumskillset.service.EmployeeService;
import de.mcs.ausb.praktikumskillset.service.EmployeeSkillService;
import de.mcs.ausb.praktikumskillset.service.SkillService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/employee-skills")
public class EmployeeSkillController {

  private final EmployeeSkillService employeeSkillService;
  private final EmployeeService employeeService;
  private final SkillService skillService;
  private final DepartmentService departmentService;

  public EmployeeSkillController(EmployeeSkillService employeeSkillService,
                                 EmployeeService employeeService,
                                 SkillService skillService,
                                 DepartmentService departmentService) {
    this.employeeSkillService = employeeSkillService;
    this.employeeService = employeeService;
    this.skillService = skillService;
    this.departmentService = departmentService;
  }

  @GetMapping
  public String list(Model model) {
    model.addAttribute("employeeSkills", employeeSkillService.findAll());
    // Für gruppierte Ansicht nach Mitarbeiter zusätzlich alle Mitarbeiter mitgeben
    model.addAttribute("employees", employeeService.findAll());
    // Zweite Gruppierung nach Abteilung
    model.addAttribute("departments", departmentService.findAll());
    return "employee-skills/list";
  }

  @GetMapping("/new")
  public String newForm(Model model) {
    model.addAttribute("employeeSkill", new EmployeeSkill());
    model.addAttribute("employees", employeeService.findAll());
    model.addAttribute("skills", skillService.findAll());
    return "employee-skills/form";
  }

  @GetMapping("/{id}/edit")
  public String edit(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
    var es = employeeSkillService.findById(id);
    if (es == null) {
      redirectAttributes.addFlashAttribute("msgError", "Eintrag nicht gefunden.");
      return "redirect:/employee-skills";
    }
    model.addAttribute("employeeSkill", es);
    model.addAttribute("employees", employeeService.findAll());
    model.addAttribute("skills", skillService.findAll());
    return "employee-skills/form";
  }

  @PostMapping
  public String save(@Valid @ModelAttribute("employeeSkill") EmployeeSkill employeeSkill,
                     BindingResult bindingResult,
                     Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("employees", employeeService.findAll());
      model.addAttribute("skills", skillService.findAll());
      return "employee-skills/form";
    }
    employeeSkillService.save(employeeSkill);
    return "redirect:/employee-skills";
  }

  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    employeeSkillService.deleteById(id);
    redirectAttributes.addFlashAttribute("msgSuccess", "Eintrag wurde gelöscht.");
    return "redirect:/employee-skills";
  }
}
