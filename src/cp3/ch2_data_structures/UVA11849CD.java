package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

// 11849 - C++ STL set (Java TreeSet), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2949
class UVA11849CD {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(System.out);

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null && !"0 0".equals(line.trim())) {
            String[] params = line.trim().split("\\s+");
            int jackCds = Integer.parseInt(params[0]);
            int jillCds = Integer.parseInt(params[1]);
            Set<Integer> jackCollection = new HashSet<>();
            for (int i = 0; i < jackCds; i++) {
                int id = Integer.parseInt(in.readLine().trim());
                jackCollection.add(id);
            }
            int cdsToSell = 0;
            for (int i = 0; i < jillCds; i++) {
                int id = Integer.parseInt(in.readLine().trim());
                if (jackCollection.contains(id))
                    cdsToSell++;
            }
            out.println(cdsToSell);
            line = in.readLine();
        }
        out.flush();
    }

}
