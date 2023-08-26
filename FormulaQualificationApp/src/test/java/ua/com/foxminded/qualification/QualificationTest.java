package ua.com.foxminded.qualification;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class QualificationTest {

    private static Path damagedAbbreviations;
    private static Path damagedStartLog;
    private static Path damagedEndLog;

    @BeforeAll
    static void setUpFileSources() {
        damagedAbbreviations = Paths.get("src", "test", "resources", "damagedAbbreviations.txt");
        damagedStartLog = Paths.get("src", "test", "resources", "damagedStart.log");
        damagedEndLog = Paths.get("src", "test", "resources", "damagedEnd.log");
    }

    @Test
    void setPilotsTest_ShouldRetrunTimeException_whenDamagedAbbreviationSourceFile() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> new Qualification().setPilots(damagedAbbreviations));
        assertEquals(exception.getMessage(), "Invalid abbreviation source!");
    }

    @Test
    void setPilotsLapTimeTest_ShouldRetrunTimeException_whendamagesEndLogOrStartLogFiles() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> new Qualification().setPilotsLapTimes(damagedStartLog, damagedEndLog));
        assertEquals(exception.getMessage(), "Invalid log source!");
    }
}
