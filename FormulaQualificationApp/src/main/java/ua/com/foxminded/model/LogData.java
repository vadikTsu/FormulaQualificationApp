package ua.com.foxminded.model;

public class LogData {

    private String abbreviation;
    private String lapTimeLog;

    public LogData(String abbreviation, String limeLog) {
        this.abbreviation = abbreviation;
        this.lapTimeLog = limeLog;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getLapTimeLog() {
        return lapTimeLog;
    }
}
