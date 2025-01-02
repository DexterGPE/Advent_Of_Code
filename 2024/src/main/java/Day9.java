package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Day9 {
    public static void main(String[] args) throws FileNotFoundException {
        String[] inputLine = fileOpening();

        long total1 = calculateSystemChecksum(inputLine);
        System.out.println("System Checksum (part 1) = " + total1);

        long total2 = calculateSystemChecksumPart2(inputLine);
        System.out.println("System Checksum (part 2) = " + total2);
    }

    private static long calculateSystemChecksumPart2(String[] inputLine) {
        String[] blockRepresentation = createBlockRepresentation(inputLine);
        String[] gaplessRepresentation = removeGapsPart2(blockRepresentation);
        return calculateChecksum(gaplessRepresentation);
    }

    private static long calculateSystemChecksum(String[] inputLine) {
        String[] blockRepresentation = createBlockRepresentation(inputLine);
        String[] gaplessRepresentation = removeGaps(blockRepresentation);
        return calculateChecksum(gaplessRepresentation);
    }

    private static long calculateChecksum(String[] gaplessRepresentation) {
        long checkSum = 0;
        for (int i = 0; i < gaplessRepresentation.length; i++) {
            if (!gaplessRepresentation[i].equals(".")) {
                checkSum += i * Long.parseLong(gaplessRepresentation[i]);
            }
        }
        return checkSum;
    }

    private static String[] removeGapsPart2(String[] blockRepresentation) {
        ArrayList<String> disk = new ArrayList<>();
        Collections.addAll(disk, blockRepresentation);

        int highestBlockId = Integer.parseInt(disk.stream().filter(s -> !s.equals(".")).max(String::compareTo).orElse("0"));

        while (highestBlockId > 0) {
            int highestBlockIndex = disk.indexOf(String.valueOf(highestBlockId));
            int blockSize = disk.lastIndexOf(String.valueOf(highestBlockId)) - highestBlockIndex + 1;
            int freeSpace = 0;

            for (int i = 0; i < highestBlockIndex; i++) {
                if (disk.get(i).equals(".")) {
                    freeSpace++;
                } else {
                    freeSpace = 0;
                }

                if (freeSpace == blockSize) {
                    for (int k = 0; k < blockSize; k++) {
                        disk.set(i - blockSize + k + 1, String.valueOf(highestBlockId));
                        disk.set(highestBlockIndex + k, ".");
                    }
                    break;
                }
            }
            highestBlockId--;
        }
        return disk.toArray(new String[0]);
    }

    private static String[] removeGaps(String[] blockRepresentation) {
        for (int i = 0; i < blockRepresentation.length; i++) {
            if (blockRepresentation[i].equals(".")) {
                for (int j = blockRepresentation.length - 1; j > i; j--) {
                    if (!blockRepresentation[j].equals(".")) {
                        blockRepresentation[i] = blockRepresentation[j];
                        blockRepresentation[j] = ".";
                        break;
                    }
                }
            }
        }
        return blockRepresentation;
    }

    private static String[] createBlockRepresentation(String[] inputLine) {
        ArrayList<String> blockRepresentation = new ArrayList<>();
        for (int i = 0; i < inputLine.length; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < Integer.parseInt(inputLine[i]); j++) {
                    blockRepresentation.add(Integer.toString(i / 2));
                }
            } else {
                for (int j = 0; j < Integer.parseInt(inputLine[i]); j++) {
                    blockRepresentation.add(".");
                }
            }
        }
        return blockRepresentation.toArray(new String[0]);
    }

    public static String[] fileOpening() throws FileNotFoundException {
        File myObj = new File("2024/input/day9.txt");
        Scanner myReader = new Scanner(myObj);
        String[] line = myReader.nextLine().trim().split("");
        myReader.close();
        return line;
    }
}
