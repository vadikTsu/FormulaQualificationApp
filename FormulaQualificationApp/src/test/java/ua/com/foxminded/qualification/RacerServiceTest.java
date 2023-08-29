package ua.com.foxminded.qualification;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RacerServiceTest {
    
    private RacerService racerService;
    private String damagedAbbreviations = "damagedAbbreviations.txt";
    private String damagedStartLog = "damagedEnd.log";
    private String damagedEndLog = "damagedStart.log";
    private String validAbbreviations = "validAbbreviations.txt";
    private String validStartLog = "validStart.log"; 
    private String validEndLog = "validEnd.log";

    
    @BeforeEach
    void setUpQualificaionObject() {
        racerService = new RacerService();
    }
    
    @Test
    void getRacersTest_shouldThrowRuntimeException_whenDamagedAbbreviationSourceFile() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> racerService.getRacers(validStartLog,validEndLog,damagedAbbreviations));
        assertEquals(exception.getMessage(), "Invalid abbreviation source!");
    }

    @Test
    void getRacersTest_shouldThrowFileNotFoundException_whenNotExistingFilePath() throws IOException {
        Exception exception = assertThrows(FileNotFoundException.class,
                () -> racerService.getRacers("invalid", "file", "path"));
        assertEquals(exception.getMessage(), "Failed to read the file !");
    }


    
    @Test
    void getRacersTest_shouldThrowRuntimeException_whenDamagedStartOrEndLogs() throws IOException{
 
        Exception exception = assertThrows(RuntimeException.class,
                () -> racerService.getRacers(damagedStartLog, damagedEndLog, validAbbreviations));
        assertEquals(exception.getMessage(), "Invalid log source!");
    }
}
