package Pass2;
import java.io.*;
import java.util.*;

class Pass2 {
    static Map<String, Integer> symbolTable = new HashMap<>();
    static Map<String, Integer> literalTable = new HashMap<>();

    public static void main(String args[]) {
        // Load Symbol Table
        loadSymbolTable("ST.txt");
        // Load Literal Table
        loadLiteralTable("LT.txt");
        // Generate Machine Code
        generateMachineCode("IM.txt", "MachineCode.txt");
    }

    private static void loadSymbolTable(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 3) {
                    String symbol = parts[1].trim();
                    int address = Integer.parseInt(parts[2].trim());
                    symbolTable.put(symbol, address);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadLiteralTable(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 3) {
                    String literal = parts[1].trim();
                    int address = Integer.parseInt(parts[2].trim());
                    literalTable.put(literal, address);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateMachineCode(String inputFile, String outputFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             PrintWriter writer = new PrintWriter(new File(outputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line);
                StringBuilder machineCode = new StringBuilder();
                while (st.hasMoreTokens()) {
                    String token = st.nextToken();
                    // Handle instructions
                    if (token.equals("IS")) {
                        // The next token will be the index of the instruction
                        if (st.hasMoreTokens()) {
                            int index = Integer.parseInt(st.nextToken());
                            machineCode.append(getOpcode(index)).append(" ");
                        }
                    } else if (token.equals("S")) {
                        // Handle symbols
                        if (st.hasMoreTokens()) {
                            String symbol = st.nextToken();
                            Integer address = symbolTable.get(symbol);
                            if (address != null) {
                                machineCode.append(address).append(" ");
                            } else {
                                machineCode.append("?? "); // Unknown symbol
                            }
                        }
                    } else if (token.equals("L")) {
                        // Handle literals
                        if (st.hasMoreTokens()) {
                            String literal = st.nextToken();
                            Integer address = literalTable.get(literal);
                            if (address != null) {
                                machineCode.append(address).append(" ");
                            } else {
                                machineCode.append("?? "); // Unknown literal
                            }
                        }
                    } else {
                        machineCode.append(token).append(" "); // Append other tokens directly
                    }
                }
                writer.println(machineCode.toString().trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getOpcode(int index) {
        switch (index) {
            case 1:
                return "01"; // Opcode for ADD
            case 2:
                return "02"; // Opcode for SUB
            case 3:
                return "03"; // Opcode for MUL
            case 4:
                return "04"; // Opcode for MOV
            default:
                return "00"; // Unknown opcode
        }
    }
}
