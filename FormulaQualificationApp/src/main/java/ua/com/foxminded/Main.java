package ua.com.foxminded;

import ua.com.foxminded.qualification.Qualification;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path logs = Paths.get("src/main/resources/abbreviations.txt");
        Path logs1 = Paths.get("src/main/resources/start.log");
        Path logs2 = Paths.get("src/main/resources/end.log");
        Qualification q = new Qualification();
        try {
            q.setPilots(logs);
            q.setPilotsLapTimes(logs1, logs2);
            System.out.println(q.showPilotsLapTimes());
        } catch (IOException ioException) {
            System.err.print(ioException.getMessage());
            ioException.printStackTrace();
        } catch (RuntimeException runtimeException) {
            System.err.print(runtimeException.getMessage());
            runtimeException.printStackTrace();
        }
    }
}

