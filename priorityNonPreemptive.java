
import java.util.Scanner;

public class priorityNonPreemptive {
    public static void main(String[] args) {
        int n;
        float totalTurnaroundTime = 0, totalWaitingTime = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of processes you want to execute: ");
        n = scanner.nextInt();

        int[] arrivalTime = new int[n];
        int[] cpuTime = new int[n];
        int[] priority = new int[n];
        int[] finishTime = new int[n];
        int[] turnaroundTime = new int[n];
        int[] waitingTime = new int[n];
        int[] processId = new int[n];

        // Input for each process
        for (int i = 0; i < n; i++) {
            System.out.println("Enter arrival time of process " + (i + 1) + ": ");
            arrivalTime[i] = scanner.nextInt();
            System.out.println("Enter CPU time of process " + (i + 1) + ": ");
            cpuTime[i] = scanner.nextInt();
            System.out.println("Enter priority of process " + (i + 1) + ": ");
            priority[i] = scanner.nextInt();
            processId[i] = i + 1; // Process IDs
        }

        // Sorting processes based on priority (higher priority has lower number)
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                // Sort based on priority, and if equal, by arrival time
                if (priority[i] > priority[j] || (priority[i] == priority[j] && arrivalTime[i] > arrivalTime[j])) {
                    // Swap CPU time
                    int temp = cpuTime[i];
                    cpuTime[i] = cpuTime[j];
                    cpuTime[j] = temp;

                    // Swap arrival time
                    temp = arrivalTime[i];
                    arrivalTime[i] = arrivalTime[j];
                    arrivalTime[j] = temp;

                    // Swap priority
                    temp = priority[i];
                    priority[i] = priority[j];
                    priority[j] = temp;

                    // Swap process IDs
                    temp = processId[i];
                    processId[i] = processId[j];
                    processId[j] = temp;
                }
            }
        }

        // Calculate finish time, turnaround time, and waiting time
        finishTime[0] = arrivalTime[0] + cpuTime[0]; // First process finishes immediately after its CPU time
        for (int i = 1; i < n; i++) {
            finishTime[i] = Math.max(finishTime[i - 1], arrivalTime[i]) + cpuTime[i];
        }

        for (int i = 0; i < n; i++) {
            turnaroundTime[i] = finishTime[i] - arrivalTime[i];
            totalTurnaroundTime += turnaroundTime[i];
            waitingTime[i] = turnaroundTime[i] - cpuTime[i];
            totalWaitingTime += waitingTime[i];
        }

        // Display process details
        System.out.println("\nProcess\tAT\tCPU_T\tPriority\tFinish\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            System.out.println(processId[i] + "\t" + arrivalTime[i] + "\t" + cpuTime[i] + "\t" + priority[i]
                    + "\t\t" + finishTime[i] + "\t" + turnaroundTime[i] + "\t" + waitingTime[i]);
        }

        System.out.println("\nTotal turnaround time: " + (totalTurnaroundTime / n));
        System.out.println("Total waiting time: " + (totalWaitingTime / n));
    }
}
