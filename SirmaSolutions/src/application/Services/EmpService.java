package application.Services;

import java.util.List;

import application.model.EmpRecord;
import application.model.Team;

public interface EmpService {

    void addEmployeeRecords(List<EmpRecord> records);

    List<Team> findAllTeamsWithOverlap();
}
