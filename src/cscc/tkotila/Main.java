package cscc.tkotila;

import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Main {
    private static final String SPLITTER_STRING = " ";
    private static final int NUMBER_OF_DAYS = 5;

    public static void main(String[] args) {
        Scanner userInputScanner = new Scanner(System.in).useDelimiter("\n");
        ArrayList<String> cityNames = new ArrayList<>();
        Map<String, int[]> cities = new HashMap<>();
        String cityNameInput = null;

        while (!Objects.equals(cityNameInput, "END")) {
            System.out.print("Enter a city name: ");
            cityNameInput = userInputScanner.next();
            if (!cityNameInput.equals("END")) cityNames.add(cityNameInput);
        }

        for (String cityName : cityNames) {
            String averageTemperatureInput = queryInput(
                String.format("Enter the average temperatures in %s for the next %d days: ", cityName, NUMBER_OF_DAYS),
                userInputScanner,
                (s) -> parseAverageTemperatureInput(s) != null);

            int[] averageTemperatures = parseAverageTemperatureInput(averageTemperatureInput);
            cities.put(cityName, averageTemperatures);
        }

        cities.forEach((cityName, temperatures) -> System.out.printf(
                "The average temperature in %s is %.1f%n",
                cityName,
                calculateAverage(temperatures))
        );
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private static double calculateAverage(int[] temperatures) {
        return Arrays.stream(temperatures).average().getAsDouble();
    }

    private static int[] parseAverageTemperatureInput(String userInput) {
        String[] splitInput = userInput.split(SPLITTER_STRING);

        if (splitInput.length != NUMBER_OF_DAYS) return null;
        if (!Arrays.stream(splitInput).allMatch(
                (s) -> {
                    try {
                        Integer.parseInt(s);
                    } catch (Exception e) {
                        return false;
                    }
                    return true;
                }
        )) return null;

        return Arrays.stream(splitInput).flatMapToInt((s) -> IntStream.of(Integer.parseInt(s))).toArray();
    }

    private static String queryInput(String query, Scanner userInputScanner, Function<String, Boolean> validator) {
        String userInput = "";

        while (!validator.apply(userInput)) {
            System.out.print(query);
            userInput = userInputScanner.next();
        }

        return userInput;
    }
}
