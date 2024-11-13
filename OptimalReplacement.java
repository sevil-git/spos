import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OptimalReplacement {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int frames, pointer = 0, hit = 0, fault = 0, ref_len;
        boolean isFull = false;
        int buffer[];
        int reference[];
        int mem_layout[][];
        
        // Input number of frames
        System.out.print("Please enter the number of Frames: ");
        frames = Integer.parseInt(br.readLine());
        
        // Input length of reference string
        System.out.print("Please enter the length of the Reference string: ");
        ref_len = Integer.parseInt(br.readLine());
        
        // Initialize arrays
        reference = new int[ref_len];
        mem_layout = new int[ref_len][frames];
        buffer = new int[frames];
        
        // Initialize buffer
        for (int j = 0; j < frames; j++)
            buffer[j] = -1;
        
        // Input reference string
        System.out.println("Please enter the reference string: ");
        for (int i = 0; i < ref_len; i++) {
            reference[i] = Integer.parseInt(br.readLine());
        }
        
        System.out.println();
        
        // Process each page in the reference string
        for (int i = 0; i < ref_len; i++) {
            int search = -1;
            // Check if page is already in buffer (hit)
            for (int j = 0; j < frames; j++) {
                if (buffer[j] == reference[i]) {
                    search = j;
                    hit++;
                    break;
                }
            }
            
            // If page is not in buffer (miss)
            if (search == -1) {
                if (isFull) { // If buffer is full, apply optimal page replacement
                    int index[] = new int[frames];
                    boolean index_flag[] = new boolean[frames];
                    
                    for (int j = i + 1; j < ref_len; j++) {
                        for (int k = 0; k < frames; k++) {
                            if ((reference[j] == buffer[k]) && (index_flag[k] == false)) {
                                index[k] = j;
                                index_flag[k] = true;
                                break;
                            }
                        }
                    }
                    
                    int max = index[0];
                    pointer = 0;
                    
                    // Determine which page to replace
                    if (max == 0)
                        max = 200; // Arbitrary large number for comparison
                    for (int j = 0; j < frames; j++) {
                        if (index[j] == 0)
                            index[j] = 200;
                        if (index[j] > max) {
                            max = index[j];
                            pointer = j;
                        }
                    }
                }
                
                // Replace page in buffer
                buffer[pointer] = reference[i];
                fault++;
                
                // Update pointer
                if (!isFull) {
                    pointer++;
                    if (pointer == frames) {
                        pointer = 0;
                        isFull = true;
                    }
                }
            }
            
            // Update memory layout for display
            for (int j = 0; j < frames; j++)
                mem_layout[i][j] = buffer[j];
        }
        
        // Display memory layout
        for (int i = 0; i < frames; i++) {
            for (int j = 0; j < ref_len; j++)
                System.out.printf("%3d ", mem_layout[j][i]);
            System.out.println();
        }
        
        // Display results
        System.out.println("The number of Hits: " + hit);
        System.out.println("Hit Ratio: " + (float)((float)hit / ref_len));
        System.out.println("The number of Faults: " + fault);
    }
}
