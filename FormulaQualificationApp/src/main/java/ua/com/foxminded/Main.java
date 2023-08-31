package ua.com.foxminded;

import ua.com.foxminded.qualification.RaceDispaly;
import ua.com.foxminded.qualification.RacerService;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        RacerService raceService = new RacerService();
        RaceDispaly raceDispaly = new RaceDispaly();
        try {
            System.out.println(raceDispaly.showFirstQualificationResult(raceService.getRacers("start.log", "end.log","abbreviations.txt")));
        } catch (IOException ioException) {
            System.err.print(ioException.getMessage());
            ioException.printStackTrace();
        }
    }
}

