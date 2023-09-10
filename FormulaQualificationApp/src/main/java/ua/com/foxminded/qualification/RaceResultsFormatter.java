/*
 * @(#)RaceResultsFormatter.java
 *
 * This file is a part of the FormulaFox project.
 * It contains the definition of the RaceResultsFormatter class,
 * which is used format race data content.
 *
 * Author: Vadym Tsudenko
 *
 * Date: September 10, 2023
 */
package ua.com.foxminded.qualification;

import ua.com.foxminded.model.Racer;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RaceResultsFormatter {

    private static final String NEW_LINE = System.lineSeparator();

    /**
     * Formats list of racers to string. Keeps lines ordered by lap time of each racer.
     * After fifteenth racer separation line added to fix first qualification session results.
     *
     * @param racers list of {@link Racer} objects.
     * @return formated string
     */
    public String formatFirstQualificationResult(List<Racer> racers) {
        int maxNameLength = racers.stream().mapToInt(racer -> racer.getName().length()).max().orElse(0);
        int maxTeamNameLength = racers.stream().mapToInt(racer -> racer.getTeamName().length()).max().orElse(0);
        return racers.stream().sorted(Comparator.comparing(Racer::getLapTime))
            .map(racer -> {
            int index = racers.indexOf(racer) + 1;
            String line = String.format("%-4s%-" + (maxNameLength + 4) + "s | %-" + (maxTeamNameLength + 4) + "s | %s",
                    index + ".", racer.getName(), racer.getTeamName(), formatDuration(racer.getLapTime()))
                    .concat(NEW_LINE);
            if (index == 15) {
                line += buildSeparator('-', line.length() - 2);
            }
            return line;
        }).collect(Collectors.joining(NEW_LINE));
    }

    private String buildSeparator(char character, int length) {
        StringBuilder repeated = new StringBuilder();
        for (int i = 0; i < length; i++) {
            repeated.append(character);
        }
        return repeated.toString();
    }

    private String formatDuration(Duration duration) {
        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();
        long milliseconds = duration.minusSeconds(seconds + minutes * 60).toMillis();
        return String.format("%d:%02d.%03d", minutes, seconds, milliseconds);
    }
}
