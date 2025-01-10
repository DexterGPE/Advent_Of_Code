package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 {

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Integer[][]> machines = machineBehaviour();

        System.out.println("Minimum tokens needed (part 1) = " + calculateMinimalAmountOfTokens(machines, 100L, 0L));
        System.out.println("Minimum tokens needed (part 2) = " + calculateMinimalAmountOfTokens(machines, Long.MAX_VALUE, 10000000000000L));
    }

    private static long calculateMinimalAmountOfTokens(ArrayList<Integer[][]> machines, Long maxTries, long addedPosition) {
        long[] buttonPressesNeeded = calculateAmountOfButtonPresses(machines, maxTries, addedPosition);
        return buttonPressesNeeded[0] * 3 + buttonPressesNeeded[1];
    }

    private static long[] calculateAmountOfButtonPresses(ArrayList<Integer[][]> machines, Long maxTries, long addedPosition) {
        long totalButtonA = 0L;
        long totalButtonB = 0L;

        maxTries = (maxTries == -1) ? Integer.MAX_VALUE : maxTries;

        for (Integer[][] machine : machines) {
            double xAnswer = machine[2][0] + addedPosition;
            double yAnswer = machine[2][1] + addedPosition;
            double ax = machine[0][0];
            double bx = machine[1][0];
            double ay = machine[0][1];
            double by = machine[1][1];

            double determinant = (ax * by - ay * bx);
            if (determinant == 0) {
                continue;
            }

            long buttonAPresses = (long) Math.ceil((xAnswer * by - yAnswer * bx) / determinant);
            long buttonBPresses = (long) Math.ceil((yAnswer - buttonAPresses * ay) / by);

            if (buttonAPresses < 0 || buttonBPresses < 0) {
                continue;
            }

            double xCheck = buttonAPresses * ax + buttonBPresses * bx;
            double yCheck = buttonAPresses * ay + buttonBPresses * by;

            if (Math.abs(xCheck - xAnswer) < 1e-6 && Math.abs(yCheck - yAnswer) < 1e-6) {
                if (buttonAPresses <= maxTries && buttonBPresses <= maxTries) {
                    totalButtonA += buttonAPresses;
                    totalButtonB += buttonBPresses;
                }
            }
        }

        return new long[]{totalButtonA, totalButtonB};
    }


    public static ArrayList<Integer[][]> machineBehaviour() throws FileNotFoundException {
        String BUTTON_REGEX = "X\\+(\\d+),\\s*Y\\+(\\d+)";
        String PRIZE_REGEX = "X=(\\d+),\\s*Y=(\\d+)";

        ArrayList<Integer[][]> machines = new ArrayList<>();
        Integer[][] rules = new Integer[3][2];

        try (Scanner scanner = new Scanner(new File("2024/input/day13.txt"))) {
            int buttonCount = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                String[] lineParts = line.split(" ");
                if (lineParts.length == 0) continue;
                if ("Button".equals(lineParts[0])) {
                    Matcher matcher = Pattern.compile(BUTTON_REGEX).matcher(line);
                    if (matcher.find())
                        rules[buttonCount++] = new Integer[]{Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))};
                } else if ("Prize:".equals(lineParts[0])) {
                    Matcher matcher = Pattern.compile(PRIZE_REGEX).matcher(line);
                    if (matcher.find()) {
                        rules[2] = new Integer[]{Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))};
                        machines.add(rules.clone());
                    }
                    buttonCount = 0;
                }
            }
        }
        return machines;
    }

}
