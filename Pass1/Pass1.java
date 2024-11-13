import java.io.*;
import java.util.*;

class Pass1 {
    static int address = 0;
    static int sadd[] = new int[10];
    static int ladd[] = new int[10];

    public static void main(String args[]) {
        BufferedReader br;
        String input = null;

        String IS[] = {"ADD", "SUB", "MUL", "MOV"};
        String UserReg[] = {"AREG", "BREG", "CREG", "DREG"};
        String AD[] = {"START", "END"};
        String DL[] = {"DC", "DS"};
        int lc = 0;
        int scount = 0, lcount = 0;
        int flag = 0;

        String sv[] = new String[10];
        String lv[] = new String[10];

        try {
            br = new BufferedReader(new FileReader("initial.txt"));
            PrintWriter imWriter = new PrintWriter(new File("IM.txt"));
            PrintWriter stWriter = new PrintWriter(new File("ST.txt"));
            PrintWriter ltWriter = new PrintWriter(new File("LT.txt"));

            while ((input = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(input, " ");
                while (st.hasMoreTokens()) {
                    String tt = st.nextToken();

                    // Check for START directive
                    if (tt.equals("START")) {
                        lc = Integer.parseInt(st.nextToken());
                        address = lc;
                        imWriter.println("START " + lc);
                    } else if (tt.equals("END")) {
                        imWriter.println("END");
                    } else if (Arrays.asList(IS).contains(tt)) {
                        int index = Arrays.asList(IS).indexOf(tt);
                        imWriter.print("IS " + (index + 1) + " ");
                    } else if (Arrays.asList(DL).contains(tt)) {
                        int index = Arrays.asList(DL).indexOf(tt);
                        imWriter.print("DL " + (index + 1) + " ");
                    } else if (tt.length() == 1 && Character.isLetter(tt.charAt(0))) { // Single letter symbol
                        boolean found = false;
                        for (int i = 0; i < scount; i++) {
                            if (sv[i].equals(tt)) {
                                imWriter.print("S" + i + " ");
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            sv[scount] = tt;
                            imWriter.print("S" + scount + " ");
                            sadd[scount] = address; // Save address for symbols
                            scount++;
                        }
                    } else if (tt.charAt(0) == '=') { // Literal handling
                        int index = lcount++;
                        lv[index] = tt;
                        imWriter.print("L" + index + " ");
                        ladd[index] = address; // Save address for literals
                    } else if (tt.equals("DS")) { // Declare Space
                        int size = Integer.parseInt(st.nextToken());
                        address += size - 1; // Update address
                        imWriter.println(); // New line for DS
                    } else { // Assume it's a variable or instruction with no specific type
                        imWriter.print(tt + " ");
                    }
                }
                address++; // Increment LC for the next line
                imWriter.println(); // Move to the next line in IM.txt
            }

            // Write Symbol Table
            for (int i = 0; i < scount; i++) {
                stWriter.println(i + "\t" + sv[i] + "\t" + sadd[i]);
            }

            // Write Literal Table
            for (int i = 0; i < lcount; i++) {
                ltWriter.println(i + "\t" + lv[i] + "\t" + ladd[i]);
            }

            br.close();
            imWriter.close();
            stWriter.close();
            ltWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
