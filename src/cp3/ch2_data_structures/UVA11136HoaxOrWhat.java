package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

// 11136 - C++ STL set (Java TreeSet), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2077
class UVA11136HoaxOrWhat {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(System.out);

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null && !"0".equals(line.trim())) {
            // CP3 suggests using C++ multiset. Using TreeMap instead to
            // record the number of appearances of every key
            TreeMap<Integer, Integer> amounts = new TreeMap<>();
            // total can get pretty high, so need to use long
            long promotionCost = 0;
            int days = Integer.parseInt(line.trim());
            for (int i = 0; i < days; i++) {
                // Read the bills for today
                StringTokenizer st = new StringTokenizer(in.readLine());
                int bills = Integer.parseInt(st.nextToken());
                for (int j = 0; j < bills; j++) {
                    int amount = Integer.parseInt(st.nextToken());
                    int count = amounts.getOrDefault(amount, 0);
                    amounts.put(amount, count + 1);
                }
                // Update cost of promotion
                int cheapest = extractCheapest(amounts);
                int dearest = extractDearest(amounts);
                promotionCost += dearest - cheapest;
            }
            out.println(promotionCost);
            line = in.readLine();
        }
        out.flush();
    }

    private static int extractCheapest(TreeMap<Integer, Integer> amounts) {
        Map.Entry<Integer, Integer> cheapest = amounts.firstEntry();
        return extractAmount(amounts, cheapest);
    }

    private static int extractAmount(TreeMap<Integer, Integer> amounts,
            Map.Entry<Integer, Integer> amount) {
        int cost = amount.getKey();
        int times = amount.getValue();
        if (times == 1)
            amounts.remove(cost);
        else
            amounts.put(cost, times - 1);
        return cost;
    }

    private static int extractDearest(TreeMap<Integer, Integer> amounts) {
        Map.Entry<Integer, Integer> dearest = amounts.lastEntry();
        return extractAmount(amounts, dearest);
    }
}
