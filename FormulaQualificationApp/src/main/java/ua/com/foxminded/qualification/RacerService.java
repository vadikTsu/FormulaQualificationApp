package ua.com.foxminded.qualification;

import static java.util.stream.Collectors.toMap;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ua.com.foxminded.dao.RaceDataAccess;
import ua.com.foxminded.model.Racer;

public class RacerService {

    public List<Racer> getRacers(String startLogFileName, String endLogFileName, String abbreviationsFileName)
            throws IOException {
        try (Stream<String> abbreviations = RaceDataAccess.getTextStream(abbreviationsFileName)) {
            List<Racer> racers = abbreviations
                    .map(validateAbbreviationSource)
                    .map(setUpPilotProfile)
                    .collect(Collectors.toList());
            Map<String, String> racersLapTimes = parseLogsToLapTimes(startLogFileName, endLogFileName);
            racers.stream().forEach(racer -> racer.setLapTime(racersLapTimes.get(racer.getAbbreviation())));
            return racers;
        }
    }

    private Function<String, Racer> setUpPilotProfile = pilotsProfile -> {
        String[] pilotsProperies = pilotsProfile.split("_");
        return new Racer(pilotsProperies[0], pilotsProperies[1], pilotsProperies[2]);
    };

    private Function<String, String> validateAbbreviationSource = log -> {
        Pattern fileFormatPattern = Pattern.compile("[A-Z]{3}_[A-Za-z\\s]+_[A-Za-z\\s]+");
        if (fileFormatPattern.matcher(log).matches()) {
            return log;
        } else {
            throw new RuntimeException("Invalid abbreviation source!");
        }
    };

    private Map<String, String> parseLogsToLapTimes(String startLogFileName, String endLogFileName) throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");
        try (Stream<String> startData = RaceDataAccess.getTextStream(startLogFileName);
                Stream<String> endData = RaceDataAccess.getTextStream(endLogFileName)) {
            return Stream.concat(startData, endData).map(validateLogSource)
                    .collect(toMap(log -> log.substring(0, 3), log -> log.substring(3),
                            (timeLogStart, timeLogEnd) -> findLapTimeFromStartEndLogData.apply(
                                    LocalDateTime.parse(timeLogStart, dateTimeFormatter),
                                    LocalDateTime.parse(timeLogEnd, dateTimeFormatter))));
        }
    }

    private Function<String, String> validateLogSource = log -> {
        Pattern fileFormatPattern = Pattern.compile("\\w{3}\\d{4}-\\d{2}-\\d{2}_\\d{2}:\\d{2}:\\d{2}\\.\\d{3}");
        if (fileFormatPattern.matcher(log).matches()) {
            return log;
        } else {
            throw new RuntimeException("Invalid log source!");
        }
    };

    private BiFunction<LocalDateTime, LocalDateTime, String> findLapTimeFromStartEndLogData = (timeLogStart,
            timeLogEnd) -> {
        Duration duration = Duration.between(timeLogStart, timeLogEnd).abs();
        return String.format("%02d:%02d.%03d%n", duration.toMinutes() % 60, duration.getSeconds() % 60,
                duration.toMillis() % 1000);
    };
}
