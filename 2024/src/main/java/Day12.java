package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day12 {

    public static void main(String[] args) throws FileNotFoundException {
        String[][] gardenMap = loadGardenMap();

        int total1 = calculateFencePrice(gardenMap, true);
        System.out.println("Total fence price (part 1) = " + total1);

        int total2 = calculateFencePrice(gardenMap, false);
        System.out.println("Total fence price (part 2) = " + total2);
    }

    private static int calculateFencePrice(String[][] gardenMap, boolean part1) {
        int total = 0;
        boolean[][] visited = new boolean[gardenMap.length][gardenMap[0].length];
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int i = 0; i < gardenMap.length; i++) {
            for (int j = 0; j < gardenMap[0].length; j++) {
                if (!visited[i][j]) {
                    List<int[]> region = findRegion(gardenMap, visited, directions, i, j);

                    if (part1) {
                        total += calculatePerimeterContribution(region, directions, gardenMap);
                    } else {
                        total += calculateCornerContribution(region);
                    }
                }
            }
        }
        return total;
    }

    private static List<int[]> findRegion(String[][] gardenMap, boolean[][] visited, int[][] directions, int startX, int startY) {
        Queue<int[]> queue = new LinkedList<>();
        List<int[]> region = new ArrayList<>();
        queue.add(new int[]{startX, startY});
        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            region.add(current);

            for (int[] dir : directions) {
                int nx = current[0] + dir[0];
                int ny = current[1] + dir[1];

                if (newPointIsPartOfRegion(gardenMap, visited, startX, startY, nx, ny)) {
                    visited[nx][ny] = true;
                    queue.add(new int[]{nx, ny});
                }
            }
        }
        return region;
    }

    private static boolean newPointIsPartOfRegion(String[][] gardenMap, boolean[][] visited, int startX, int startY, int nx, int ny) {
        return isInBounds(gardenMap, nx, ny) && gardenMap[nx][ny].equals(gardenMap[startX][startY]) && !visited[nx][ny];
    }

    private static boolean isInBounds(String[][] gardenMap, int x, int y) {
        return x >= 0 && y >= 0 && x < gardenMap.length && y < gardenMap[0].length;
    }

    private static int calculateCornerContribution(List<int[]> region) {
        int cornerCount = 0;
        int[][] diagonalDirections = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        for (int[] point : region) {
            int row = point[0];
            int col = point[1];

            for (int[] direction : diagonalDirections) {
                int adjacentRow = row + direction[0];
                int adjacentCol = col + direction[1];
                int[] adjacentPointRow = {row + direction[0], col};
                int[] adjacentPointCol = {row, col + direction[1]};

                if (!regionContains(region, adjacentPointRow) && !regionContains(region, adjacentPointCol)) {
                    cornerCount++;
                } else if (regionContains(region, adjacentPointRow) && regionContains(region, adjacentPointCol) && !regionContains(region, new int[]{adjacentRow, adjacentCol})) {
                    cornerCount++;
                }
            }
        }
        return cornerCount * region.size();
    }

    private static int calculatePerimeterContribution(List<int[]> region, int[][] directions, String[][] gardenMap) {
        int perimeter = 0;
        for (int[] point : region) {
            for (int[] dir : directions) {
                int nx = point[0] + dir[0];
                int ny = point[1] + dir[1];

                if (nx < 0 || ny < 0 || nx >= gardenMap.length || ny >= gardenMap[0].length) {
                    perimeter++;
                } else if (!gardenMap[nx][ny].equals(gardenMap[point[0]][point[1]])) {
                    perimeter++;
                }
            }
        }
        return perimeter * region.size();
    }

    public static boolean regionContains(List<int[]> list, int[] target) {
        for (int[] element : list) {
            if (Arrays.equals(element, target)) {
                return true;
            }
        }
        return false;
    }

    private static String[][] loadGardenMap() throws FileNotFoundException {
        List<String[]> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("2024/input/day12.txt"))) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine().split(""));
            }
        }
        return lines.toArray(new String[0][]);
    }
}
