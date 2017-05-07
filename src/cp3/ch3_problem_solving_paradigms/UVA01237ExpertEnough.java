package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 1237 - Complete Search, Iterative (One Loop, Linear Scan), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3678
class UVA01237ExpertEnough {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        while (tests-- > 0) {
            int items = Integer.parseInt(in.readLine().trim()); // < 10K
            String[] makers = new String[items];
            int[] lowestPrices = new int[items];
            int[] highestPrices = new int[items];
            for (int i = 0; i < items; i++) {
                StringTokenizer st = new StringTokenizer(in.readLine());
                makers[i] = st.nextToken();
                lowestPrices[i] = Integer.parseInt(st.nextToken()); // > 0, < 1M
                highestPrices[i] = Integer.parseInt(st.nextToken()); // > 0, < 1M
            }
            int queries = Integer.parseInt(in.readLine().trim()); // < 1K
            for (int i = 0; i < queries; i++) {
                int price = Integer.parseInt(in.readLine().trim()); // > 0, < 1M
                // As inputs are small, CP3 suggests brute force
                int match = -1;
                for (int j = 0; j < items; j++) {
                    if (lowestPrices[j] <= price && price <= highestPrices[j]) {
                        if (match == -1) { // first match
                            match = j;
                        } else { // second match
                            match = -1;
                            break;
                        }
                    }
                }
                if (match == -1)
                    out.println("UNDETERMINED");
                else
                    out.println(makers[match]);
            }
            if (tests > 0)
                out.println();
        }
        out.close();
    }

}
