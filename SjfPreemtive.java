
import java.util.*;

public class SjfPreemtive {
    static class Process {
        int id;         // Process ID
        int arrivalTime; // Arrival time
        int burstTime;   // Burst time
        int remainingTime; // Remaining time for the process

        Process(int id, int arrivalTime, int burstTime) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.remainingTime = burstTime;
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

        // Execute Preemptive SJF Scheduling
        executePreemptiveSJF(processes);
    }

    public static void executePreemptiveSJF(Process[] processes) {
        int n = processes.length;
        int currentTime = 0;
        int completedProcesses = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        // Create a priority queue to hold processes by remaining time
        PriorityQueue<Process> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.remainingTime));

        while (completedProcesses < n) {
            // Add all processes that have arrived by current time to the queue
            for (Process process : processes) {
                if (process.arrivalTime <= currentTime && process.remainingTime > 0) {
                    pq.add(process);
                }
            }

            // If the priority queue is empty, advance time
            if (pq.isEmpty()) {
                currentTime++;
                continue;
            }

            // Get the process with the smallest remaining time
            Process currentProcess = pq.poll();
            currentProcess.remainingTime--;

            // If the process is completed
            if (currentProcess.remainingTime == 0) {
                completedProcesses++;
                int turnaroundTime = currentTime + 1 - currentProcess.arrivalTime;
                int waitingTime = turnaroundTime - currentProcess.burstTime;

                totalTurnaroundTime += turnaroundTime;
                totalWaitingTime += waitingTime;

                System.out.println("Process " + currentProcess.id + " completed at time " + (currentTime + 1));
            }

            // Increment current time
            currentTime++;
        }

        // Calculate and print average turnaround time and waiting time
        System.out.println("\nAverage Turnaround Time: " + (float) totalTurnaroundTime / n);
        System.out.println("Average Waiting Time: " + (float) totalWaitingTime / n);
    }
}
