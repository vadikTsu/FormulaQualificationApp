package ua.com.foxminded.qualification;

import java.util.List;

import ua.com.foxminded.model.Racer;

public class RaceDispaly  {

    public String showFirstQualificationResult(List<Racer> racers) {
        StringBuilder result = new StringBuilder();
        String sep = "-----------------------------" + System.lineSeparator();
        racers.sort((racer1, racer2) -> racer1.getLapTime().compareTo(racer2.getLapTime()));
        racers.forEach(racer -> {
            int index = racers.indexOf(racer) + 1; 
            result.append(String.format("%-4s", index + "."))
            .append(racer)
            .append(System.lineSeparator());
            if (index == 15) {result.append(sep);}
        });
        return result.toString();
    }
}
