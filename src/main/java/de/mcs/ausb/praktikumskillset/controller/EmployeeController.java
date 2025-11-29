package de.mcs.ausb.praktikumskillset.controller;

import de.mcs.ausb.praktikumskillset.model.Employee;
import de.mcs.ausb.praktikumskillset.service.DepartmentService;
import de.mcs.ausb.praktikumskillset.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

  private final EmployeeService employeeService;
  private final DepartmentService departmentService;

  public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
    this.employeeService = employeeService;
    this.departmentService = departmentService;
  }

  @GetMapping
  public String list(Model model) {
    model.addAttribute("employees", employeeService.findAll());
    // Für gruppierte Ansicht: Abteilungen mitliefern
    model.addAttribute("departments", departmentService.findAll());
    return "employees/list";
  }

  @GetMapping("/new")
  public String newForm(Model model) {
    model.addAttribute("employee", new Employee());
    model.addAttribute("departments", departmentService.findAll());
    return "employees/form";
  }

  @GetMapping("/{id}/edit")
  public String edit(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
    var emp = employeeService.findById(id);
    if (emp == null) {
      redirectAttributes.addFlashAttribute("msgError", "Mitarbeiter nicht gefunden.");
      return "redirect:/employees";
    }
    model.addAttribute("employee", emp);
    model.addAttribute("departments", departmentService.findAll());
    return "employees/form";
  }

  @PostMapping
  public String save(@Valid @ModelAttribute("employee") Employee employee,
                     BindingResult bindingResult,
                     Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("departments", departmentService.findAll());
      return "employees/form";
    }
    employeeService.save(employee);
    return "redirect:/employees";
  }

  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    try {
      employeeService.deleteById(id);
      redirectAttributes.addFlashAttribute("msgSuccess", "Mitarbeiter wurde gelöscht.");
    } catch (IllegalStateException ex) {
      redirectAttributes.addFlashAttribute("msgError", ex.getMessage());
    }
    return "redirect:/employees";
  }
}
