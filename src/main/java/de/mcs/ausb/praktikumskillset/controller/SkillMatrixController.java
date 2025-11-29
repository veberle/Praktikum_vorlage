package de.mcs.ausb.praktikumskillset.controller;

import de.mcs.ausb.praktikumskillset.dto.ChartData;
import de.mcs.ausb.praktikumskillset.model.Department;
import de.mcs.ausb.praktikumskillset.model.Employee;
import de.mcs.ausb.praktikumskillset.model.Skill;
import de.mcs.ausb.praktikumskillset.model.SkillCategory;
import de.mcs.ausb.praktikumskillset.service.DepartmentService;
import de.mcs.ausb.praktikumskillset.service.EmployeeService;
import de.mcs.ausb.praktikumskillset.service.SkillCategoryService;
import de.mcs.ausb.praktikumskillset.service.SkillMatrixService;
import de.mcs.ausb.praktikumskillset.service.SkillService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/skillmatrix")
public class SkillMatrixController {

  private final DepartmentService departmentService;
  private final EmployeeService employeeService;
  private final SkillCategoryService skillCategoryService;
  private final SkillService skillService;
  private final SkillMatrixService skillMatrixService;

  public SkillMatrixController(DepartmentService departmentService,
      EmployeeService employeeService,
      SkillCategoryService skillCategoryService,
      SkillService skillService,
      SkillMatrixService skillMatrixService) {
    this.departmentService = departmentService;
    this.employeeService = employeeService;
    this.skillCategoryService = skillCategoryService;
    this.skillService = skillService;
    this.skillMatrixService = skillMatrixService;
  }

  @GetMapping
  public String index(Model model) {
    // Linke Seite: Mitarbeiter gruppiert nach Abteilung
    List<Department> departments = departmentService.findAll();
    List<Employee> employees = employeeService.findAll();
    Map<Long, List<Employee>> employeesByDeptId = employees.stream()
                                                           .collect(
                                                               Collectors.groupingBy(e -> e.getDepartment().getId(), LinkedHashMap::new,
                                                                   Collectors.toList()));

    List<DepartmentGroup> deptGroups = new ArrayList<>();
    for (Department d : departments) {
      List<Employee> employeeeList = employeesByDeptId.getOrDefault(d.getId(), Collections.emptyList());
      List<SimpleEmployee> simple = employeeeList.stream()
                                        .map(e -> new SimpleEmployee(e.getId(), e.getFirstName() + " " + e.getLastName(), d.getId()))
                                        .toList();
      deptGroups.add(new DepartmentGroup(d.getId(), d.getName(), simple));
    }

    // Rechte Seite: Skills gruppiert nach Kategorie
    List<SkillCategory> categories = skillCategoryService.findAll();
    List<Skill> skills = skillService.findAll();
    Map<Long, List<Skill>> skillsByCatId = skills.stream()
                                                 .collect(Collectors.groupingBy(s -> s.getSkillCategory().getId(), LinkedHashMap::new,
                                                     Collectors.toList()));
    List<CategoryGroup> categoryGroups = new ArrayList<>();
    for (SkillCategory c : categories) {
      List<Skill> groupSkills = skillsByCatId.getOrDefault(c.getId(), Collections.emptyList());
      List<SimpleSkill> simple = groupSkills.stream()
                                            .map(s -> new SimpleSkill(s.getId(), s.getName(), c.getId()))
                                            .toList();
      categoryGroups.add(new CategoryGroup(c.getId(), c.getName(), simple));
    }

    model.addAttribute("deptGroups", deptGroups);
    model.addAttribute("categoryGroups", categoryGroups);
    model.addAttribute("title", "Skillmatrix");

    return "skillmatrix/index";
  }

  @GetMapping("/data")
  @ResponseBody
  public ChartData chartData(@RequestParam(value = "employeeIds", required = false) List<String> employeeIdsRaw,
      @RequestParam(value = "skillIds", required = false) List<String> skillIdsRaw) {
    List<Long> employeeIds = parseIdParams(employeeIdsRaw);
    List<Long> skillIds = parseIdParams(skillIdsRaw);
    return skillMatrixService.buildChartData(employeeIds, skillIds);
  }

  private static List<Long> parseIdParams(List<String> raw) {
    if (raw == null || raw.isEmpty())
      return Collections.emptyList();
    List<Long> result = new ArrayList<>();
    for (String r : raw) {
      if (r == null || r.isBlank())
        continue;
      String[] parts = r.split(",");
      for (String p : parts) {
        try {
          result.add(Long.parseLong(p.trim()));
        } catch (NumberFormatException ignored) {
        }
      }
    }
    // Duplikate entfernen, Reihenfolge beibehalten
    return new LinkedHashSet<>(result).stream().toList();
  }

  // View Models Gruppierung
  @Getter
  @AllArgsConstructor
  public static class DepartmentGroup {
    private Long id;
    private String name;
    private List<SimpleEmployee> employees;
  }

  @Getter
  @AllArgsConstructor
  public static class SimpleEmployee {
    private Long id;
    private String name;
    private Long departmentId;
  }

  @Getter
  @AllArgsConstructor
  public static class CategoryGroup {
    private Long id;
    private String name;
    private List<SimpleSkill> skills;
  }

  @Getter
  @AllArgsConstructor
  public static class SimpleSkill {
    private Long id;
    private String name;
    private Long categoryId;
  }
}
