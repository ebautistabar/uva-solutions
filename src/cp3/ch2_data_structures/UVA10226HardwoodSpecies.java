package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

// 10226 - C++ STL map (Java TreeMap), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1167
class UVA10226HardwoodSpecies {

    private static final DecimalFormat df = buildDecimalFormat();
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(System.out);

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        in.readLine(); // blank line
        while (tests-- > 0) {
            double total = 0;
            Map<String, Double> counts = new TreeMap<String, Double>();
            String tree = in.readLine();
            while (tree != null && !"".equals(tree.trim())) {
                Double count = counts.get(tree);
                if (count == null)
                    count = 0.0;
                counts.put(tree, count + 1);
                total++;
                tree = in.readLine();
            }
            for (Map.Entry<String, Double> pair : counts.entrySet()) {
                String species = pair.getKey();
                String percentage = df.format(pair.getValue() / total * 100);
                out.printf("%s %s\n", species, percentage);
            }
            if (tests > 0)
                out.println();
        }
        out.flush();
    }

    private static DecimalFormat buildDecimalFormat() {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat df = (DecimalFormat) nf;
        df.applyPattern("#0.0000");
        return df;
    }

}
