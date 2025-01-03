package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Day11 {
    public static void main(String[] args) throws FileNotFoundException {
        Long[] stones = fileOpening();

        Long total1 = calculateNumberOfStones(stones, 25);
        System.out.println("Total number of stones after 25 blinks = " + total1);

        Long total2 = calculateNumberOfStones(stones, 75);
        System.out.println("Total number of stones after 75 blinks = " + total2);
    }

    private static HashMap<Long, Long> getMapWithFreqOfStones(Long[] stones, int numberOfBlinks) {
        HashMap<Long, Long> stoneFreq = initializeStoneFreq(stones);

        for (int i = 0; i < numberOfBlinks; i++) {
            HashMap<Long, Long> tempStoneFreq = new HashMap<>();
            for (Long key : stoneFreq.keySet()) {
                performBlinkingRulesToUpdateStoneFreq(key, stoneFreq, tempStoneFreq);
            }
            stoneFreq = tempStoneFreq;
        }
        return stoneFreq;
    }

    private static void performBlinkingRulesToUpdateStoneFreq(Long key, HashMap<Long, Long> stoneFreq, HashMap<Long, Long> tempStoneFreq) {
        Long frequency = stoneFreq.get(key);
        if (key == 0)
            addToHashMap(1L, tempStoneFreq, frequency);
        else if (evenNumberOfDigits(key))
            splitStoneAndAddToHashMap(key, tempStoneFreq, frequency);
        else
            addToHashMap(key * 2024, tempStoneFreq, frequency);
    }

    private static void splitStoneAndAddToHashMap(Long key, HashMap<Long, Long> tempStoneFreq, Long frequency) {
        String stoneString = Long.toString(key);
        long firstHalf = Long.parseLong(stoneString.substring(0, stoneString.length() / 2));
        long secondHalf = Long.parseLong(stoneString.substring(stoneString.length() / 2));
        addToHashMap(firstHalf, tempStoneFreq, frequency);
        addToHashMap(secondHalf, tempStoneFreq, frequency);
    }

    private static Long calculateNumberOfStones(Long[] stones, int numberOfBlinks){
        HashMap<Long, Long> stoneFreq = getMapWithFreqOfStones(stones, numberOfBlinks);
        Long total= 0L;

        for ( Long val : stoneFreq.values())
            total+=val;

        return total;
    }

    private static void addToHashMap(long key, HashMap<Long, Long> map, Long frequency) {
        if (map.containsKey(key))
            map.put(key, map.get(key) + frequency);
         else
            map.put(key, frequency);
    }

    private static HashMap<Long, Long> initializeStoneFreq(Long[] stones) {
        HashMap<Long, Long> stoneFreq = new HashMap<>();
        for (long stone:stones)
            addToHashMap(stone, stoneFreq, 1L);

        return stoneFreq;
    }

    private static boolean evenNumberOfDigits(long number) {
        String numberStr = Long.toString(number);
        return numberStr.length() % 2 == 0;
    }

    public static Long[] fileOpening() throws FileNotFoundException {
        File myObj = new File("2024/input/day11.txt");
        Scanner myReader = new Scanner(myObj);

        String[] line = myReader.nextLine().split(" ");
        Long[] intLine = new Long[line.length];

        for (int i = 0; i < line.length; i++)
            intLine[i] = Long.parseLong(line[i]);

        myReader.close();
        return intLine;
    }
}
