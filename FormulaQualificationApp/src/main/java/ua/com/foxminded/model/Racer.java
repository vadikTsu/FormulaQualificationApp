package ua.com.foxminded.model;

import java.time.Duration;

public class Racer {

    private String teamName;
    private String name;
    private String abbreviation;
    private Duration lapTime;

    public Racer(String abbreviation, String name, String teamName) {
        this.teamName = teamName;
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public Duration getLapTime() {
        return lapTime;
    }

    public void setLapTime(Duration lapTime) {
        this.lapTime = lapTime;
    }
}
