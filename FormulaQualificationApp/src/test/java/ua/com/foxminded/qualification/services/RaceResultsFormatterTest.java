package ua.com.foxminded.qualification.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.qualification.formatters.RaceFormatter;
import ua.com.foxminded.qualification.model.Racer;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RaceResultsFormatterTest {

    @Mock
    private RacerService racerService;
    private RaceFormatter raceResultsFormatter;
    private static final String NEW_LINE = System.lineSeparator();

    @BeforeEach
    void setUp() {
        raceResultsFormatter = new RaceFormatter();
    }

    @Test
    void showFirstQualificationResult_shouldReturnFormatedRaceResultOrderedByLapTime_whenValidRacersList() throws IOException {
        Racer racer1 = new Racer("SVF", "Sebastian Vettel", "FERRARI", Duration.ofMinutes(1));
        Racer racer2 = new Racer("DRR", "Daniel Ricciardo", "RED BULL RACING TAG HEUER", Duration.ofMinutes(2));
        Racer racer3 = new Racer("VBM", "Valtteri Bottas", "MERCEDES", Duration.ofMinutes(3));
        given(racerService.getRacers("startLog_path", "endLog_path", "abbreviations_path"))
            .willReturn(Arrays.asList(racer1, racer2, racer3));

        String expected =
            "1.  Sebastian Vettel     | FERRARI                       | 1:00.000" + NEW_LINE
                + NEW_LINE
                + "2.  Daniel Ricciardo     | RED BULL RACING TAG HEUER     | 2:00.000" + NEW_LINE
                + NEW_LINE
                + "3.  Valtteri Bottas      | MERCEDES                      | 3:00.000" + NEW_LINE;

        assertEquals(expected, raceResultsFormatter.formatFirstQualificationResult(
            racerService.getRacers("startLog_path", "endLog_path", "abbreviations_path")));
    }
}
