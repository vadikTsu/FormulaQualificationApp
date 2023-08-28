package ua.com.foxminded.qualification;

import ua.com.foxminded.model.LogData;
import ua.com.foxminded.model.Pilot;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toMap;

public class Qualification {

    private List<Pilot> pilots;
    private Map<Pilot, String> pilotsLapTimes;

    private Function<String, Pilot> setUpPilotProfile = pilotsProfile -> {
        String[] pilotsProperies = pilotsProfile.split("_");
        return new Pilot(pilotsProperies[0], pilotsProperies[1], pilotsProperies[2]);
    };

    private Function<LogData, Pilot> findPilotByLogData = pilotsLog -> {
        return pilots.stream().filter(pilot -> pilot.getAbbreviation().equals(pilotsLog.getAbbreviation())).findAny()
                .orElseThrow(() -> new RuntimeException("Lost logs or abbreviations data"));
    };

    private Function<String, LogData> parseToLogData = logsData -> new LogData(logsData.substring(0, 3),
            logsData.substring(3));

    private BiFunction<LocalDateTime, LocalDateTime, String> findLapTimeFromStartEndLogData = 
            (limeLogStart, timeLogEnd) -> {
                Duration duration = Duration.between(limeLogStart, timeLogEnd).abs();
                return String.format("%02d:%02d.%03d%n", duration.toMinutes() % 60, duration.getSeconds() % 60,
                        duration.toMillis() % 1000);
    };

    /**
     * Parse file with abbreviations to set List<Pilot> pilots.
     *
     * @param pilotAbbreviatsionsSource
     */
    public void setPilots(Path pilotAbbreviatsionsSource) throws IOException {
        try (Stream<String> abbreviations = Files.lines(pilotAbbreviatsionsSource)) {
            Pattern fileFormatPattern = Pattern.compile("[A-Z]{3}_[A-Za-z\\s]+_[A-Za-z\\s]+");
            pilots = abbreviations.map(abbreviation -> {
                if (fileFormatPattern.matcher(abbreviation).matches()) {
                    return abbreviation;
                } else {
                    throw new RuntimeException("Invalid abbreviation source!");
                }
            }).map(setUpPilotProfile).collect(Collectors.toList());
        }
    }

    /**
     * Parse start log and end log files to set pilotsLapTimes ordered by pilot`s lap
     * time.
     *
     * @throws {@link RuntimeException}
     * @param startLog
     * @param endLog
     */
    public void setPilotsLapTimes(Path startLog, Path endLog) throws IOException {
        try (Stream<String> startData = Files.lines(startLog); Stream<String> endData = Files.lines(endLog)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");
            Pattern fileFormatPattern = Pattern.compile("\\w{3}\\d{4}-\\d{2}-\\d{2}_\\d{2}:\\d{2}:\\d{2}\\.\\d{3}");
            pilotsLapTimes = Stream.concat(startData, endData).map(log -> {
                if (fileFormatPattern.matcher(log).matches()) {
                    return log;
                } else {
                    throw new RuntimeException("Invalid log source!");
                }
            }).map(parseToLogData)
                    .collect(toMap(findPilotByLogData, LogData::getLapTimeLog,
                            (timeLogStart, timeLogEnd) -> findLapTimeFromStartEndLogData.apply(
                                    LocalDateTime.parse(timeLogStart, dateTimeFormatter),
                                    LocalDateTime.parse(timeLogEnd, dateTimeFormatter))))
                    .entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(toMap(Map.Entry::getKey,
                            Map.Entry::getValue, 
                            (existingValue, newValue) -> existingValue, LinkedHashMap::new));
        }
    }

    /**
     * Modifies pilotsLapTimes to display the results of the first qualification
     * session
     * 
     * @return String with sorted and numbered pilots lap times.
     */
    public String showPilotsLapTimes() {
        List<Map.Entry<Pilot, String>> entries = new ArrayList<>(pilotsLapTimes.entrySet());
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.lineSeparator();
        IntStream.range(0, entries.size()).forEach(i -> {
            if (i == 15) {
                result.append("============================================================" + NEW_LINE);
            }
            result.append(i + 1).append(". ").append(entries.get(i).getKey()).append("|")
                    .append(entries.get(i).getValue()).append(NEW_LINE);
        });
        return result.toString();
    }
}
