package ua.com.foxminded.qualification;

import static java.util.stream.Collectors.toMap;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ua.com.foxminded.dao.FileReader;
import ua.com.foxminded.model.Racer;

public class RacerService {

    private static final Pattern LOG_FILE_FORMAT = Pattern.compile("\\w{3}\\d{4}-\\d{2}-\\d{2}_\\d{2}:\\d{2}:\\d{2}\\.\\d{3}");
    private static final Pattern ABBREVIATION_FILE_FORMAT = Pattern.compile("[A-Z]{3}_[A-Za-z\\s]+_[A-Za-z\\s]+");
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");

    public List<Racer> getRacers(String startLogFileName, String endLogFileName, String abbreviationsFileName)
        throws IOException {
        try (Stream<String> abbreviations = new FileReader().read(abbreviationsFileName)) {
            List<Racer> racers = abbreviations.peek(validateAbbreviationSource).map(setUpPilotProfile)
                .collect(Collectors.toList());
            Map<String, LocalDateTime> racersStartLogs = parseLogsToLapTimes(startLogFileName);
            Map<String, LocalDateTime> racersEndLogs = parseLogsToLapTimes(endLogFileName);
            racers.forEach(racer -> racer.setLapTime(Duration.between(
                racersStartLogs.get(racer.getAbbreviation()),
                racersEndLogs.get(racer.getAbbreviation()))));
            return racers;
        }
    }

    private final Function<String, Racer> setUpPilotProfile = pilotsProfile -> {
        String[] pilotsProperies = pilotsProfile.split("_");
        return new Racer(pilotsProperies[0], pilotsProperies[1], pilotsProperies[2], null);
    };

    private final Consumer<String> validateAbbreviationSource = abbreviation -> {
        if (!ABBREVIATION_FILE_FORMAT.matcher(abbreviation).matches()) {
            throw new RuntimeException("Invalid abbreviation source!");
        }
    };

    private Map<String, LocalDateTime> parseLogsToLapTimes(String logFileName) throws IOException {
        try (Stream<String> logData = new FileReader().read(logFileName)) {
            return logData.peek(validateLogSource).collect(toMap(log -> log.substring(0, 3),
                log -> LocalDateTime.parse(log.substring(3), DATE_TIME_FORMAT), (existingValue, newValue) -> {
                    throw new RuntimeException("Duplicate log: " + logFileName);
                }));
        }
    }

    private final Consumer<String> validateLogSource = log -> {
        if (!LOG_FILE_FORMAT.matcher(log).matches()) {
            throw new RuntimeException("Invalid log source!");
        }
    };
}
