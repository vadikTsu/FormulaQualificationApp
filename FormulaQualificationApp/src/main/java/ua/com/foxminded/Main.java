package ua.com.foxminded;

import ua.com.foxminded.qualification.RaceResultsFormatter;
import ua.com.foxminded.qualification.RacerService;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        RacerService raceService = new RacerService();
        RaceResultsFormatter raceDispaly = new RaceResultsFormatter();
        try {
            System.out.println(raceDispaly
                .formatFirstQualificationResult(raceService.getRacers("start.log", "end.log", "abbreviations.txt")));
        } catch (IOException ioException) {
            System.err.print(ioException.getMessage());
            ioException.printStackTrace();
        }
    }
}
