package Pass1Micro;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class Pass1Micro {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("macro_input.asm"));

        FileWriter mnt = new FileWriter("mnt.txt");
        FileWriter mdt = new FileWriter("mdt.txt");
        FileWriter pnt = new FileWriter("pntab.txt");
        
        LinkedHashMap<String, Integer> pntab = new LinkedHashMap<>();
        String line;
        String macroname = null;
        int mdtp = 1, paramNo = 1, pp = 0, kp = 0, flag = 0;
        
        while ((line = br.readLine()) != null) {
            String parts[] = line.split("\\s+");

            if (parts[0].equalsIgnoreCase("MACRO")) {
                flag = 1;
                line = br.readLine();
                parts = line.split("\\s+");
                macroname = parts[0];
                if (parts.length <= 1) {
                    mnt.write(parts[0] + "\t" + pp + "\t" + kp + "\t" + mdtp + "\t" + "\n");
                    continue;
                }
                for (int i = 1; i < parts.length; i++) {
                    parts[i] = parts[i].replaceAll("[&,]", "");

                    if (parts[i].contains("=")) {
                        String keywordParam[] = parts[i].split("=");
                        pntab.put(keywordParam[0], paramNo++);
                    } else {
                        pntab.put(parts[i], paramNo++);
                        pp++;
                    }
                }
                mnt.write(parts[0] + "\t" + pp + "\t" + kp + "\t" + mdtp + "\t" + "\n");
                
            } else if (parts[0].equalsIgnoreCase("MEND")) {
                mdt.write(line + "\n");
                flag = kp = pp = 0;
                mdtp++;
                pnt.write(macroname + ":\t");
                Iterator<String> itr = pntab.keySet().iterator();
                while (itr.hasNext()) {
                    pnt.write(itr.next() + "\t");
                }
                pnt.write("\n");
                pntab.clear();
            } else if (flag == 1) {
                for (int i = 0; i < parts.length; i++) {
                    if (parts[i].contains("&")) {
                        parts[i] = parts[i].replaceAll("[&,]", "");
                        mdt.write("(P," + pntab.get(parts[i]) + ")\t");
                    } else {
                        mdt.write(parts[i] + "\t");
                    }
                }
                mdt.write("\n");
                mdtp++;
            }
        }
        
        br.close();
        mdt.close();
        mnt.close();
        pnt.close();
        System.out.println("Macro Pass1 Processing done. :)");
    }
}

