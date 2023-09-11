/*
 * @(#)RacerDao.java
 *
 * This file is a part of the FormulaFox project.
 * It contains the definition of the RacerDao class,
 * which is used to read data from classpath resources.
 *
 * Author: Vadym Tsudenko
 *
 * Date: September 10, 2023
 */
package ua.com.foxminded.qualification.dao;

import ua.com.foxminded.qualification.model.Racer;

import java.io.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

public class RacerDao {

    private static final Pattern LOG_FILE_FORMAT = Pattern.compile("\\w{3}\\d{4}-\\d{2}-\\d{2}_\\d{2}:\\d{2}:\\d{2}\\.\\d{3}");
    private static final Pattern ABBREVIATION_FILE_FORMAT = Pattern.compile("[A-Z]{3}_[A-Za-z\\s]+_[A-Za-z\\s]+");
    private final ClassLoader classLoader = getClass().getClassLoader();

    /**
     * Parses and validates data from abbreviation source to Racer models.
     *
     * @param abbreviationFileName name of file with abbreviations.
     * @return list of {@link Racer} models
     * @throws IOException
     */
    public List<Racer> getAbbreviations(String abbreviationFileName) throws IOException {
        try (InputStream inputStream = classLoader.getResourceAsStream(abbreviationFileName)) {
            if (inputStream == null) throw new FileNotFoundException();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                return reader
                    .lines()
                    .peek(validateAbbreviationSource)
                    .map(setUpPilotProfile)
                    .collect(toList());
            }
        }
    }

    /**
     * Parses and validates log files to strings.
     *
     * @param logFileName name of file with logs.
     * @return list of strings, each string represents abbreviation of racer and time-record.
     * @throws IOException
     */
    public List<String> getLogs(String logFileName) throws IOException {
        try (InputStream inputStream = classLoader.getResourceAsStream(logFileName)) {
            if (inputStream == null) throw new FileNotFoundException("No such file: " + logFileName);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                return reader.lines()
                    .peek(validateLogSource)
                    .collect(toList());
            }
        }
    }

    private final Consumer<String> validateAbbreviationSource = abbreviation -> {
        if (!ABBREVIATION_FILE_FORMAT.matcher(abbreviation).matches()) {
            throw new RuntimeException("Invalid abbreviation source!");
        }
    };

    private final Consumer<String> validateLogSource = log -> {
        if (!LOG_FILE_FORMAT.matcher(log).matches()) {
            throw new RuntimeException("Invalid log source!");
        }
    };

    private final Function<String, Racer> setUpPilotProfile = pilotsProfile -> {
        String[] pilotsProperies = pilotsProfile.split("_");
        return new Racer(pilotsProperies[0], pilotsProperies[1], pilotsProperies[2], null);
    };
}
