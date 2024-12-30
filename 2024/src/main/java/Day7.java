package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day7 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<long[]> lines = fileOpening();

        String[] operators = {"add", "multiply"};
        long total1 = getTotalOfSolvableLines(lines, operators);
        System.out.println("Total for part 1 = " + total1);

        String[] operators2 = {"add", "multiply", "concatenation"};
        long total2 = getTotalOfSolvableLines(lines, operators2);
        System.out.println("Total for part 2 = " + total2);
    }

    private static boolean checkIfSolutionPossible(long[] line, String[] operators) {
        long solution = line[0];
        return checkCombination(line, operators, 1, line[1], solution);
    }

    private static boolean checkCombination(long[] line, String[] operators, long index, long currentTotal, long solution) {
        if (index == line.length - 1) {
            return currentTotal == solution;
        }

        for (String operator : operators) {
            long newTotal = applyOperator(currentTotal, line[(int) (index + 1)], operator);
            if (checkCombination(line, operators, index + 1, newTotal, solution)) {
                return true;
            }
        }

        return false;
    }

    private static long applyOperator(long currentTotal, long value, String operator) {
        switch (operator) {
            case "add" -> {
                return currentTotal + value;
            }
            case "multiply" -> {
                return currentTotal * value;
            }
            case "concatenation" -> {
                return Long.parseLong(Long.toString(currentTotal) + value);
            }
        }

        throw new IllegalArgumentException("Unsupported operator: " + operator);
    }

    public static long getTotalOfSolvableLines(ArrayList<long[]> lines, String[] operators) {
        long total = 0;

        for (long[] line : lines) {
            if (checkIfSolutionPossible(line, operators)) {
                total += line[0];
            }
        }
        return total;
    }

    public static ArrayList<long[]> fileOpening() throws FileNotFoundException {
        File myObj = new File("2024/input/day7.txt");
        Scanner myReader = new Scanner(myObj);
        ArrayList<long[]> lines = new ArrayList<>();

        while (myReader.hasNextLine()) {
            String[] readLine = myReader.nextLine().split(" ");
            long[] numbers = new long[readLine.length];
            for (int i = 0; i < readLine.length; i++) {
                String cleanedString = readLine[i].replace(":", "");
                numbers[i] = Long.parseLong(cleanedString);
            }
            lines.add(numbers);
        }

        return lines;
    }
}
