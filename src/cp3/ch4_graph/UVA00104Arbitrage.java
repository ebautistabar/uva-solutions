package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 00104 - All Pairs Shortest Paths, Variants, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=40
// Floyd Warshall, notes below
class UVA00104Arbitrage {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final double TARGET_PROFIT = 1.01;

    public static void main(String args[]) throws IOException {
        String line = getLine();
        while (line != null) {
            int size = Integer.parseInt(line.trim());
            double[][] rate = new double[size][size];
            for (int i = 0; i < size; i++) {
                StringTokenizer st = new StringTokenizer(getLine());
                for (int j = 0; j < size; j++) {
                    if (i == j) {
                        rate[i][j] = 1;
                    } else {
                        rate[i][j] = Double.parseDouble(st.nextToken());
                    }
                }
            }

            if (!findAndPrintSequence(rate)) {
                out.println("no arbitrage sequence exists");
            }

            line = getLine();
        }
        out.close();
    }

    private static boolean findAndPrintSequence(double[][] rate) {
        int sequenceLimit = rate.length;
        int currencies = rate.length;
        // DP table that stores profit. profit[c][i][j] is the profit obtained in c steps from
        //  currency i to currency j
        double[][][] profit = new double[sequenceLimit][currencies][currencies];
        // Aux. table that stores the actual path. previous[c][i][j] stores the previous currency
        //  to j in a path of c steps starting in currency i
        int[][][] previous = new int[sequenceLimit][currencies][currencies];
        // Initialize tables. Only paths with 1 conversion for the time being
        // For the rest of conversions:
        // - profit is 0, which is always worse than any profit that can be calculated with the
        // given input and thus will let us assign something on the first iteration of k
        // - we don't care about previous, as it's never read, only assigned
        for (int i = 0; i < currencies; i++) {
            for (int j = 0; j < currencies; j++) {
                profit[0][i][j] = rate[i][j];
                previous[0][i][j] = i;
            }
        }
        // Traversal of DP states
        for (int c = 1; c < sequenceLimit; c++) { // number of conversions from start to goal
            for (int i = 0; i < currencies; i++) { // starting currency
                for (int j = 0; j < currencies; j++) { // final currency
                    // Calculate value of this state
                    for (int k = 0; k < rate.length; k++) {
                        double profitCandidate = profit[c - 1][i][k] * rate[k][j];
                        if (profit[c][i][j] < profitCandidate) {
                            profit[c][i][j] = profitCandidate;
                            previous[c][i][j] = k;
                            //out.printf("%d %d %d %d %f %n", c+1, i+1, j+1, k, profit[c][i][j]);
                        }
                    }
                    // As we want to minimize number of conversions, and we start from
                    //  the minimum number of conversions upwards, we can stop as soon
                    //  as we find one that matches our criteria: start and goal must
                    //  be the same, and we want to reach at least a particular profit.
                    // Important: the check must be done after we have fully processed
                    //  the state, as we want to maximize the profit for each state.
                    //  I think this is just a quirk of the checker, not mentioned in the
                    //  statement.
                    if (i == j && profit[c][i][j] >= TARGET_PROFIT) {
                        print(previous, c, i, i);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void print(int[][][] previous, int conversions, int start, int goal) {
        // The path cannot be output in reverse order. No "smart" checker for this problem
        int[] path = new int[conversions + 2];
        int tail = goal;
        path[conversions + 1] = start;
        for (int c = conversions; c >= 0; c--) {
            path[c] = previous[c][start][tail];
            tail = previous[c][start][tail];
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.length; i++) {
            sb.append(path[i] + 1); // 1-based
            sb.append(' ');
        }
        sb.setLength(sb.length() - 1);
        out.println(sb);
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }
}
/*
Need to minimize the number of conversions while at the same time maximizing profit. The solution
has some resemblance to Floyd Warshall. We could just minimize the number of conversions, but it
doesn't guarantee the needed profit. Alternatively, we could maximize the profit, but it doesn't
guarantee the minimum number of conversions. We must consider both.

Let's consider it from the DP angle. We want to maximize the profit, and our state is
the starting currency, the final currency and the number of conversions so far.
The recursive case is:
 f(i,j,c) = max( f(i,k,c-1) * rate(k,j) for each k from 0 to j)
The base case is:
 f(i,j,1) = rate(i,j)
The solution is:
 f(i,i,c) that reaches the profit target and minimizes c
*/
