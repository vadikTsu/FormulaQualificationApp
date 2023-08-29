package ua.com.foxminded.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class RaceDataAccessTest {

    @Test
    void testGetTextStream_shouldThrowFileNotFoundException_whenNotExistiongFile() {
        String fileName = "non_existent_file.txt";
        Exception exception = assertThrows(FileNotFoundException.class, 
                () -> RaceDataAccess.getTextStream(fileName));
        assertEquals(exception.getMessage(), "Failed to read the file !");
    }
    
    @Test
    void testGetTextStream_shouldReturnNotNullStream_whenNotEmptyFile() throws IOException {
        String fileName = "validAbbreviations.txt";
        Stream<String> lines = RaceDataAccess.getTextStream(fileName);
        assertNotNull(lines);
    }
}
