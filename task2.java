// Write a program that implements the following disk-scheduling algorithms:
// FCFS, SSTF, SCAN, C-SCAN

// Your program will service a disk with 5,000 cylinders numbered 0 to 4,999. 
// The program will be passed the initial position of the disk head (as a 
// parameter on the command line) and report the total amount of head movement 
// and total number of change of direction required by each algorithm under 
// each of the following cases:

// 1. The program will generate a random series of 1,000 cylinder requests and 
// service them according to each of the algorithms listed above.
// 2. The program will read a series of cylinder requests from an input.txt 
// file and service them according to each of the algorithms listed above.

import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class task2 {
    public static void FCFS(){

    }

    public static void main(String[] args){
        int numberRequests = 1000;

        // ---- Part 1 ----
        // Generate random 1000 cylinder requests and run each algorithm
        int randomRequests1[] = new int[numberRequests];
        Random random = new Random();

        for (int i = 0; i < numberRequests; i++) {
            randomRequests1[i] = random.nextInt(numberRequests);
        }

        // ---- Part 2 ----
        // Read cylinder requests from input.txt and run each algorithm
        ArrayList<Integer> randomRequests2 = new ArrayList<>();
        String fileName = "input.txt";

        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextInt()) {
                randomRequests2.add(scanner.nextInt());
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }

    }
}
