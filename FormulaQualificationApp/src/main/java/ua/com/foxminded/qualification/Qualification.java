package ua.com.foxminded.qualification;

import ua.com.foxminded.model.LogData;
import ua.com.foxminded.model.Pilot;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class Qualification {

    private List<Pilot> pilots;
    private List<String> pilotsLapTimes;

    private Function<String, Pilot> setUpPilotProfile = pilotsProfile -> {
        String[] pilotsProperies = pilotsProfile.split("_");
        return new Pilot(pilotsProperies[0], pilotsProperies[1], pilotsProperies[2]);
    };

    private Function<LogData, Pilot> findPilotByLogData = pilotsLog -> {
        return pilots.stream().filter(pilot -> pilot.getAbbreviation().equals(pilotsLog.getAbbreviation())).findAny()
                .orElse(new Pilot("none", "none", "none"));
    };

    private Function<String, LogData> parseToLogData = logsData -> {
        return new LogData(logsData.substring(0, 3), logsData.substring(3));
    };

    private BiFunction<LocalDateTime, LocalDateTime, String> findeLapTime = (limeLogStart, timeLogEnd) -> {
        Duration duration = Duration.between(limeLogStart, timeLogEnd).abs();
        return String.format("%02d:%02d.%03d%n", duration.toMinutes() % 60, duration.getSeconds() % 60,
                duration.toMillis() % 1000);
    };

    /**
     * Parse file with abbreviations to List<Pilot> pilots.
     *
     * @param pilotAbbreviatsionsSource
     */
    public void setPilots(Path pilotAbbreviatsionsSource) {
        try (Stream<String> abbreviations = Files.lines(pilotAbbreviatsionsSource)) {
            String regex = "[A-Z]{3}_[A-Za-z\\s]+_[A-Za-z\\s]+";
            Pattern pattern = Pattern.compile(regex);
            pilots = abbreviations.map(abbreviation -> {
                if (pattern.matcher(abbreviation).matches()) {
                    return abbreviation;
                } else {
                    throw new RuntimeException("Invalid abbreviation source!");
                }
            }).map(setUpPilotProfile).collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Parse startLog and endLog files to set pilotsLapTimes ordered by pilot lap
     * time.
     *
     * @throws {@link RuntimeException}
     * @param startLog
     * @param endLog
     */
    public void setPilotsLapTimes(Path startLog, Path endLog) {
        try (Stream<String> startData = Files.lines(startLog); Stream<String> endData = Files.lines(endLog)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");
            Pattern pattern = Pattern.compile("\\w{3}\\d{4}-\\d{2}-\\d{2}_\\d{2}:\\d{2}:\\d{2}\\.\\d{3}");

            pilotsLapTimes = Stream.concat(startData, endData).map(log -> {
                if (pattern.matcher(log).matches()) {
                    return log;
                } else {
                    throw new RuntimeException("Invalid log source!");
                }
            }).map(parseToLogData)
                    .collect(toMap(findPilotByLogData, LogData::getLapTimeLog,
                            (timeLogStart, timeLogEnd) -> findeLapTime.apply(
                                    LocalDateTime.parse(timeLogStart, dateTimeFormatter),
                                    LocalDateTime.parse(timeLogEnd, dateTimeFormatter))))
                    .entrySet().stream().sorted(Map.Entry.comparingByValue())
                    .map(entry -> String.format(("%s | %s"), entry.getKey(), entry.getValue())).collect(toList());
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Modifies the list to display the first qualifying session
     * 
     * @return List<String> with sorted and numbered  pilots lap times.
     */
    public List<String> getPilotsLapTimes() {
        return IntStream.range(0, pilotsLapTimes.size())
                .mapToObj(index -> (index == 15 ? "---------------------------------------" + "\n" + (index + 1) + ". "
                        : (index + 1) + ". ") + pilotsLapTimes.get(index))
                .collect(toList());

    }
}
