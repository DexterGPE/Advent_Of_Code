package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day02 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String[]> lines = fileOpening();
        int total_safe = getTotalSafePart1(lines);
        System.out.println("part 1 solution = " + total_safe);

        int total_safe2 = getTotalSafePart2(lines);
        System.out.println("part 2 solution = " + total_safe2);
    }

    private static int getTotalSafePart2(ArrayList<String[]> lines) {
        int total_safe = 0;

        for (String[] line : lines) {
            String[][] newlines = allPossibleLines(line);
            boolean anySafe = false;
            for (String[] newline : newlines) {
                if (lineIsSafe(newline)) {
                    anySafe = true;
                }
            }
            if (anySafe) {
                total_safe++;
            }
        }
        return total_safe;
    }

    private static String[][] allPossibleLines(String[] line) {
        String[][] result = new String[line.length][];

        for (int i = 0; i < line.length; i++) {

            String[] subArray = new String[line.length - 1];
            int index = 0;
            for (int j = 0; j < line.length; j++) {
                if (j != i) {
                    subArray[index++] = line[j];
                }
            }
            result[i] = subArray;
        }

        return result;
    }


    private static int getTotalSafePart1(ArrayList<String[]> lines) {
        int total_safe = 0;

        for (String[] line : lines) {
            if (lineIsSafe(line)) {
                total_safe++;
            }
        }
        return total_safe;
    }

    public static boolean lineIsSafe(String[] line) {
        if (line.length <= 1) {
            return true;
        }

        boolean[] directionBigger = checkIfItemsAreBiggerThanLastItem(line);

        return isLineSafe(line, directionBigger);
    }

    private static boolean isLineSafe(String[] line, boolean[] directionBigger) {
        return (areAllTrue(directionBigger) || areAllFalse(directionBigger)) && noLargeGaps(line);
    }

    private static boolean[] checkIfItemsAreBiggerThanLastItem(String[] line) {
        int last_item = Integer.parseInt(line[0]);
        boolean[] directionBigger = new boolean[line.length - 1];

        for (int i = 1; i < line.length; i++) {
            int itemInt = Integer.parseInt(line[i]);
            directionBigger[i - 1] = itemInt > last_item;
            last_item = itemInt;
        }
        return directionBigger;
    }

    public static boolean noLargeGaps(String[] line) {
        int last_item = Integer.parseInt(line[0]);

        for (int i = 1; i < line.length; i++) {
            int itemInt = Integer.parseInt(line[i]);
            int difference = Math.abs(itemInt - last_item);
            if (!(difference > 0 && difference < 4)) {
                return false;
            }
            last_item = itemInt;
        }
        return true;
    }

    public static boolean areAllTrue(boolean[] array) {
        for (boolean value : array) {
            if (!value) {
                return false;
            }
        }
        return true;
    }

    public static boolean areAllFalse(boolean[] array) {
        for (boolean value : array) {
            if (value) {
                return false;
            }
        }
        return true;
    }

    public static ArrayList<String[]> fileOpening() throws FileNotFoundException {
        File myObj = new File("2024/input/day02.txt");
        Scanner myReader = new Scanner(myObj);

        ArrayList<String[]> lines = new ArrayList<>();

        while (myReader.hasNextLine()) {
            String[] line = myReader.nextLine().split(" ");
            lines.add(line);
        }

        myReader.close();
        return lines;
    }
}

