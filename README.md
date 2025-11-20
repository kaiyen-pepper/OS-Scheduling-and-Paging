
# Paging and Disk-Scheduling
This project applies paging and scheduling algorithms to three java programs. 

### Page Number and Page Offset Calculator
Task 1: task1.java takes in user input from the Command Line Interface of page size (Kb) and 32-bit decimal address and calculates the page number and page offset for the given information.

### Disk-Scheduler
Task 2: task2.java takes input from the Command Line Interface to schedule processes from a given input file and 1000 randomly generated requests for a disk with 5000 cylinders numbered 0 to 4999, previous and current head. The program will print out the average turnaround, waiting, and response times as well as CPU utilization.

### Logical Block Number Calculator
Task 3: task3.java takes input from the Command Line Interface to of a logical block number, HD number of cylinders, tracks, and sectors to calculates the hard disk address in <Cylinder, Head, Sector> format.

## Environment

To run this project, you will need to have the following environment variables:

`javac` or a Java compiler

## Running Tests

To run each task, run the following command

```bash
  javac task1.java
  java task1
```
Change the file name as needed.