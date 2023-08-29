package ua.com.foxminded.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class RaceDataAccess {

    public static Stream<String> getTextStream(String fileName) throws IOException {
        ClassLoader classLoader = RaceDataAccess.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream != null) {
            Stream<String> lines = new BufferedReader(new InputStreamReader(inputStream)).lines();
            return lines;
        } else {
            throw new FileNotFoundException("Failed to read the file !");
        }
    }
}
