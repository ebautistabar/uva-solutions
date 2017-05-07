package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 10812 - Interesting Real Life Problems, Easier, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1753
class UVA10812BeatTheSpread {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int t = Integer.parseInt(in.readLine());
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(in.readLine());
            int sum = Integer.parseInt(st.nextToken());
            int difference = Integer.parseInt(st.nextToken());
            // x + y = sum; x = sum - y
            // x - y = diff; sum - 2y = diff; (sum - diff) / 2 = y
            if (difference > sum) {
                out.println("impossible");
            } else {
                double y = (sum - difference) / 2.0;
                if (y == (int)y) {
                    int x = sum - (int)y;
                    out.println(x + " " + (int)y);
                } else {
                    out.println("impossible");
                }
            }
        }
        out.close();
    }

}
