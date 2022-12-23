package application.factories;

import java.time.LocalDate;

import application.model.EmpRecord;

public final class RecordFactory {

    private static final String DEFAULT_REGEX_PATTERN = ", ";
    private static final String NULL_STR = "NULL";
    private static final int INDEX_ZERO = 0;
    private static final int INDEX_ONE = 1;
    private static final int INDEX_TWO = 2;
    private static final int INDEX_THREE = 3;

    public RecordFactory() {
    }

    public static EmpRecord execute(String line) {
        String[] recordArgs = line.split(DEFAULT_REGEX_PATTERN);

        long emplID = Long.parseLong(recordArgs[INDEX_ZERO].trim());
        long projectID = Long.parseLong(recordArgs[INDEX_ONE].trim());

        //default, ISO_LOCAL_DATE
        LocalDate dateFrom = LocalDate.parse(recordArgs[INDEX_TWO]);

        LocalDate dateTo;
        //DateTo can be null and hence the below condition to satisfy the null date to current date
        if (recordArgs[INDEX_THREE] == null || NULL_STR.equals(recordArgs[INDEX_THREE])) {
            dateTo = LocalDate.now();
        } else {
            dateTo = LocalDate.parse(recordArgs[INDEX_THREE]);
        }

        return new EmpRecord(
                emplID,
                projectID,
                dateFrom,
                dateTo
        );
    }
}
