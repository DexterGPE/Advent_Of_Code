package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day8 {
    public static void main(String[] args) throws FileNotFoundException {
        String[][] board = fileOpening();

        int totalNumberOfUniqueAntinodesPart1 = findNumberOfUniqueAntinodes(board, true);
        System.out.println("Total number of unique antinodes (part 1) = " + totalNumberOfUniqueAntinodesPart1);

        int totalNumberOfUniqueAntinodesPart2 = findNumberOfUniqueAntinodes(board, false);
        System.out.println("Total number of unique antinodes (part 2) = " + totalNumberOfUniqueAntinodesPart2);
    }

    private static int findNumberOfUniqueAntinodes(String[][] board, boolean part1) {
        HashSet<List<Integer>> antinodes = getAllAntinodes(board, part1);
        return antinodes.size();
    }

    private static HashSet<List<Integer>> getAllAntinodes(String[][] board, boolean part1) {
        HashSet<List<Integer>> antinodes = new HashSet<>();
        Map<String, List<int[]>> coordinatesMap = findCoordsOfAllAntennas(board);

        for (String key : coordinatesMap.keySet()) {
            List<int[]> coordinates = coordinatesMap.get(key);

            if (!coordinates.isEmpty()) {
                for (int i = 0; i < coordinates.size(); i++) {
                    for (int j = i + 1; j < coordinates.size(); j++) {
                        int[] firstItem = coordinates.get(i);
                        int[] secondItem = coordinates.get(j);
                        if (part1) {
                            newAntinodeCoordinateCalculations(firstItem, secondItem, antinodes, board);
                            newAntinodeCoordinateCalculations(secondItem, firstItem, antinodes, board);
                        } else {
                            newAntinodeCoordinateCalculationsWithoutDistance(firstItem, secondItem, antinodes, board);
                            newAntinodeCoordinateCalculationsWithoutDistance(secondItem, firstItem, antinodes, board);
                            antinodes.add(Arrays.asList(firstItem[0], firstItem[1]));
                            antinodes.add(Arrays.asList(secondItem[0], secondItem[1]));
                        }
                    }
                }
            }
        }
        return antinodes;
    }

    private static HashSet<List<Integer>> newAntinodeCoordinateCalculationsWithoutDistance(int[] coordinate1, int[] coordinate2, HashSet<List<Integer>> antinodes, String[][] board) {
        while (true) {
            int newX = coordinate2[0] + (coordinate2[0] - coordinate1[0]);
            int newY = coordinate2[1] + (coordinate2[1] - coordinate1[1]);
            if (newX >= 0 && newX < board.length && newY >= 0 && newY < board[0].length) {
                antinodes.add(Arrays.asList(newX, newY));
                coordinate1 = coordinate2.clone();
                coordinate2 = new int[]{newX, newY};
            } else {
                break;
            }
        }
        return antinodes;
    }

    private static HashSet<List<Integer>> newAntinodeCoordinateCalculations(int[] coordinate1, int[] coordinate2, HashSet<List<Integer>> antinodes, String[][] board) {
        int newX = coordinate2[0] + (coordinate2[0] - coordinate1[0]);
        int newY = coordinate2[1] + (coordinate2[1] - coordinate1[1]);
        if (newX >= 0 && newX < board.length && newY >= 0 && newY < board[0].length) {
            antinodes.add(Arrays.asList(newX, newY));
        }
        return antinodes;
    }

    private static Map<String, List<int[]>> findCoordsOfAllAntennas(String[][] board) {
        Map<String, List<int[]>> coordinates = new HashMap<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (!board[i][j].equals(".")) {
                    coordinates.computeIfAbsent(board[i][j], k -> new ArrayList<>()).add(new int[]{i, j});
                }
            }
        }
        return coordinates;
    }

    public static String[][] fileOpening() throws FileNotFoundException {
        File myObj = new File("2024/input/day8.txt");
        Scanner myReader = new Scanner(myObj);
        List<String[]> linesList = new ArrayList<>();

        while (myReader.hasNextLine()) {
            String[] line = myReader.nextLine().split("");
            linesList.add(line);
        }
        String[][] lines = new String[linesList.size()][];
        lines = linesList.toArray(lines);

        myReader.close();
        return lines;
    }
}
