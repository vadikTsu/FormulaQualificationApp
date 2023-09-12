package ua.com.foxminded.qualification.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RacerDaoTest {

    private final String damagedAbbreviations = "damagedAbbreviations.txt";
    private final String damagedStartLog = "damagedEnd.log";
    private final String duplicateLog = "duplicate.log";
    private RacerDao racerDao;

    @BeforeEach
    void setup() {
        racerDao = new RacerDao();
    }

    @Test
    void getAbbreviations_shouldThrowFileNotFoundException_whenNotExistingFilePath() throws IOException {
        Exception exception = assertThrows(FileNotFoundException.class,
            () -> racerDao.getAbbreviations("invalid_path"));
        assertEquals("No such file: invalid_path", exception.getMessage());
    }

    @Test
    void getLogs_shouldThrowRuntimeException_whenDuplicateLogsInFile() {
        Exception exception = assertThrows(RuntimeException.class,
            () -> racerDao.getLogs(duplicateLog));
        assertEquals("Duplicate log: " + duplicateLog, exception.getMessage());
    }

    @Test
    void getLogs_shouldThrowRuntimeException_whenDamagedStartOrEndLogs() throws IOException {
        Exception exception = assertThrows(RuntimeException.class,
            () -> racerDao.getLogs(damagedStartLog));
        assertEquals("Invalid log source!", exception.getMessage());
    }

    @Test
    void getAbbreviations_shouldThrowRuntimeException_whenDamagedAbbreviationSourceFile() {
        Exception exception = assertThrows(RuntimeException.class,
            () -> racerDao.getAbbreviations(damagedAbbreviations));
        assertEquals("Invalid abbreviation source!", exception.getMessage());
    }
}
