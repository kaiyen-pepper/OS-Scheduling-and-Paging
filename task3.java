// Write a program that is passed: number of sectors, number of tracks, 
// number of cylinders of a given hard disk and a logical block number 
// (all in decimals) and have it output the logical block number in 
// <Cylinder#, plate# , Sector #> format.
// Note : 0th sector is at <0,0,0> and 1st sector is at <0,0,1> 

// As an example, your program would run as follows:
// Enter a logical block number:1041
// Enter HD number of cylinders: 1000
// Enter HD number of tracks: 20
// Enter HD number of sectors: 63
// The logical block number 1041 is located at < 0 , 16 , 33>

import java.util.Scanner;

public class task3 {

    public static int calculateCylinders(int blockNum, int numCylinder, int numTracks, int numSectors){
        return blockNum / (numTracks * numSectors);
    }

    public static int calculateTracks(int blockNum, int numCylinder, int numTracks, int numSectors){
        return blockNum % (numTracks * numSectors) / numSectors;
    }

    public static int calculateSectors(int blockNum, int numCylinder, int numTracks, int numSectors){
        return blockNum % (numTracks * numSectors) % numSectors;
    }

    public static void main(String[] args) {
        // Initialize Scanner
        Scanner scanner = new Scanner(System.in);

        // Take in inputs page size and virtual address for calculation
        System.out.print("Enter a logical block number: ");
        int blockNum = scanner.nextInt();

        System.out.print("Enter HD number of cylinders: ");
        int numCylinder = scanner.nextInt();

        System.out.print("Enter HD number of tracks: ");
        int numTracks = scanner.nextInt();

        System.out.print("Enter HD number of sectors: ");
        int numSectors = scanner.nextInt();
        scanner.close();

        // Do calculations
        int cylinderIndex = calculateCylinders(blockNum, numCylinder, numTracks, numSectors);
        int trackNum = calculateTracks(blockNum, numCylinder, numTracks, numSectors);
        int sectorNum = calculateSectors(blockNum, numCylinder, numTracks, numSectors);

        // Print results
        System.out.printf("The logical block number %d is located at < %d , %d , %d >\n", blockNum, cylinderIndex, trackNum, sectorNum);
    }
}
