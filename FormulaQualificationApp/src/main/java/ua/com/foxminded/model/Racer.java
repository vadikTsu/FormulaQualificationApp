/*
 * @(#)Racer.java
 *
 * This file is a part of the Racer project.
 * It contains the definition of the Racer class,
 * which is used as model to transfer data about racers profile and their lap times.
 *
 * Author: Vadym Tsudenko
 *
 * Date: September 10, 2023
 */
package ua.com.foxminded.model;

import java.time.Duration;

public class Racer {

    private final String teamName;
    private final String name;
    private final String abbreviation;
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
