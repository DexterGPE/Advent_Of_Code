package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day04 {
    public static void main(String[] args) throws FileNotFoundException {
        String[][] lines = fileOpening();

        int totalOccurrences = getTotalOccurrencesPart1(lines);
        System.out.println("XMAS counter (part 1) = " + totalOccurrences);

        int totalOccurrences2 = getTotalOccurrencesPart2(lines);
        System.out.println("X - MAS counter (part 2) = " + totalOccurrences2);
    }

    private static int getTotalOccurrencesPart2(String[][] lines) {
        int count = 0;
        for (int i = 0; i < lines.length - 2; i++) {
            for (int j = 0; j < lines[i].length - 2; j++) {
                if ((TopLeftBotRight(lines, i, j, "MAS") || TopLeftBotRight(lines, i, j, "SAM")) &&
                        ((TopRightBotLeft(lines, i, j, "MAS") || TopRightBotLeft(lines, i, j, "SAM")))) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean TopLeftBotRight(String[][] lines, int i, int j, String pattern) {
        char[] charPattern = pattern.toCharArray();

        if (i + 2 < lines.length && j + 2 < lines[i].length) {
            return (lines[i][j].equals(String.valueOf(charPattern[0])) && lines[i + 1][j + 1].equals(String.valueOf(charPattern[1])) && lines[i + 2][j + 2].equals(String.valueOf(charPattern[2])));
        }
        return false;
    }

    private static boolean TopRightBotLeft(String[][] lines, int i, int j, String pattern) {
        char[] charPattern = pattern.toCharArray();

        if (i + 2 < lines[i].length && j + 2 < lines[i].length) {
            return (lines[i][j + 2].equals(String.valueOf(charPattern[0])) && lines[i + 1][j + 1].equals(String.valueOf(charPattern[1])) && lines[i + 2][j].equals(String.valueOf(charPattern[2])));
        }
        return false;
    }

    private static int getTotalOccurrencesPart1(String[][] lines) {
        int totalOccurrences = 0;
        String[] patterns = {"XMAS", "SAMX"};

        for (String pattern : patterns) {
            char[] charPattern = pattern.toCharArray();
            totalOccurrences += diagonalLeftOccurrences(lines, charPattern);
            totalOccurrences += diagonalRightOccurrences(lines, charPattern);

            totalOccurrences += horizontalOccurrences(lines, charPattern);
            totalOccurrences += verticalOccurrences(lines, charPattern);
        }

        return totalOccurrences;
    }

    private static int horizontalOccurrences(String[][] lines, char[]  pattern) {
        int count = 0;

        for (String[] line : lines) {
            for (int j = 0; j < line.length - 3; j++) {
                if (j + 3 < line.length) {
                    if (line[j].equals(String.valueOf(pattern[0])) && line[j + 1].equals(String.valueOf(pattern[1]))
                            && line[j + 2].equals(String.valueOf(pattern[2])) && line[j + 3].equals(String.valueOf(pattern[3]))) {
                        count++;
                    }
                }
            }
        }
        return count;
    }


    private static int verticalOccurrences(String[][] lines, char[] pattern) {
        int count = 0;

        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length - 3; j++) {
                if (j + 3 < lines[i].length) {
                    if (lines[j][i].equals(String.valueOf(pattern[0])) && lines[j + 1][i].equals(String.valueOf(pattern[1]))
                            && lines[j + 2][i].equals(String.valueOf(pattern[2])) && lines[j + 3][i].equals(String.valueOf(pattern[3]))) {
                        count++;
                    }
                }
            }
        }
        return count;
    }


    private static int diagonalRightOccurrences(String[][] lines, char[] pattern) {
        int count = 0;
        for (int i = 0; i < lines.length - 3; i++) {
            for (int j = 0; j < lines[i].length - 3; j++) {
                if (lines[i][j].equals(String.valueOf(pattern[0])) && lines[i + 1][j + 1].equals(String.valueOf(pattern[1])) &&
                        lines[i + 2][j + 2].equals(String.valueOf(pattern[2])) && lines[i + 3][j + 3].equals(String.valueOf(pattern[3]))) {
                    count++;
                }
            }
        }
        return count;
    }

    private static int diagonalLeftOccurrences(String[][] lines, char[] pattern) {
        int count = 0;

        for (int i = 0; i < lines.length - 3; i++) {
            for (int j = 3; j < lines[i].length; j++) {
                if (lines[i][j].equals(String.valueOf(pattern[0])) && lines[i + 1][j - 1].equals(String.valueOf(pattern[1])) &&
                        lines[i + 2][j - 2].equals(String.valueOf(pattern[2])) && lines[i + 3][j - 3].equals(String.valueOf(pattern[3]))) {
                    count++;
                }
            }
        }
        return count;
    }


    public static String[][] fileOpening() throws FileNotFoundException {
        File myObj = new File("2024/input/day04.txt");
        Scanner myReader = new Scanner(myObj);

        String[][] lines = new String[140][140];
        int i = 0;
        while (myReader.hasNextLine()) {
            String[] line = myReader.nextLine().split("");
            lines[i] = line;
            i++;
        }

        myReader.close();
        return lines;
    }
}
