package application.Services;

import static application.constants.AppConstants.DEFAULT_OVERLAP_ZERO_DAYS;
import static application.constants.AppConstants.INDEX_ZERO;
import static application.constants.AppConstants.ONE;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import application.model.EmpRecord;
import application.factories.TeamFactory;
import application.model.EmpRecord;
import application.model.Team;
import application.Repo.EmpRepo;

public class EmpServiceImple implements EmpService {

    private EmpRepo emplRepo;

    public EmpServiceImple(EmpRepo employeeRepository) {
        this.emplRepo = employeeRepository;
    }

    /** Method which save all records to the database using EmployeeRepository */
    @Override
 
    public void addEmployeeRecords(List<EmpRecord> records) {
        this.emplRepo.saveAll(records);
    }

    /** Method which finds all teams,
     * couples which have overlap and save them into List<Team> */
    @Override
    public List<Team> findAllTeamsWithOverlap() {
        List<EmpRecord> allRecords = this.emplRepo.getAllRecords();

        List<Team> teams = new ArrayList<>();
        for (int i = INDEX_ZERO; i < allRecords.size() - ONE; i++) {
            for (int j = i + ONE; j < allRecords.size(); j++) {
                EmpRecord firstEmpl = allRecords.get(i);
                EmpRecord secondEmpl = allRecords.get(j);

                if (firstEmpl.getProjectId() == secondEmpl.getProjectId()
                        && hasOverlap(firstEmpl, secondEmpl)) {
                    long overlapDays = calculateOverlap(firstEmpl, secondEmpl);

                    if (overlapDays > DEFAULT_OVERLAP_ZERO_DAYS) {
                        updateTeamCollection(teams, firstEmpl, secondEmpl, overlapDays);
                    }
                }
            }
        }
        return teams;
    }

    /** Method which calculating the total overlap and returning it */
    private long calculateOverlap(EmpRecord firstEmpl, EmpRecord secondEmpl) {
        LocalDate periodStartDate =
                firstEmpl.getDateFrom().isBefore(secondEmpl.getDateFrom()) ?
                        secondEmpl.getDateFrom() : firstEmpl.getDateFrom();

        LocalDate periodEndDate =
                firstEmpl.getDateTo().isBefore(secondEmpl.getDateTo()) ?
                        firstEmpl.getDateTo() : secondEmpl.getDateTo();

        //This method works good and when we have involved leap years too
        //from 2023-01-01 to 2023-01-15 will return 14days and not 15, which will accept for correct
        return Math.abs(ChronoUnit.DAYS.between(periodStartDate, periodEndDate));
    }

    /** hasOverlap method returning if two employees have overlap */
    private boolean hasOverlap(EmpRecord firstEmpl, EmpRecord secondEmpl) {
        //have overlap if (startA <= endB) and (endA >= startB)
        return (firstEmpl.getDateFrom().isBefore(secondEmpl.getDateTo())
                || firstEmpl.getDateFrom().isEqual(secondEmpl.getDateTo()))
                && (firstEmpl.getDateTo().isAfter(secondEmpl.getDateFrom())
                || firstEmpl.getDateTo().isEqual(secondEmpl.getDateFrom()));
    }

    /** method check and returning if the current team is already present in team collection
     * (worked together under others projects) */
    private boolean isTeamPresent(Team team, long firstEmplId, long secondEmplId) {
        return ( team.getFirstEmployeeId() == firstEmplId
                && team.getSecondEmployeeId() == secondEmplId )
                || ( team.getFirstEmployeeId() == secondEmplId
                && team.getSecondEmployeeId() == firstEmplId );
    }

    /** If the team is already present, it's total overlap duration will be updated with the new value,
     * otherwise will be created and added new team with the current data */
    private void updateTeamCollection(List<Team> teams, EmpRecord firstEmpl, EmpRecord secondEmpl, long overlapDays) {
        AtomicBoolean isPresent = new AtomicBoolean(false);
        //If the team is present -> update team's total overlap
        teams.forEach(team -> {
            if (isTeamPresent(team, firstEmpl.getEmployeeId(), secondEmpl.getEmployeeId())) {
                team.addOverlapDuration(overlapDays);
                isPresent.set(true);
            }
        });
        //If the team isn't present -> create and add new team with the current data
        if (!isPresent.get()) {
            Team newTeam = TeamFactory.execute(
                    firstEmpl.getEmployeeId(),
                    secondEmpl.getEmployeeId(),
                    overlapDays);
            teams.add(newTeam);
        }
    }
}

