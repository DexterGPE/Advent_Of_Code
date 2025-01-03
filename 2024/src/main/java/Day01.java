package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Day01 {
    public static void main(String[] args) throws FileNotFoundException {
        int[][] lines = readFile();

        System.out.println("part1 solution = " + part1(lines));
        System.out.println("part2 solution = " + part2(lines));
    }

    private static int countOccurrences(int[] array, int target) {
        int count = 0;
        for (int num : array) {
            if (num == target) count++;
        }
        return count;
    }

    private static int part2(int[][] lines) {
        int total = 0;
        int[] lines1 = lines[0];
        int[] lines2 = lines[1];

        Arrays.sort(lines1);
        Arrays.sort(lines2);

        for (int num : lines1) {
            total += num * countOccurrences(lines2, num);
        }

        return total;
    }

    private static int part1(int[][] lines) {
        int total = 0;
        int[] lines1 = lines[0];
        int[] lines2 = lines[1];

        Arrays.sort(lines1);
        Arrays.sort(lines2);

        for (int i = 0; i < lines1.length; i++) {
            total += Math.abs(lines2[i] - lines1[i]);
        }

        return total;
    }

    private static int[][] readFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("2024/input/day01.txt"));

        int[][] lines = new int[2][1000];
        int i = 0;

        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(" {3}");
            if (line.length < 2) continue;

            lines[0][i] = Integer.parseInt(line[0]);
            lines[1][i] = Integer.parseInt(line[1]);
            i++;
        }

        scanner.close();
        return new int[][] { Arrays.copyOf(lines[0], i), Arrays.copyOf(lines[1], i) };
    }
}
