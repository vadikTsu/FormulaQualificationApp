package ua.com.foxminded.qualification.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.qualification.formatters.RaceFormatter;
import ua.com.foxminded.qualification.model.Racer;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RaceResultsFormatterTest {

    private RaceFormatter raceResultsFormatter;
    private static final String NEW_LINE = System.lineSeparator();

    @BeforeEach
    void setUp() {
        raceResultsFormatter = new RaceFormatter();
    }

    @Test
    void showFirstQualificationResult_shouldReturnFormatedRaceResultOrderedByLapTime_whenValidRacersList() {
        Racer racer1 = new Racer("SVF", "Sebastian Vettel", "FERRARI", Duration.ofMinutes(1));
        Racer racer2 = new Racer("DRR", "Daniel Ricciardo", "RED BULL RACING TAG HEUER", Duration.ofMinutes(2));
        Racer racer3 = new Racer("VBM", "Valtteri Bottas", "MERCEDES", Duration.ofMinutes(3));
        racer3.setLapTime(Duration.ofMinutes(3));
        List<Racer> racers = Arrays.asList(racer3, racer1, racer2);

        String expected =
                    "1.  Sebastian Vettel     | FERRARI                       | 1:00.000" + NEW_LINE
                  + NEW_LINE
                  + "2.  Daniel Ricciardo     | RED BULL RACING TAG HEUER     | 2:00.000" + NEW_LINE
                  + NEW_LINE
                  + "3.  Valtteri Bottas      | MERCEDES                      | 3:00.000" + NEW_LINE;
        String actual = raceResultsFormatter.formatFirstQualificationResult(racers);

        assertEquals(expected, actual);
    }
}