package ua.com.foxminded.qualification;

import ua.com.foxminded.qualification.formatters.RaceFormatter;
import ua.com.foxminded.qualification.services.RacerService;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        RacerService raceService = new RacerService();
        RaceFormatter raceDispaly = new RaceFormatter();
        try {
            System.out.println(raceDispaly
                .formatFirstQualificationResult(raceService.getRacers("start.log", "end.log", "abbreviations.txt")));
        } catch (IOException ioException) {
            System.err.print(ioException.getMessage());
            ioException.printStackTrace();
        }
    }
}
