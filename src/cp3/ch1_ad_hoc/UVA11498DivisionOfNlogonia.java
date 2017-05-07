package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 11498 - Super Easy, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2493
public class UVA11498DivisionOfNlogonia {

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String line = null;
        while ((line = in.readLine()) != null) {
            int k = Integer.parseInt(line);
            if (k > 0) {
                StringTokenizer tokenizer = new StringTokenizer(in.readLine());
                int n = Integer.parseInt(tokenizer.nextToken());
                int m = Integer.parseInt(tokenizer.nextToken());
                for (int i = 0; i < k; i++) {
                    tokenizer = new StringTokenizer(in.readLine());
                    int x = Integer.parseInt(tokenizer.nextToken());
                    int y = Integer.parseInt(tokenizer.nextToken());
                    if (n == x || m == y) {
                        out.println("divisa");
                    } else {
                        out.print(m > y ? "S" : "N");
                        out.println(n > x ? "O" : "E");
                    }
                }
            }
        }
        out.close();
    }

}
