package application.core;

import static application.constants.AppConstants.FILE_PATH;
import static application.constants.AppConstants.TEAM_LONGEST_PERIOD;
import static application.constants.AppConstants.EMPTY_COLLECTION_SIZE;
import static application.constants.AppConstants.FILE_PATH;
import static application.constants.AppConstants.INDEX_ZERO;
import static application.constants.AppConstants.NO_TEAMS_MSG;

import java.util.List;
import java.util.stream.Collectors;

import application.model.EmpRecord;
import application.core.interfaces.Runnable;
import application.factories.RecordFactory;
import application.Services.EmpService;
import application.io.FileIO;
import application.io.Writer;
import application.model.EmpRecord;
import application.model.Team;

public class Engine implements Runnable {


    private FileIO fileIO;
    private Writer writer;
    private EmpService emplService;

    public Engine(FileIO fileIO, Writer writer, EmpService emplService) {
        this.fileIO = fileIO;
        this.writer = writer;
        this.emplService = emplService;
    }

    @Override
    public void run() {
        //Read all employee records data from .txt file
    	List<EmpRecord> records = this.fileIO.read(FILE_PATH)
                .stream()
                .map(RecordFactory::execute)
                .collect(Collectors.toList());
        //Save all employee records into "database"
        this.emplService.addEmployeeRecords(records);
        //Find all team, couple of employees which have worked under same project and have overlap
        List<Team> teams = this.emplService.findAllTeamsWithOverlap();
        printResult(teams);
    }

    /**
     * If don't have couple of employees which have worked together under same project
     * will be printed message with text "Doesn't exist teams", otherwise
     * will be find and print the team with best overlap under their joint projects.
     **/
    private void printResult(List<Team> teams) {
        if (teams.size() != EMPTY_COLLECTION_SIZE) {
            teams.sort((team1, team2) ->
                    (int) (team2.getTotalDuration() - team1.getTotalDuration()));
            Team bestTeam = teams.get(INDEX_ZERO);

            this.writer.write(
                    String.format(TEAM_LONGEST_PERIOD,
                            bestTeam.getFirstEmployeeId(),
                            bestTeam.getSecondEmployeeId(),
                            bestTeam.getTotalDuration()));
        } else {
            this.writer.write(NO_TEAMS_MSG);
        }
    }

}
