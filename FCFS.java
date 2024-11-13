import java.util.*;
import java.io.*;

public class FCFS { //FCFS
    public static void main(String args[]) {
        int n, sum = 0;
        float total_tt = 0, total_waiting = 0;
        Scanner s = new Scanner(System.in);
        
        System.out.println("Enter Number Of Processes You Want to Execute:");
        n = s.nextInt();
        
        int arrival[] = new int[n];
        int cpu[] = new int[n];
        int finish[] = new int[n];
        int turntt[] = new int[n];
        int wait[] = new int[n];
        int process[] = new int[n]; 

        for (int i = 0; i < n; i++) {
            System.out.println("Enter arrival time of Process " + (i + 1) + ":");
            arrival[i] = s.nextInt();
            System.out.println("Enter CPU time of Process " + (i + 1) + ":");
            cpu[i] = s.nextInt();
            process[i] = i + 1;
        }

        for (int i = 0; i < n; i++) {
            sum = sum + cpu[i];
            finish[i] = sum;
        }

        for (int i = 0; i < n; i++) {
            turntt[i] = finish[i] - arrival[i];
            total_tt = total_tt + turntt[i];

            wait[i] = turntt[i] - cpu[i];
            total_waiting += wait[i];
        }

        System.out.println("\n\nProcess\t\tAT\tCPU_T");
        for (int i = 0; i < n; i++) {
            System.out.println(process[i] + "\t\t" + arrival[i] + "\t" + cpu[i]);
        }

        System.out.println("\n\n");
        System.out.println("Average Turnaround Time is: " + (total_tt / n));
        System.out.println("Average Waiting Time is: " + (total_waiting / n));
    }
}

