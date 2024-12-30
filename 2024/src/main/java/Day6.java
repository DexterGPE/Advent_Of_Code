package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day6 {
    public static void main(String[] args) throws FileNotFoundException {
        int total = countStepsOnBoardBeforeLeaving(fileOpening());
        System.out.println("Steps on board before leaving (part 1) = " + total);

        System.out.println();
        System.out.println("Warning: This may take a while.");

        int validPositions = findLoopObstructionPositions(fileOpening());
        System.out.println("Valid obstruction positions (part 2) = " + validPositions);
    }

    private static int findLoopObstructionPositions(String[][] board) {
        int validPositions = 0;
        double counter = 0.0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

                double percentage = (counter / (board[i].length * board[j].length)) * 100;
                if (counter % 175 == 0) {
                    System.out.println(percentage + "% finished");
                }

                if (board[i][j].equals(".")) {
                    String[][] testBoard = cloneBoard(board);
                    testBoard[i][j] = "#";
                    if (causesLoop(testBoard)) {
                        validPositions++;
                    }
                }
                counter++;
            }
        }
        return validPositions;
    }

    private static boolean causesLoop(String[][] board) {
        Set<String> visitedStates = new HashSet<>();

        while (true) {
            String state = encodeBoardState(board);
            if (visitedStates.contains(state)) {
                return true;
            }
            visitedStates.add(state);
            String[][] oldBoard = cloneBoard(board);
            board = findGuardInBoard(board);
            if (boardsAreSame(board, oldBoard)) {
                return false;
            }
        }
    }

    private static String encodeBoardState(String[][] board) {
        StringBuilder sb = new StringBuilder();
        for (String[] row : board) {
            for (String cell : row) {
                sb.append(cell);
            }
        }
        return sb.toString();
    }

    private static String[][] findGuardInBoard(String[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                switch (board[i][j]) {
                    case "^" -> {
                        return moveUp(board, i, j);
                    }
                    case "v" -> {
                        return moveDown(board, i, j);
                    }
                    case ">" -> {
                        return moveRight(board, i, j);
                    }
                    case "<" -> {
                        return moveLeft(board, i, j);
                    }
                }
            }
        }
        return board;
    }


    private static int countStepsOnBoardBeforeLeaving(String[][] board) {
        board = startloop(board);
        return countX(board);
    }


    private static String[][] startloop(String[][] board) {
        do {
            String[][] oldboard = cloneBoard(board);
            board = findGuardInBoard(board);

            if (boardsAreSame(board, oldboard)) {
                return board;
            }
        } while (true);
    }

    private static int countX(String[][] board) {
        int total = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].equals("X")) {
                    total++;
                }
            }
        }
        return total;
    }


    private static boolean boardsAreSame(String[][] board, String[][] oldboard) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (!board[i][j].equals(oldboard[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    private static String[][] cloneBoard(String[][] board) {
        String[][] clone = new String[board.length][];
        for (int i = 0; i < board.length; i++) {
            clone[i] = board[i].clone();
        }
        return clone;
    }

    private static String[][] moveUp(String[][] board, int i, int j) {
        do {
            if (!board[i][j].equals("#")) {
                board[i][j] = "X";
                if (onEdgeOfBoard(board, i, j)) {
                    return board;
                }
            }
            if (i > 0) {
                i--;
            } else {
                break;
            }
        } while (!board[i][j].equals("#"));

        board[i + 1][j] = ">";
        return board;
    }

    private static boolean onEdgeOfBoard(String[][] board, int i, int j) {
        return i == 0 || j == 0 || j + 1 == board[i].length || i + 1 == board[j].length;
    }

    private static String[][] moveRight(String[][] board, int i, int j) {
        do {
            if (!board[i][j].equals("#")) {
                board[i][j] = "X";
                if (onEdgeOfBoard(board, i, j)) {
                    return board;
                }
            }
            if (j + 1 < board[i].length) {
                j++;
            } else {
                break;
            }
        } while (!board[i][j].equals("#"));

        board[i][j - 1] = "v";
        return board;
    }

    private static String[][] moveLeft(String[][] board, int i, int j) {
        do {
            if (!board[i][j].equals("#")) {
                board[i][j] = "X";
                if (onEdgeOfBoard(board, i, j)) {
                    return board;
                }
            }

            if (j > 0) {
                j--;
            } else {
                break;
            }
        } while (!board[i][j].equals("#"));

        board[i][j + 1] = "^";
        return board;
    }

    private static String[][] moveDown(String[][] board, int i, int j) {
        do {
            if (!board[i][j].equals("#")) {
                board[i][j] = "X";
                if (onEdgeOfBoard(board, i, j)) {
                    return board;
                }
            }
            if (i + 1 < board.length) {
                i++;
            } else {
                break;
            }
        } while (!board[i][j].equals("#"));

        board[i - 1][j] = "<";
        return board;
    }

    public static String[][] fileOpening() throws FileNotFoundException {
        File myObj = new File("2024/input/day6.txt");
        Scanner myReader = new Scanner(myObj);

        ArrayList<String[]> board = new ArrayList<>();

        while (myReader.hasNextLine()) {
            String[] line = myReader.nextLine().split("");
            board.add(line);
        }

        String[][] boardArray = new String[board.size()][];
        for (int i = 0; i < board.size(); i++) {
            boardArray[i] = board.get(i);
        }

        return boardArray;
    }
}
