import java.util.*;

public class NonPreemptiveSJF {
    static class Process {
        int id;             // Process ID
        int arrivalTime;    // Arrival time
        int burstTime;      // Burst time
        int finishTime;     // Finish time
        int turnAroundTime; // Turnaround time
        int waitingTime;    // Waiting time

        Process(int id, int arrivalTime, int burstTime) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        Process[] processes = new Process[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time of process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Enter burst time of process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            processes[i] = new Process(i + 1, arrivalTime, burstTime);
        }

        // Execute Non-Preemptive SJF Scheduling
        executeNonPreemptiveSJF(processes);
    }

    public static void executeNonPreemptiveSJF(Process[] processes) {
        int n = processes.length;
        int currentTime = 0;
        int completedProcesses = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        // Sort processes based on arrival time
        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));

        while (completedProcesses < n) {
            // Find the index of the process with the shortest burst time that has arrived
            int idx = -1;
            int minBurstTime = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (processes[i].arrivalTime <= currentTime && processes[i].burstTime > 0) {
                    if (processes[i].burstTime < minBurstTime) {
                        minBurstTime = processes[i].burstTime;
                        idx = i;
                    }
                }
            }

            // If no process is found, increment the current time
            if (idx == -1) {
                currentTime++;
                continue;
            }

            // Execute the selected process
            Process currentProcess = processes[idx];
            currentTime += currentProcess.burstTime;
            currentProcess.finishTime = currentTime;
            currentProcess.turnAroundTime = currentProcess.finishTime - currentProcess.arrivalTime;
            currentProcess.waitingTime = currentProcess.turnAroundTime - currentProcess.burstTime;

            // Accumulate waiting and turnaround times
            totalWaitingTime += currentProcess.waitingTime;
            totalTurnaroundTime += currentProcess.turnAroundTime;

            // Mark the process as completed
            currentProcess.burstTime = 0; // Mark as finished
            completedProcesses++;
            System.out.println("Process " + currentProcess.id + " completed at time " + currentProcess.finishTime);
        }

        // Calculate and print average turnaround time and waiting time
        System.out.println("\nAverage Turnaround Time: " + (float) totalTurnaroundTime / n);
        System.out.println("Average Waiting Time: " + (float) totalWaitingTime / n);
    }
}
