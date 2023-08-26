package ua.com.foxminded.model;

public class Pilot {

    private String teamName;
    private String racerName;
    private String abbreviation;

    public Pilot(String abbreviation, String racerName, String teamName) {
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

    @Override
    public String toString() {
        return String.format("%s|%s|%s", getAbbreviation(), getRacerName(), getTeamName());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pilot other = (Pilot) obj;
        if (getAbbreviation() != other.getAbbreviation())
            return false;
        return true;
    }
}
