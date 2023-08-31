package ua.com.foxminded.qualification;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import ua.com.foxminded.model.Racer;

public class RaceDispaly {

    private static final String NEW_LINE = System.lineSeparator();

    public String showFirstQualificationResult(List<Racer> racers) {
        int maxNameLength = racers.stream().mapToInt(racer -> racer.getName().length()).max().orElse(0);
        int maxTeamNameLength = racers.stream().mapToInt(racer -> racer.getTeamName().length()).max().orElse(0);

        racers.sort(Comparator.comparing(Racer::getLapTime));
        return racers.stream().map(racer -> {
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
