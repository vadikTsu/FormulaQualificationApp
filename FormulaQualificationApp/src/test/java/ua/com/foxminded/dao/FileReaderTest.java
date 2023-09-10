package ua.com.foxminded.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class FileReaderTest {

    @Test
    void read_shouldThrowFileNotFoundException_whenNotExistiongFile() {
        String fileName = "non_existent_file.txt";
        Exception exception = assertThrows(FileNotFoundException.class,
                () -> new FileReader().read(fileName));
        assertEquals(exception.getMessage(), "No such file: " + fileName);
    }

    @Test
    void read_shouldReturnNotNullStream_whenNotEmptyFile() throws IOException {
        Stream<String> expected = Stream.of(
                "DRR_Daniel Ricciardo_RED BULL RACING TAG HEUER",
                "SVF_Sebastian Vettel_FERRARI",
                "LHM_Lewis Hamilton_MERCEDES");
        Stream<String> actual = new FileReader().read("validAbbreviations.txt");
        assertEquals(actual.collect(Collectors.toList()), expected.collect(Collectors.toList()));
    }
}
