package ua.com.foxminded.qualification.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RacerServiceTest {

    private RacerService racerService;
    private final String damagedAbbreviations = "damagedAbbreviations.txt";
    private final String damagedStartLog = "damagedEnd.log";
    private final String damagedEndLog = "damagedStart.log";
    private final String validAbbreviations = "validAbbreviations.txt";
    private final String validStartLog = "validStart.log";
    private final String validEndLog = "validEnd.log";
    private final String duplicateLog = "duplicate.log";

    @BeforeEach
    void setUpQualificaionObject() {
        racerService = new RacerService();
    }

    @Test
    void getRacers_shouldThrowRuntimeException_whenDamagedAbbreviationSourceFile() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> racerService.getRacers(validStartLog, validEndLog, damagedAbbreviations));
        assertEquals(exception.getMessage(), "Invalid abbreviation source!");
    }

    @Test
    void getRacers_shouldThrowFileNotFoundException_whenNotExistingFilePath() throws IOException {
        Exception exception = assertThrows(FileNotFoundException.class,
                () -> racerService.getRacers(validStartLog, validEndLog, "invalid_path"));
        assertEquals(exception.getMessage(), "No such file: path");
    }

    @Test
    void getRacers_shouldThrowRuntimeException_whenDuplicateLogsInFile() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> racerService.getRacers(duplicateLog, validEndLog, validAbbreviations));
        assertEquals(exception.getMessage(), "Duplicate log: " + duplicateLog);
    }

    @Test
    void getRacers_shouldThrowRuntimeException_whenDamagedStartOrEndLogs() throws IOException {
        Exception exception = assertThrows(RuntimeException.class,
                () -> racerService.getRacers(damagedStartLog, damagedEndLog, validAbbreviations));
        assertEquals(exception.getMessage(), "Invalid log source!");
    }
}
