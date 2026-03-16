package com.pragma.traceability.domain.model;

public class EmployeeEfficiency {
    private Long employeeId;
    private Double averageDurationMinutes;
    private Long totalOrders;

    public EmployeeEfficiency() {
    }

    public EmployeeEfficiency(Long employeeId, Double averageDurationMinutes, Long totalOrders) {
        this.employeeId = employeeId;
        this.averageDurationMinutes = averageDurationMinutes;
        this.totalOrders = totalOrders;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Double getAverageDurationMinutes() {
        return averageDurationMinutes;
    }

    public void setAverageDurationMinutes(Double averageDurationMinutes) {
        this.averageDurationMinutes = averageDurationMinutes;
    }

    public Long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Long totalOrders) {
        this.totalOrders = totalOrders;
    }
}