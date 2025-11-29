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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SkillMatrixControllerTest {

  private MockMvc mockMvc;

  private DepartmentService departmentService;
  private EmployeeService employeeService;
  private SkillCategoryService skillCategoryService;
  private SkillService skillService;
  private SkillMatrixService skillMatrixService;

  @BeforeEach
  void setUp() {
    departmentService = mock(DepartmentService.class);
    employeeService = mock(EmployeeService.class);
    skillCategoryService = mock(SkillCategoryService.class);
    skillService = mock(SkillService.class);
    skillMatrixService = mock(SkillMatrixService.class);

    // Sample data
    Department it = new Department(); it.setId(1L); it.setName("IT");
    Department hr = new Department(); hr.setId(2L); hr.setName("HR");
    when(departmentService.findAll()).thenReturn(List.of(it, hr));

    Employee alice = new Employee(); alice.setId(10L); alice.setFirstName("Alice"); alice.setLastName("A"); alice.setDepartment(it);
    Employee bob = new Employee(); bob.setId(11L); bob.setFirstName("Bob"); bob.setLastName("B"); bob.setDepartment(hr);
    when(employeeService.findAll()).thenReturn(List.of(alice, bob));

    SkillCategory backend = new SkillCategory(); backend.setId(100L); backend.setName("Backend");
    SkillCategory soft = new SkillCategory(); soft.setId(101L); soft.setName("Soft");
    when(skillCategoryService.findAll()).thenReturn(List.of(backend, soft));

    Skill java = new Skill(); java.setId(1000L); java.setName("Java"); java.setSkillCategory(backend);
    Skill teamwork = new Skill(); teamwork.setId(1001L); teamwork.setName("Teamwork"); teamwork.setSkillCategory(soft);
    when(skillService.findAll()).thenReturn(List.of(java, teamwork));

    SkillMatrixController controller = new SkillMatrixController(
        departmentService, employeeService, skillCategoryService, skillService, skillMatrixService
    );

    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/templates/");
    viewResolver.setSuffix(".ftl");

    mockMvc = MockMvcBuilders.standaloneSetup(controller)
        .setViewResolvers(viewResolver)
        .setMessageConverters(new MappingJackson2HttpMessageConverter())
        .build();
  }

  @Test
  void index_rendersViewWithGroups() throws Exception {
    mockMvc.perform(get("/skillmatrix"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("deptGroups"))
        .andExpect(model().attributeExists("categoryGroups"))
        .andExpect(view().name("skillmatrix/index"));
  }

  @Test
  void data_returnsJsonChartData() throws Exception {
    ChartData cd = new ChartData();
    cd.setLabels(List.of("Java"));
    ChartData.Dataset ds = new ChartData.Dataset(10L, "Alice A", List.of(80));
    cd.setDatasets(List.of(ds));
    when(skillMatrixService.buildChartData(anyList(), anyList())).thenReturn(cd);

    mockMvc.perform(get("/skillmatrix/data").param("employeeIds", "10").param("skillIds", "1000"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.labels[0]").value("Java"))
        .andExpect(jsonPath("$.datasets[0].label").value("Alice A"))
        .andExpect(jsonPath("$.datasets[0].data[0]").value(80));
  }
}
