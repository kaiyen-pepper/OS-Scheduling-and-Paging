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
import java.util.Set;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class task2 {
    // First-Come, First-Serve algorithm implementation
    public static void FCFS(ArrayList<Integer> requests, int head) {
        int totalHeadMovement = 0;
        int directionChanges = 0;
        int prevDirection = 0;
        for (int request : requests) {
            // Process each request in the order they arrive
            System.out.println("Servicing request at cylinder: " + request);
            if (Integer.compare(request, head) != prevDirection && prevDirection != 0) {
                directionChanges++;
            }
            prevDirection = Integer.compare(request, head);
            totalHeadMovement += Math.abs(request - head);
            head = request;
        }
        
        // Output results
        System.out.println("FCFS - Total Head Movement: " + totalHeadMovement + ", Direction Changes: " + directionChanges);
    }

    // Shortest Seek Time First algorithm implementation
    public static void SSTF(ArrayList<Integer> requests, int head) {
        // Sort requests via QuickSort
        requests.add(2150); // Temporary addition to avoid index issues
        Collections.sort(requests);

        // Use two pointers to track closest requests
        int left = (requests.indexOf(head) > 0) ? requests.indexOf(head) - 1 : 0;
        int right = (requests.indexOf(head) < requests.size() - 1) ? requests.indexOf(head) + 1 : 4999;
        int totalHeadMovement = 0;
        int directionChanges = 0;
        int prevDirection = 0;
        Set<Integer> visited = new HashSet<Integer>();

        // Infinite loop stuck on one end
        while (visited.size() < requests.size() - 1) {
            // Find the closest request based on left and right pointers
            int left_diff = Math.abs(head - requests.get(left));
            int right_diff = Math.abs(head - requests.get(right));

            // Choose next request if not visited already
            int nextHead = -1;
            if (!visited.contains(left) && !visited.contains(right)) {
                nextHead = (left_diff <= right_diff) ? left : right;
            } else {
                nextHead = visited.contains(left) ? right : left;
            }

            // Process request and update HashSet
            System.out.println("Servicing request at cylinder: " + requests.get(nextHead));
            totalHeadMovement += Math.abs(requests.get(nextHead) - head);
            if (Integer.compare(requests.get(nextHead), head) != prevDirection && prevDirection != 0) {
                directionChanges++;
            }
            prevDirection = Integer.compare(nextHead, head);
            visited.add(nextHead);

            // Update head position
            head = requests.get(nextHead);

            // Update left and right pointers
            left = (nextHead == left && left > 0) ? left - 1 : left;
            right = (nextHead == right && right < requests.size() - 1) ? right + 1 : right;
        }

        // for (int i = 0; i < requests.size(); i++) {
        //     int nextRequest;
        //     if (Math.abs(head - requests.get(left)) <= Math.abs(head - requests.get(right)) && requests.get(left) != 0) {
        //         nextRequest = requests.get(left);
        //         left = (requests.indexOf(requests.get(left)) > 0) ? requests.get(left - 1) : 0;
        //     } else if (right != 4999) {
        //         nextRequest = requests.get(right);
        //         right = (requests.indexOf(requests.get(right)) < requests.size() - 1) ? requests.get(right + 1) : 4999;
        //     } else {
        //         break; // No more requests to process
        //     }

        //     // Process the next request
        //     System.out.println("Servicing request at cylinder: " + requests.get(nextRequest));
        //     if (Integer.compare(requests.get(nextRequest), head) != prevDirection && prevDirection != 0) {
        //         directionChanges++;
        //     }
        //     prevDirection = Integer.compare(requests.get(nextRequest), head);
        //     totalHeadMovement += Math.abs(requests.get(nextRequest) - head);
        //     head = requests.get(nextRequest);
        // }

        // Output results
        System.out.println("SSTF - Total Head Movement: " + totalHeadMovement + ", Direction Changes: " + directionChanges);

    }

    public static void main(String[] args){
        int numberRequests = 1000;

        // ---- Part 1 ----
        // Generate random 1000 cylinder requests between 0 and 4999
        ArrayList<Integer> randomRequests1 = new ArrayList<>(numberRequests);
        Random random = new Random();

        for (int i = 0; i < numberRequests; i++) {
            randomRequests1.add(random.nextInt(numberRequests));
        }

        // ---- Part 2 ----
        // Read cylinder requests from input.txt
        ArrayList<Integer> randomRequests2 = new ArrayList<>();
        String fileName = "input.txt";

        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextInt()) {
                randomRequests2.add(scanner.nextInt());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
        

        // ---- Run algorithms on both sets of requests ----
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter initial head position (0-4999): ");
        int initialHeadPosition = scanner.nextInt();
        scanner.close();

        // Run algorithms with part 1 requests
        // System.out.println("Results for random requests:");
        // FCFS(randomRequests1, initialHeadPosition);
        // SSTF(randomRequests1, initialHeadPosition);

        // Run algorithms with part 2 requests
        System.out.println("Results for requests from input.txt:");
        // FCFS(randomRequests2, initialHeadPosition);
        SSTF(randomRequests2, initialHeadPosition);

    }
}
