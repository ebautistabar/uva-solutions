package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

// 11340 - 1D Array Manipulation, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2315
class UVA11340Newspaper {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int n = Integer.parseInt(in.readLine());
        while (n-- > 0) {
            // we were supposed to use an array, but the judge input had
            // non-ascii chars so...
            Map<Character, Integer> value = new HashMap<>();
            int k = Integer.parseInt(in.readLine());
            for (int i = 0; i < k; i++) {
                String[] line = in.readLine().split("\\s+");
                value.put(line[0].charAt(0), Integer.parseInt(line[1]));
            }
            int m = Integer.parseInt(in.readLine());
            int money = 0;
            for (int i = 0; i < m; i++) {
                String line = in.readLine();
                for (int j = 0; j < line.length(); j++) {
                    money += value.getOrDefault(line.charAt(j), 0);
                }
            }
            int x = money / 100;
            int y = money % 100;
            out.printf("%d.%02d$%n", x, y);
        }
        out.close();
    }

}
