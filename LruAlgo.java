import java.util.*;

class LruAlgo {
    int[] p, fr, fs;
    int n, m, pf = 0;

    Scanner src = new Scanner(System.in);

    void read() {
        System.out.println("Enter page table size:");
        n = src.nextInt();
        p = new int[n];
        System.out.println("Enter elements in page table:");
        for (int i = 0; i < n; i++) {
            p[i] = src.nextInt();
        }
        System.out.println("Enter page frame size:");
        m = src.nextInt();
        fr = new int[m];
        fs = new int[m];
        Arrays.fill(fr, -1);  // Initialize frame to -1
    }

    void display() {
        System.out.println("\nCurrent Frame Status:");
        for (int i = 0; i < m; i++) {
            if (fr[i] == -1) {
                System.out.print("[ ] ");
            } else {
                System.out.print("[" + fr[i] + "] ");
            }
        }
        System.out.println();
    }

    void lru() {
        for (int j = 0; j < n; j++) {
            boolean flag1 = false; // Page found in frame
            boolean flag2 = false; // Empty frame found

            // Check if the page is already in frame
            for (int i = 0; i < m; i++) {
                if (fr[i] == p[j]) {
                    flag1 = true;
                    flag2 = true;
                    break;
                }
            }

            // If page is not in frame, check for an empty frame
            if (!flag1) {
                for (int i = 0; i < m; i++) {
                    if (fr[i] == -1) {
                        fr[i] = p[j];
                        flag2 = true;
                        break;
                    }
                }
            }

            // If no empty frame, replace a page
            if (!flag2) {
                // Reset usage tracking
                Arrays.fill(fs, 0);
                // Mark the pages that are currently in frame
                for (int k = j - 1, l = 1; l <= m - 1; l++, k--) {
                    for (int i = 0; i < m; i++) {
                        if (fr[i] == p[k]) {
                            fs[i] = 1; // Page was used recently
                        }
                    }
                }
                // Find a page that was not used
                int index = -1;
                for (int i = 0; i < m; i++) {
                    if (fs[i] == 0) {
                        index = i;
                        break;
                    }
                }
                fr[index] = p[j]; // Replace the page
                pf++; // Increment page fault count
            }

            // Display the current page request and frame status
            System.out.print("Page: " + p[j] + " -> ");
            display();
        }
        System.out.println("\nNumber of page faults: " + pf);
    }

    public static void main(String[] args) {
        LruAlgo a = new LruAlgo();
        a.read();
        a.lru();
        a.display();
    }
}
