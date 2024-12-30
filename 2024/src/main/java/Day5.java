package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class Day5 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String[]>[] result = fileOpening();

        int total = followingRuleMiddleNR(result);
        System.out.println("Result of part 1 = " + total);

        int total2 = followingRuleMiddleNrPart2(result);
        System.out.println("Result of part 2 = " + total2);

    }

    private static int followingRuleMiddleNrPart2(ArrayList<String[]>[] result) {
        int totalValue = 0;

        for (String[] order : result[1]) {
            boolean following = true;
            for (String[] rule : result[0]) {
                following = orderFollowsRule(order, rule);
                if (!following) {
                    break;
                }
            }
            if (!following) {
                String[] newOrder = reOrdering(order, result[0]);
                int index = newOrder.length / 2;
                int middleValue = Integer.parseInt(newOrder[index]);
                totalValue += middleValue;
            }
        }
        return totalValue;
    }

    private static String[] reOrdering(String[] order, ArrayList<String[]> rules) {
        String[] newOrder = Arrays.copyOf(order, order.length);
        boolean changesMade;

        do {
            changesMade = false;

            for (String[] rule : rules) {
                if (notBothRuleValuesInOrder(order, rule)) {
                    continue;
                }

                int[] indices = getIndices(newOrder, rule);
                int firstIndex = indices[0];
                int secondIndex = indices[1];

                if (firstIndex > secondIndex) {
                    swap(newOrder, firstIndex, secondIndex);
                    changesMade = true;
                }
            }
        } while (changesMade);

        return newOrder;
    }

    private static int[] getIndices(String[] order, String[] rule) {
        int firstInRule = Integer.parseInt(rule[0]);
        int secondInRule = Integer.parseInt(rule[1]);
        int firstIndex = -1, secondIndex = -1;

        for (int i = 0; i < order.length; i++) {
            int item = Integer.parseInt(order[i]);

            if (item == firstInRule)
                firstIndex = i;
            else if (item == secondInRule)
                secondIndex = i;

            if (firstIndex != -1 && secondIndex != -1)
                break;
        }

        return new int[]{firstIndex, secondIndex};
    }

    private static void swap(String[] order, int firstIndex, int secondIndex) {
        String temp = order[firstIndex];
        order[firstIndex] = order[secondIndex];
        order[secondIndex] = temp;
    }

    private static int followingRuleMiddleNR(ArrayList<String[]>[] result) {
        int totalValue = 0;

        for (String[] order : result[1]) {
            boolean following = true;
            for (String[] rule : result[0]) {
                following = orderFollowsRule(order, rule);
                if (!following) {
                    break;
                }
            }
            totalValue = addToTotalIfItFollowsRule(order, following, totalValue);
        }
        return totalValue;
    }

    private static int addToTotalIfItFollowsRule(String[] order, boolean following, int totalValue) {
        if (following) {
            int index = order.length / 2;
            int middleValue = Integer.parseInt(order[index]);
            totalValue += middleValue;
        }
        return totalValue;
    }

    private static boolean orderFollowsRule(String[] order, String[] rule) {
        int firstInRule = Integer.parseInt(rule[0]);
        int secondInRule = Integer.parseInt(rule[1]);
        boolean firstFound = false;
        boolean secondFound = false;

        if (notBothRuleValuesInOrder(order, rule)) {
            return true;
        }

        for (String it : order) {
            int item = Integer.parseInt(it);
            if (item == firstInRule) {
                firstFound = true;
            }
            if (item == secondInRule) {
                secondFound = true;
            }

            if (secondFound && !firstFound) {
                return false;
            } else if (firstFound && !secondFound) {
                return true;
            }
        }
        return true;
    }

    private static boolean notBothRuleValuesInOrder(String[] order, String[] rule) {
        return !(Arrays.asList(order).contains(rule[0]) && Arrays.asList(order).contains(rule[1]));
    }

    public static ArrayList<String[]>[] fileOpening() throws FileNotFoundException {
        File myObj = new File("2024/input/day5.txt");
        Scanner myReader = new Scanner(myObj);

        ArrayList<String[]> resultsRules = new ArrayList<>();
        ArrayList<String[]> resultsOrder = new ArrayList<>();
        ArrayList<String[]>[] arrayOfListArray = new ArrayList[2];

        while (myReader.hasNextLine()) {
            String[] line = myReader.nextLine().split("\\|");
            if (line.length == 2) {
                resultsRules.add(line);
            } else if (!line[0].isEmpty()) {
                resultsOrder.add(line[0].split(","));
            }
        }
        arrayOfListArray[0] = resultsRules;
        arrayOfListArray[1] = resultsOrder;
        myReader.close();
        return arrayOfListArray;
    }
}
