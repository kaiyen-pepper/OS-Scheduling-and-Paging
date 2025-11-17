// Assume that a system has a 32-bit virtual address. Write a 
// program that will be passed a virtual address (in decimal) 
// and page size in KB from console and have it output the page 
// number and offset for the given address.

// As an example, your program would run as follows:

// Enter page size:2
// Enter a virtual address: 10500
// The address 10500 contains: page number= 5 offset= 260

import java.util.Scanner;

class task1 {
    public static int calculatePageNumber(int pageSize, int virtualAddr) {
        // Unit conversion: page size in bytes
        // KiB = 1024 Bytes
        pageSize = pageSize * 1024;
        return (int) Math.floor(virtualAddr / pageSize);
    }

    public static int calculatePageOffset(int pageSize, int pageNumber, int virtualAddr) {
        return virtualAddr - (pageNumber * pageSize * 1024);
    }
    public static void main(String[] args) {
        // Initialize Scanner
        Scanner scanner = new Scanner(System.in);

        // Take in inputs page size and virtual address for calculation
        System.out.print("Enter page size: ");
        int pageSize = scanner.nextInt();

        System.out.print("Enter a virtual address: ");
        int virtualAddr = scanner.nextInt();
        scanner.close();

        // Do calculations
        int pageNumber = calculatePageNumber(pageSize, virtualAddr);
        int pageOffset = calculatePageOffset(pageSize, pageNumber, virtualAddr);

        // Print results
        System.out.printf("The address %d contains: page number = %d offset = %d\n", virtualAddr, pageNumber, pageOffset);
    }
}