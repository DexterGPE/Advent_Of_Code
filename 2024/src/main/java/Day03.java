package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> matches = fileOpening();

        int total = multiplyFoundMatchesPart1(matches);
        System.out.println("part 1 solution = " + total);

        int total2 = multiplyFoundMatchesPart2(matches);
        System.out.println("part 2 solution = " + total2);

    }

    public static int multiplyFoundMatchesPart1(ArrayList<String> matches) {
        int total = 0;

        for (String match : matches) {
            total = updateTotal(match, total);
        }
        return total;
    }

    private static int updateTotal(String match, int total) {
        int[] items = extractIntegersFromMul(match);
        if (items != null) {
            total += items[0] * items[1];
        }
        return total;
    }

    public static int multiplyFoundMatchesPart2(ArrayList<String> matches) {
        int total = 0;
        boolean enabled = true;

        for (String match : matches) {
            if (match.equals("don't()")) {
                enabled = false;
            } else if (match.equals("do()")) {
                enabled = true;
            } else {
                if (enabled) {
                    total = updateTotal(match, total);
                }
            }
        }
        return total;
    }

    public static int[] extractIntegersFromMul(String input) {
        String pattern = "mul\\((\\d+),(\\d+)\\)";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(input);

        if (matcher.matches()) {
            int firstNumber = Integer.parseInt(matcher.group(1));
            int secondNumber = Integer.parseInt(matcher.group(2));
            return new int[]{firstNumber, secondNumber};
        }

        return null;
    }

    public static ArrayList<String> fileOpening() throws FileNotFoundException {
        File myObj = new File("2024/input/day03.txt");
        Scanner myReader = new Scanner(myObj);

        String pattern = "(mul\\((-?\\d+),(-?\\d+)\\))|do\\(\\)|don't\\(\\)";
        Pattern compiledPattern = Pattern.compile(pattern);
        ArrayList<String> matches = new ArrayList<>();
        while (myReader.hasNextLine()) {
            String line = myReader.nextLine();

            Matcher matcher = compiledPattern.matcher(line);
            while (matcher.find()) {
                String match = matcher.group();
                if (matcher.group(1) != null) {
                    matches.add(matcher.group(1));
                } else {
                    matches.add(match);
                }
            }
        }

        myReader.close();

        return matches;
    }
}
