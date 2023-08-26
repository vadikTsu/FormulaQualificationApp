package ua.com.foxminded;

import ua.com.foxminded.qualification.Qualification;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Main {
    public static void main(String[] args) {
        Path logs = Paths.get("src/main/resources/abbreviations.txt");
        Path logs1 = Paths.get("src/main/resources/start.log");

        Path logs2 = Paths.get("src/main/resources/end.log");

        Qualification q = new Qualification();
        q.setPilots(logs);

        q.setPilotsLapTimes(logs1, logs2);
        System.out.println(q.getPilotsLapTimes());
    }
}


