package ua.com.foxminded.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Racer {

    private String teamName;
    private String name;
    private String abbreviation;
    private Duration lapTime;

    public Racer(String abbreviation, String name, String teamName, Duration lapTime) {
        this.abbreviation = abbreviation;
        this.name = name;
        this.teamName = teamName;
        this.lapTime = lapTime;
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
