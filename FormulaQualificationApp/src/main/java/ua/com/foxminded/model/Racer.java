package ua.com.foxminded.model;

public class Racer {

    private String teamName;
    private String racerName;
    private String abbreviation;
    private String lapTime;

    public Racer(String abbreviation, String racerName, String teamName) {
        this.teamName = teamName;
        this.racerName = racerName;
        this.abbreviation = abbreviation;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getRacerName() {
        return racerName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getLapTime() {
        return lapTime;
    }

    public void setLapTime(String lapTime) {
        this.lapTime = lapTime;
    }

    @Override
    public String toString() {
        return String.format("%-8s %-20s %-25s %s", getAbbreviation(), getRacerName(), getTeamName(), getLapTime());
    }
}
