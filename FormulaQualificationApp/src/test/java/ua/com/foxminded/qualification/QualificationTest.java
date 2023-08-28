package ua.com.foxminded.qualification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QualificationTest {
    
    private Qualification qualification;
    private static Path damagedAbbreviations;
    private static Path damagedStartLog;
    private static Path damagedEndLog;
    private static Path validAbbreviations;
    private static Path validStartLog; 
    private static Path validEndLog;
    private static Path lostDataAbbreviations;
    private static Path lostDataStartLog;
    private static Path lostDataEndLog;
    private final String NEW_LINE = System.lineSeparator();
    
    @BeforeAll
    static void setUpFileSources() {
        damagedAbbreviations = Paths.get("src", "test", "resources", "damagedAbbreviations.txt");
        damagedStartLog = Paths.get("src", "test", "resources", "damagedStart.log");
        damagedEndLog = Paths.get("src", "test", "resources", "damagedEnd.log");
        
        validAbbreviations = Paths.get("src", "test", "resources", "validAbbreviations.txt");
        validStartLog = Paths.get("src", "test", "resources", "validStart.log");
        validEndLog = Paths.get("src", "test", "resources", "validEnd.log");

        lostDataAbbreviations = Paths.get("src", "test", "resources", "lostDataAbbreviations.txt");
        lostDataStartLog = Paths.get("src", "test", "resources", "lostDataStart.log");
        lostDataEndLog = Paths.get("src", "test", "resources", "lostDataEnd.log");
    }
    
    @BeforeEach
    void setUpQualificaionObject() {
        qualification = new Qualification();
    }

    @Test
    void setPilotsTest_shouldThrowRuntimeException_whenDamagedAbbreviationSourceFile() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> qualification.setPilots(damagedAbbreviations));
        assertEquals(exception.getMessage(), "Invalid abbreviation source!");
    }
    
    @Test
    void setPilotsTest_shouldThrowFileNotFoundException_whenNotExistingFilePath() throws IOException{
        Exception exception = assertThrows(NoSuchFileException.class,
                () -> qualification.setPilots(Paths.get("invalid", "file", "path")));
        assertEquals(exception.getMessage(), "invalid\\file\\path");   
    }

    @Test
    void setPilotsLapTimeTest_shoulThrowdNullPointerException_whenPilotsListIsNull() {
        Exception exception = assertThrows(NullPointerException.class,
                () -> qualification.setPilotsLapTimes(damagedStartLog, damagedEndLog));
        assertEquals(exception.getMessage(), null);
    }
    
    @Test
    void setPilotsLapTimeTest_shouldThrowRuntimeException_whenDamagedStartOrEndLogs() throws IOException{
        Qualification q = new Qualification();
        q.setPilots(validAbbreviations);
        Exception exception = assertThrows(RuntimeException.class,
                () -> q.setPilotsLapTimes(damagedStartLog, damagedEndLog));
        assertEquals(exception.getMessage(), "Invalid log source!");
    }
    
    @Test
    void setPilotsLapTimeTest_shouldThrowRuntimeException_whenLostDataInAbbreviationOrLogSource() throws IOException{
        
        qualification.setPilots(lostDataAbbreviations);
        
        Exception exception = assertThrows(RuntimeException.class,
                () -> qualification.setPilotsLapTimes(lostDataStartLog, lostDataEndLog));
        assertEquals(exception.getMessage(), "Lost logs or abbreviations data");
    }

    @Test
    void showPilotsLapTimes_shouldReturnResultStringSortedByLapTime_whenValidData() throws IOException {
        qualification.setPilots(validAbbreviations);
        qualification.setPilotsLapTimes(validStartLog, validEndLog);
        
        String actual = qualification.showPilotsLapTimes();
        String expected = 
                "1. SVF|Sebastian Vettel|FERRARI|01:04.415"+NEW_LINE
                + NEW_LINE
                + "2. DRR|Daniel Ricciardo|RED BULL RACING TAG HEUER|01:12.013"+NEW_LINE
                + NEW_LINE
                + "3. VBM|Valtteri Bottas|MERCEDES|01:12.434"+ NEW_LINE
                + NEW_LINE
                + "4. LHM|Lewis Hamilton|MERCEDES|01:12.460"+ NEW_LINE
                + NEW_LINE
                + "5. SVM|Stoffel Vandoorne|MCLAREN RENAULT|01:12.463"+ NEW_LINE
                + NEW_LINE
                + "6. SSW|Sergey Sirotkin|WILLIAMS MERCEDES|01:12.585"+ NEW_LINE
                + NEW_LINE
                + "7. KRF|Kimi Raikkonen|FERRARI|01:12.639"+ NEW_LINE
                + NEW_LINE
                + "8. FAM|Fernando Alonso|MCLAREN RENAULT|01:12.657"+ NEW_LINE
                + NEW_LINE
                + "9. CLS|Charles Leclerc|SAUBER FERRARI|01:12.829"+ NEW_LINE
                + NEW_LINE
                + "10. SPF|Sergio Perez|FORCE INDIA MERCEDES|01:12.848"+ NEW_LINE
                + NEW_LINE
                + "11. RGH|Romain Grosjean|HAAS FERRARI|01:12.930" + NEW_LINE
                + NEW_LINE
                + "12. PGS|Pierre Gasly|SCUDERIA TORO ROSSO HONDA|01:12.941" + NEW_LINE
                + NEW_LINE
                + "13. CSR|Carlos Sainz|RENAULT|01:12.950" + NEW_LINE
                + NEW_LINE
                + "14. EOF|Esteban Ocon|FORCE INDIA MERCEDES|01:13.028" + NEW_LINE
                + NEW_LINE
                + "15. NHR|Nico Hulkenberg|RENAULT|01:13.065" + NEW_LINE
                + NEW_LINE
                + "============================================================" 
                + NEW_LINE
                + "16. BHS|Brendon Hartley|SCUDERIA TORO ROSSO HONDA|01:13.179" + NEW_LINE
                + NEW_LINE
                + "17. MES|Marcus Ericsson|SAUBER FERRARI|01:13.265" + NEW_LINE
                + NEW_LINE
                + "18. LSW|Lance Stroll|WILLIAMS MERCEDES|01:13.323" + NEW_LINE
                + NEW_LINE
                + "19. KMH|Kevin Magnussen|HAAS FERRARI|01:13.393" + NEW_LINE
                + NEW_LINE;
        assertEquals(expected, actual);
    }
}
