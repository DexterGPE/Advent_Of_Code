package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day10 {
    public static void main(String[] args) throws FileNotFoundException {
        int[][] topographicMap = fileOpening();

        int total1 = sumOfTrailHeadScores(topographicMap);
        System.out.println("The sum of trailhead scores (part 1) = " + total1);

        int total2 = sumOfAllRatings(topographicMap);
        System.out.println("The sum of all Ratings (part 2) = " + total2);
    }

    private static int sumOfTrailHeadScores(int[][] topographicMap) {
        return sumOfList(calculateScores(topographicMap, false));
    }

    private static int sumOfAllRatings(int[][] topographicMap) {
        return sumOfList(calculateScores(topographicMap, true));
    }

    private static int sumOfList(List<Integer> list) {
        int total = 0;
        for (int i = 0; i < list.size(); i++) {
            total += list.get(i);
        }
        return total;
    }

    private static List<Integer> calculateScores(int[][] topographicMap, boolean returnRoutes) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < topographicMap.length; i++) {
            for (int j = 0; j < topographicMap[0].length; j++) {
                if (topographicMap[i][j] == 0) {
                    if (returnRoutes) {
                        HashSet<List<int[]>> toAdd = findTrailHeadRating(topographicMap, i, j);
                        result.add(toAdd.size());
                    } else {
                        HashSet<List<Integer>> toAdd = findAllPathsFromTrailHead(topographicMap, i, j);
                        result.add(toAdd.size());
                    }
                }
            }
        }
        return result;
    }

    private static HashSet<List<Integer>> findAllPathsFromTrailHead(int[][] topographicMap, int i, int j) {
        HashSet<List<int[]>> routesFinished = new HashSet<>();
        HashSet<List<Integer>> topsReached = new HashSet<>();
        Set<String> visited = new HashSet<>();
        List<int[]> route = new ArrayList<>();
        String[] options = {"Up", "Right", "Down", "Left"};

        recursivelyFindPaths(i, j, options, topographicMap, visited, topsReached, routesFinished, route);

        return topsReached;
    }

    private static HashSet<List<int[]>> findTrailHeadRating(int[][] topographicMap, int i, int j) {
        HashSet<List<int[]>> routesFinished = new HashSet<>();
        HashSet<List<Integer>> topsReached = new HashSet<>();
        Set<String> visited = new HashSet<>();
        List<int[]> route = new ArrayList<>();
        String[] options = {"Up", "Right", "Down", "Left"};

        recursivelyFindPaths(i, j, options, topographicMap, visited, topsReached, routesFinished, route);
        return routesFinished;
    }

    private static void recursivelyFindPaths(int i, int j, String[] options, int[][] topographicMap,
                                             Set<String> visited, HashSet<List<Integer>> topsReached,
                                             HashSet<List<int[]>> routesFinished, List<int[]> route) {
        if (topographicMap[i][j] == 9) {
            finishReached(i, j, topsReached, routesFinished, route);
            return;
        }

        String currentCoordination = i + "," + j;
        if (visited.contains(currentCoordination)) {
            return;
        }
        visited.add(currentCoordination);

        for (String option : options) {
            getAllPathsWithDirection(i, j, options, topographicMap, visited, topsReached, routesFinished, route, option);
        }

        visited.remove(currentCoordination);
    }

    private static void getAllPathsWithDirection(int i, int j, String[] options, int[][] topographicMap, Set<String> visited, HashSet<List<Integer>> topsReached, HashSet<List<int[]>> routesFinished, List<int[]> route, String option) {
        int[] newCoords = new int[]{i, j};
        newCoords = getNewCoordinatesInDirection(i, j, option, newCoords);

        if (isValidMove(newCoords[0], newCoords[1], topographicMap, i, j)) {
            route.add(new int[]{i, j});
            recursivelyFindPaths(newCoords[0], newCoords[1], options, topographicMap, visited, topsReached, routesFinished, route);
            route.removeLast();
        }
    }

    private static void finishReached(int i, int j, HashSet<List<Integer>> topsReached, HashSet<List<int[]>> routesFinished, List<int[]> route) {
        routesFinished.add(route);
        if (topsReached != null) {
            topsReached.add(Arrays.asList(i, j));
        }
    }

    private static int[] getNewCoordinatesInDirection(int i, int j, String option, int[] newCoords) {
        switch (option) {
            case "Up" -> newCoords = goUp(i, j);
            case "Right" -> newCoords = goRight(i, j);
            case "Down" -> newCoords = goDown(i, j);
            case "Left" -> newCoords = goLeft(i, j);
        }
        return newCoords;
    }

    private static boolean isValidMove(int i, int j, int[][] map, int oldI, int oldJ) {
        return i >= 0 && i < map.length && j >= 0 && j < map[0].length && map[i][j] == map[oldI][oldJ] + 1;
    }

    private static int[] goLeft(int x, int y) {
        return new int[]{x, y - 1};
    }

    private static int[] goRight(int x, int y) {
        return new int[]{x, y + 1};
    }

    private static int[] goUp(int x, int y) {
        return new int[]{x - 1, y};
    }

    private static int[] goDown(int x, int y) {
        return new int[]{x + 1, y};
    }

    public static int[][] fileOpening() throws FileNotFoundException {
        File myObj = new File("2024/input/day10.txt");
        Scanner myReader = new Scanner(myObj);
        List<int[]> linesList = new ArrayList<>();

        while (myReader.hasNextLine()) {
            String[] line = myReader.nextLine().split("");
            int[] intLine = new int[line.length];

            for (int i = 0; i < line.length; i++) {
                intLine[i] = Integer.parseInt(line[i]);
            }
            linesList.add(intLine);
        }

        myReader.close();
        return linesList.toArray(new int[0][]);
    }
}
