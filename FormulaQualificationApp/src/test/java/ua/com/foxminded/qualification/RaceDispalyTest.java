package ua.com.foxminded.qualification;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.model.Racer;

class RaceDispalyTest {

    private RaceDispaly raceDispaly;
    private static final String NEW_LINE = System.lineSeparator();
    List<Racer> racers;

    @BeforeEach
    void setUp() {
        raceDispaly = new RaceDispaly();
    }

    @Test
    void showFirstQualificationResultTest_shouldReturnFormatedRaceResultOrderedByLapTime_whenValidRacersList() {
        racers = Arrays.asList(
                new Racer("SVF", "Sebastian Vettel", "FERRARI"),
                new Racer("DRR", "Daniel Ricciardo", "RED BULL RACING TAG HEUER"),
                new Racer("VBM", "Valtteri Bottas", "MERCEDES"),
                new Racer("SVM", "Stoffel Vandoorne", "MCLAREN RENAULT"));
        racers.forEach(racer -> {
            racer.setLapTime("1:22:0" + (racers.size() - racers.indexOf(racer)+1));
        });
        
        String expected = 
                  "1.  SVM      Stoffel Vandoorne    MCLAREN RENAULT           1:22:02"+NEW_LINE
                + "2.  VBM      Valtteri Bottas      MERCEDES                  1:22:03"+NEW_LINE
                + "3.  DRR      Daniel Ricciardo     RED BULL RACING TAG HEUER 1:22:04"+NEW_LINE
                + "4.  SVF      Sebastian Vettel     FERRARI                   1:22:05"+NEW_LINE;
        String actual = raceDispaly.showFirstQualificationResult(racers);
        
        Assertions.assertEquals(expected, actual);
    }


}
