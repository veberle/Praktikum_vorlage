package de.mcs.ausb.praktikumskillset.controller;

import de.mcs.ausb.praktikumskillset.model.Department;
import de.mcs.ausb.praktikumskillset.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class DepartmentControllerTest {

  DepartmentService service;
  DepartmentController controller;
  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    service = Mockito.mock(DepartmentService.class);
    controller = new DepartmentController(service);

    // Einfache ViewResolver-Konfiguration, damit MockMvc View-Namen auflöst, ohne FreeMarker zu benötigen
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/templates/");
    viewResolver.setSuffix(".ftl");

    mockMvc = MockMvcBuilders.standaloneSetup(controller)
        .setViewResolvers(viewResolver)
        .build();
  }

  @Test
  void list_shouldRenderListViewWithModel() throws Exception {
    Department d = new Department();
    d.setId(1L);
    d.setName("IT");
    when(service.findAll()).thenReturn(List.of(d));

    mockMvc.perform(get("/departments"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("departments"))
        .andExpect(view().name("departments/list"));

    verify(service).findAll();
  }

  @Test
  void save_shouldValidateAndRedirect() throws Exception {
    when(service.save(any(Department.class))).thenAnswer(inv -> inv.getArgument(0));

    mockMvc.perform(post("/departments")
            .param("name", "HR")
            .param("description", "desc"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/departments"));

    verify(service).save(any(Department.class));
  }

  @Test
  void edit_shouldRenderFormWhenFound() throws Exception {
    Department d = new Department();
    d.setId(5L);
    d.setName("Ops");
    when(service.findById(5L)).thenReturn(d);

    mockMvc.perform(get("/departments/5/edit"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("department"))
        .andExpect(view().name("departments/form"));

    verify(service).findById(5L);
  }

  @Test
  void edit_shouldRedirectWhenNotFound() throws Exception {
    when(service.findById(7L)).thenReturn(null);

    mockMvc.perform(get("/departments/7/edit"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/departments"))
        .andExpect(flash().attributeExists("msgError"));

    verify(service).findById(7L);
  }

  @Test
  void delete_shouldRedirectWithSuccessMessage() throws Exception {
    doNothing().when(service).deleteById(1L);

    mockMvc.perform(post("/departments/1/delete"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/departments"))
        .andExpect(flash().attributeExists("msgSuccess"));

    verify(service).deleteById(1L);
  }

  @Test
  void delete_shouldRedirectWithErrorMessageWhenServiceThrows() throws Exception {
    doThrow(new IllegalStateException("Fehler")).when(service).deleteById(2L);

    mockMvc.perform(post("/departments/2/delete"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/departments"))
        .andExpect(flash().attributeExists("msgError"));

    verify(service).deleteById(2L);
  }
}
