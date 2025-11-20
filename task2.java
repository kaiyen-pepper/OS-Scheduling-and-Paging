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
import java.util.ArrayList;
import java.util.Collections;

public class task2 {
    // First-Come, First-Serve algorithm implementation
    public static void FCFS(ArrayList<Integer> requests, int head, int prevHead) {
        int totalHeadMovement = 0;
        int directionChanges = 0;
        int prevDirection = Integer.compare(head, prevHead);
        for (int request : requests) {
            // Process each request in the order they arrive
            // System.out.println("Servicing request at cylinder: " + request);
            if (Integer.compare(request, head) != prevDirection && prevDirection != 0) {
                directionChanges++;
            }
            prevDirection = Integer.compare(request, head);
            totalHeadMovement += Math.abs(request - head);
            head = request;
        }
        
        // Output results
        System.out.printf("FCFS - Total Head Movement: %d, Direction Changes: %d\n\n", totalHeadMovement, directionChanges);
    }

    // Shortest Seek Time First algorithm implementation
    public static void SSTF(ArrayList<Integer> requests, int head, int prevHead) {
        // Sort requests in ascending order
        Collections.sort(requests);

        int totalHeadMovement = 0;
        int directionChanges = 0;
        boolean[] visited = new boolean[requests.size()];
        String prevDirection = Integer.compare(head, prevHead) > 0 ? "right" : (Integer.compare(head, prevHead) < 0 ? "left" : null);

        // Find initial position of head in sorted list
        int pos = Collections.binarySearch(requests, head);
        if (pos < 0) pos = -pos - 1;

        int left = pos - 1;
        int right = pos;

        while (true) {
            int leftDiff = Integer.MAX_VALUE;
            int rightDiff = Integer.MAX_VALUE;

            if (left >= 0 && !visited[left])
                leftDiff = Math.abs(head - requests.get(left));

            if (right < requests.size() && !visited[right])
                rightDiff = Math.abs(head - requests.get(right));

            // Visited all requests 
            if (leftDiff == Integer.MAX_VALUE && rightDiff == Integer.MAX_VALUE)
                break;


            // Pick closest out of left and right indices
            int next;
            int nextIndex;
            String nextDirection;

            if (leftDiff <= rightDiff) {
                next = requests.get(left);
                nextIndex = left;
                nextDirection = "left";
                left--;
            } else {
                next = requests.get(right);
                nextIndex = right;
                nextDirection = "right";
                right++;
            }

            if (prevDirection != null && !prevDirection.equals(nextDirection)) {
                directionChanges++;
            }

            prevDirection = nextDirection;
            visited[nextIndex] = true;

            // System.out.println("Servicing request at cylinder: " + next);
            totalHeadMovement += Math.abs(head - next);
            head = next;
        }

        // Output results
        System.out.printf("SSTF - Total Head Movement: %d, Direction Changes: %d\n\n", totalHeadMovement, directionChanges);;

    }
    
    // Scan helper function
    public static int processRange(int head, ArrayList<Integer> requests, int start, int end) {
        int totalHeadMovement = 0;
        start = Math.max(0, Math.min(start, requests.size() - 1));
        end = Math.max(0, Math.min(end, requests.size() - 1));
        int step = (start < end) ? 1 : -1;

        for (int i = start; i != end + step; i += step) {
            // System.out.println("Servicing request at cylinder: " + requests.get(i));
            totalHeadMovement += Math.abs(requests.get(i) - head);
            head = requests.get(i);
        }
        return totalHeadMovement;
    }

    // Scan algorithm implementation
    public static void SCAN(ArrayList<Integer> requests, int head, int prevHead) {
        int totalHeadMovement = 0;
        
        // Sort requests in ascending order
        requests.sort(null);
        
        // Find initial position of head in sorted list
        int pos = Collections.binarySearch(requests, head);
        if (pos < 0) pos = -pos - 1;

        // Determine initial direction
        boolean movingRight = head >= prevHead;
        System.out.println("Initial head direction: " + (movingRight ? "right" : "left"));

        // Process requests based on initial direction
        if (movingRight) {
            // Go towards end of list
            totalHeadMovement += processRange(head, requests, pos, requests.size() - 1);
            // Move to the end before reversing
            totalHeadMovement += Math.abs(4999 - requests.get(requests.size() - 1));
            head = 4999;
            // System.out.println("Reached end of disk at cylinder: " + head);

            // Service left side
            if (pos - 1 >= 0)
                // Go towards start of list
                totalHeadMovement += processRange(head, requests, pos - 1, 0);
        } else {
            // Service left side
            if (pos - 1 >= 0)
                // Go towards start of list
                totalHeadMovement += processRange(head, requests, pos - 1, 0);
            
            // Move to the start before reversing
            totalHeadMovement += Math.abs(requests.get(0) - 0);
            head = 0;
            // System.out.println("Reached end of disk at cylinder: " + head);

            // Service right side
            if (pos < requests.size())
                // Go towards end of list
                totalHeadMovement += processRange(head, requests, pos, requests.size() - 1);
        }

        // Output results
        System.out.printf("SCAN - Total Head Movement: %d, Direction Changes: %d\n\n", totalHeadMovement, 1);
    }

    // Scan algorithm implementation
    public static void CSCAN(ArrayList<Integer> requests, int head, int prevHead) {
        int totalHeadMovement = 0;
        int directionChanges = 0;
        
        // Sort requests in ascending order
        requests.sort(null);
        
        // Find initial position of head in sorted list
        int pos = Collections.binarySearch(requests, head);
        if (pos < 0) pos = -pos - 1;

        boolean movingRight = head >= prevHead;
        System.out.println("Initial head direction: " + (movingRight ? "right" : "left"));

        // Process requests based on initial direction
        if (movingRight) {
            if (pos < requests.size())
                // Go towards end of list
                totalHeadMovement += processRange(head, requests, pos, requests.size() - 1);

            // Move to the end before jumping to the start
            totalHeadMovement += Math.abs(4999 - requests.get(requests.size() - 1));
            System.out.printf("Reached end of disk at cylinder: 4999\nRestarting at cylinder 0\n");
            
            // Jump from end to start
            directionChanges++;
            totalHeadMovement += 4999;
            head = 0;

            if (pos > 0)
                // Go towards start of list
                totalHeadMovement += processRange(head, requests, 0, pos - 1);
            directionChanges++;
        } else {
            if (pos - 1 >= 0)
                // Go towards start of list
                totalHeadMovement += processRange(head, requests, pos - 1, 0);
 
            // Move to the start before jumping to the end
            totalHeadMovement += requests.get(0);
            System.out.printf("Reached end of disk at cylinder 0\nJumping to cylinder 4999\n");
            
            // Jump from start to end
            directionChanges++;
            totalHeadMovement += 4999;
            head = 4999;

            // Service right portion
            if (pos < requests.size())
                // Go towards end of list
                totalHeadMovement += processRange(head, requests, requests.size() - 1, pos);
            directionChanges++;
        }

        // Output results
        System.out.printf("CSCAN - Total Head Movement: %d, Direction Changes: %d\n\n", totalHeadMovement, directionChanges);
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
        Scanner scanner2 = new Scanner(System.in);
        System.out.print("Enter initial head position (0-4999): ");
        int initialHeadPosition = scanner2.nextInt();
        System.out.print("Enter previous head position (0-4999): ");
        int previousHeadPosition = scanner2.nextInt();        
        scanner2.close();

        // Run algorithms with part 1 requests
        System.out.println("Results for random requests:");
        FCFS(randomRequests1, initialHeadPosition, previousHeadPosition);
        SSTF(randomRequests1, initialHeadPosition, previousHeadPosition);
        SCAN(randomRequests1, initialHeadPosition, previousHeadPosition);
        CSCAN(randomRequests1, initialHeadPosition, previousHeadPosition);

        // Run algorithms with part 2 requests
        System.out.println("Results for requests from input.txt:");
        FCFS(randomRequests2, initialHeadPosition, previousHeadPosition);
        SSTF(randomRequests2, initialHeadPosition, previousHeadPosition);
        SCAN(randomRequests2, initialHeadPosition, previousHeadPosition);
        CSCAN(randomRequests2, initialHeadPosition, previousHeadPosition);

    }
}
