import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Pass2Micro {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("macro_input.asm"));
        BufferedReader mntReader = new BufferedReader(new FileReader("mnt.txt"));
        BufferedReader mdtReader = new BufferedReader(new FileReader("mdt.txt"));
        FileWriter outputWriter = new FileWriter("output.asm");

        // Load MNT
        List<MacroEntry> mnt = new ArrayList<>();
        String mntLine;
        while ((mntLine = mntReader.readLine()) != null) {
            String[] parts = mntLine.split("\\s+");
            String macroName = parts[0];
            int pp = Integer.parseInt(parts[1]); // positional parameters
            int kp = Integer.parseInt(parts[2]); // keyword parameters
            int mdtp = Integer.parseInt(parts[3]); // starting address in MDT
            mnt.add(new MacroEntry(macroName, pp, kp, mdtp));
        }

        // Load MDT
        List<String> mdt = new ArrayList<>();
        String mdtLine;
        while ((mdtLine = mdtReader.readLine()) != null) {
            mdt.add(mdtLine);
        }

        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\\s+");

            // Check for macro calls
            for (String part : parts) {
                MacroEntry macroEntry = getMacroEntry(part, mnt);
                if (macroEntry != null) {
                    // If it is a macro call, process it
                    List<String> paramValues = getParameterValues(parts, macroEntry);

                    // Retrieve the macro definition
                    StringBuilder expandedMacro = new StringBuilder();
                    for (int i = 0; i < mdt.size(); i++) {
                        String mdtEntry = mdt.get(macroEntry.mdtp - 1 + i); // -1 for 0-based index
                        if (mdtEntry.contains("MEND")) {
                            expandedMacro.append(mdtEntry).append("\n");
                            break;
                        }

                        // Replace parameters in macro definition
                        for (int j = 0; j < macroEntry.pp; j++) {
                            mdtEntry = mdtEntry.replace("(P," + (j + 1) + ")", paramValues.get(j));
                        }

                        // If there are keyword parameters, handle them
                        for (int j = 0; j < macroEntry.kp; j++) {
                            mdtEntry = mdtEntry.replace("(K," + (j + 1) + ")", paramValues.get(macroEntry.pp + j));
                        }

                        expandedMacro.append(mdtEntry).append("\n");
                    }

                    // Write the expanded macro to output
                    outputWriter.write(expandedMacro.toString());
                } else {
                    // If not a macro call, write the original line
                    outputWriter.write(line + "\n");
                }
            }
        }

        br.close();
        mntReader.close();
        mdtReader.close();
        outputWriter.close();
        System.out.println("Macro Pass2 Processing done. :)");
    }

    private static MacroEntry getMacroEntry(String part, List<MacroEntry> mnt) {
        for (MacroEntry entry : mnt) {
            if (entry.macroName.equalsIgnoreCase(part)) {
                return entry;
            }
        }
        return null;
    }

    private static List<String> getParameterValues(String[] parts, MacroEntry macroEntry) {
        List<String> values = new ArrayList<>();
        int totalParams = macroEntry.pp + macroEntry.kp; // Total number of parameters

        // Extract parameter values from parts
        for (int i = 0; i < totalParams; i++) {
            if (i < parts.length) { // Check if index is within bounds
                values.add(parts[i]);
            } else {
                values.add(""); // Add an empty string if no value is provided for optional parameters
            }
        }
        return values;
    }

    // MacroEntry class to hold macro information
    static class MacroEntry {
        String macroName;
        int pp; // Positional parameters
        int kp; // Keyword parameters
        int mdtp; // Address in MDT

        public MacroEntry(String macroName, int pp, int kp, int mdtp) {
            this.macroName = macroName;
            this.pp = pp;
            this.kp = kp;
            this.mdtp = mdtp;
        }
    }
}
