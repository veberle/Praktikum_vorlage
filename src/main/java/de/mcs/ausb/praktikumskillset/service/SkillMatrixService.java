package de.mcs.ausb.praktikumskillset.service;

import de.mcs.ausb.praktikumskillset.dto.ChartData;
import de.mcs.ausb.praktikumskillset.model.Employee;
import de.mcs.ausb.praktikumskillset.model.EmployeeSkill;
import de.mcs.ausb.praktikumskillset.model.Skill;
import de.mcs.ausb.praktikumskillset.repository.EmployeeRepository;
import de.mcs.ausb.praktikumskillset.repository.EmployeeSkillRepository;
import de.mcs.ausb.praktikumskillset.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SkillMatrixService {

  private final EmployeeRepository employeeRepository;
  private final SkillRepository skillRepository;
  private final EmployeeSkillRepository employeeSkillRepository;

  public SkillMatrixService(EmployeeRepository employeeRepository,
                            SkillRepository skillRepository,
                            EmployeeSkillRepository employeeSkillRepository) {
    this.employeeRepository = employeeRepository;
    this.skillRepository = skillRepository;
    this.employeeSkillRepository = employeeSkillRepository;
  }

  public ChartData buildChartData(List<Long> employeeIds, List<Long> skillIds) {
    ChartData result = new ChartData();
    if (employeeIds == null || employeeIds.isEmpty() || skillIds == null || skillIds.isEmpty()) {
      return result; // empty
    }

    // Fetch skills & preserve order of requested IDs
    List<Skill> skills = skillRepository.findAllById(skillIds);
    Map<Long, Skill> skillById = skills.stream().collect(Collectors.toMap(Skill::getId, s -> s));
    List<Long> filteredSkillIds = skillIds.stream().filter(skillById::containsKey).toList();
    List<String> labels = filteredSkillIds.stream().map(id -> skillById.get(id).getName()).toList();
    result.setLabels(labels);

    // Fetch employees & preserve order
    List<Employee> employees = employeeRepository.findAllById(employeeIds);
    Map<Long, Employee> employeeById = employees.stream().collect(Collectors.toMap(Employee::getId, e -> e));
    List<Long> filteredEmployeeIds = employeeIds.stream().filter(employeeById::containsKey).toList();

    if (filteredEmployeeIds.isEmpty() || filteredSkillIds.isEmpty()) {
      return result;
    }

    // Fetch all relevant EmployeeSkill rows
    List<EmployeeSkill> esList = employeeSkillRepository
        .findByEmployee_IdInAndSkill_IdIn(filteredEmployeeIds, filteredSkillIds);

    // Build map: employeeId -> (skillId -> level)
    Map<Long, Map<Long, Integer>> matrix = new HashMap<>();
    for (EmployeeSkill es : esList) {
      Long eId = es.getEmployee().getId();
      Long sId = es.getSkill().getId();
      matrix.computeIfAbsent(eId, k -> new HashMap<>()).put(sId, es.getLevel());
    }

    // Build datasets per employee, levels default 0 if missing
    List<ChartData.Dataset> datasets = new ArrayList<>();
    for (Long eId : filteredEmployeeIds) {
      Employee e = employeeById.get(eId);
      String label = e.getFirstName() + " " + e.getLastName();
      Map<Long, Integer> row = matrix.getOrDefault(eId, Collections.emptyMap());
      List<Integer> data = new ArrayList<>();
      for (Long sId : filteredSkillIds) {
        data.add(row.getOrDefault(sId, 0));
      }
      datasets.add(new ChartData.Dataset(eId, label, data));
    }

    result.setDatasets(datasets);
    return result;
  }
}
