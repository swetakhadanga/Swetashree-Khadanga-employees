package application.model;

import java.time.LocalDate;

public class EmpRecord {

    private Long employeeId;
    private Long projectId;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public EmpRecord(long employeeId, long projectId, LocalDate dateFrom, LocalDate dateTo) {
        this.setEmployeeId(employeeId);
        this.setProjectId(projectId);
        this.setDateFrom(dateFrom);
        this.setDateTo(dateTo);
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    private void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getProjectId() {
        return this.projectId;
    }

    private void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public LocalDate getDateFrom() {
        return this.dateFrom;
    }

    private void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return this.dateTo;
    }

    private void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }
}
