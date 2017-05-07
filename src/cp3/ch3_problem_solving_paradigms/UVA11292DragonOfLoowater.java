package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

// 11292 - Greedy, Involving Sorting (Or The Input Is Already Sorted), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2267
class UVA11292DragonOfLoowater {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            int heads = Integer.parseInt(st.nextToken());
            int knights = Integer.parseInt(st.nextToken());
            if (heads != 0 && knights != 0) {
                int[] diameter = new int[heads];
                for (int i = 0; i < diameter.length; i++) {
                    diameter[i] = Integer.parseInt(in.readLine().trim());
                }
                Arrays.sort(diameter);
                int[] height = new int[knights];
                for (int i = 0; i < height.length; i++) {
                    height[i] = Integer.parseInt(in.readLine().trim());
                }
                Arrays.sort(height);
                int coins = 0;
                int head = 0, knight = 0;
                while (head < diameter.length && knight < height.length) {
                    if (diameter[head] <= height[knight]) {
                        head++;
                        coins += height[knight];
                    }
                    knight++;
                }
                if (head < diameter.length) {
                    out.println("Loowater is doomed!");
                } else {
                    out.println(coins);
                }
            }
            line = in.readLine();
        }
        out.close();
    }

}
