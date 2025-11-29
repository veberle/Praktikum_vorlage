package de.mcs.ausb.praktikumskillset.dto;

import java.util.ArrayList;
import java.util.List;

public class ChartData {
  private List<String> labels = new ArrayList<>();
  private List<Dataset> datasets = new ArrayList<>();

  public ChartData() {
  }

  public ChartData(List<String> labels, List<Dataset> datasets) {
    this.labels = labels;
    this.datasets = datasets;
  }

  public List<String> getLabels() {
    return labels;
  }

  public void setLabels(List<String> labels) {
    this.labels = labels;
  }

  public List<Dataset> getDatasets() {
    return datasets;
  }

  public void setDatasets(List<Dataset> datasets) {
    this.datasets = datasets;
  }

  public static class Dataset {
    private Long employeeId;
    private String label;
    private List<Integer> data;

    public Dataset() {}

    public Dataset(Long employeeId, String label, List<Integer> data) {
      this.employeeId = employeeId;
      this.label = label;
      this.data = data;
    }

    public Long getEmployeeId() {
      return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
      this.employeeId = employeeId;
    }

    public String getLabel() {
      return label;
    }

    public void setLabel(String label) {
      this.label = label;
    }

    public List<Integer> getData() {
      return data;
    }

    public void setData(List<Integer> data) {
      this.data = data;
    }
  }
}
