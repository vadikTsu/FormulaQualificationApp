/*
 * @(#)RaceService.java
 *
 * This file is a part of the FormulaFox project.
 * It contains the definition of the RaceService class,
 * which is used to parse data from log and abbreviation files to collect race data.
 *
 * Author: Vadym Tsudenko
 *
 * Date: September 10, 2023
 */
package ua.com.foxminded.qualification.services;

import ua.com.foxminded.qualification.dao.RacerDao;
import ua.com.foxminded.qualification.model.Racer;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class RacerService {

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");
    private final RacerDao racerDao;

    public RacerService(RacerDao racerDao) {
        this.racerDao = racerDao;
    }

    /**
     * Collects data from start log, end log, abbreviations
     * to list of racers ordered by their lap time.
     *
     * @param startLogFileName      name of file with start logs.
     * @param endLogFileName        name of file with end logs.
     * @param abbreviationsFileName name of file with abbreviations.
     * @return parsed data to list of {@link Racer}
     */
    public List<Racer> getRacers(String startLogFileName, String endLogFileName, String abbreviationsFileName) throws IOException {
        List<Racer> abbreviations = racerDao.getAbbreviations(abbreviationsFileName);
        Map<String, LocalDateTime> racersStartLogs = parseLogsToLapTimes(startLogFileName);
        Map<String, LocalDateTime> racersEndLogs = parseLogsToLapTimes(endLogFileName);
        return abbreviations.stream()
            .peek(racer -> racer.setLapTime(Duration.between(
                racersStartLogs.get(racer.getAbbreviation()),
                racersEndLogs.get(racer.getAbbreviation()))))
            .sorted(Comparator.comparing(Racer::getLapTime))
            .collect(toList());
    }

    private Map<String, LocalDateTime> parseLogsToLapTimes(String logFileName) throws IOException {
        Stream<String> logData = racerDao.getLogs(logFileName).stream();
        return logData.collect(toMap(log -> log.substring(0, 3),
            log -> LocalDateTime.parse(log.substring(3), DATE_TIME_FORMAT), (existingValue, newValue) -> {
                throw new RuntimeException("Duplicate log: " + logFileName);
            }));

    }
}

