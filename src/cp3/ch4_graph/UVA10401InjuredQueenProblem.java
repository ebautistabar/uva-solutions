package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

// 10401 - Special Graph (Directed Acyclic Graph), Counting paths in DAG, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1342
// Notes below
class UVA10401InjuredQueenProblem {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        String line = getLine();
        while (line != null) {
            line = line.trim();
            int cols = line.length();
            int rows = cols;
            long[][] ways = new long[rows][cols];
            for (int r = 0; r < rows; r++)
                ways[r][0] = 1;
            for (int c = 0; c < cols; c++) {
                // The queen can only go in the exact cell specified in the input
                if (line.charAt(c) != '?') {
                    int queenRow = getRow(line.charAt(c));
                    long currentWays = ways[queenRow][c];
                    for (int r = 0; r < rows; r++) {
                        ways[r][c] = 0;
                    }
                    ways[queenRow][c] = currentWays;
                }
                // As we haven't reached the end, let's update the totals in next column
                if (c < cols - 1) {
                    for (int thisColumnsRow = 0; thisColumnsRow < rows; thisColumnsRow++) {
                        for (int nextColumnsRow = 0; nextColumnsRow < rows; nextColumnsRow++) {
                            if (thisColumnsRow == nextColumnsRow // horizontal king move
                                    || nextColumnsRow == thisColumnsRow - 1 // diagonal king moves
                                    || nextColumnsRow == thisColumnsRow + 1) {
                                continue; // these 2 cells threat each other, so don't increase the count
                            }
                            ways[nextColumnsRow][c + 1] += ways[thisColumnsRow][c];
                        }
                    }
                }
            }

            long totalWays = 0;
            for (int r = 0; r < rows; r++) {
                totalWays += ways[r][cols - 1];
            }
            out.println(totalWays);
            line = getLine();
        }
        out.close();
    }

    private static int getRow(char c) {
        if (c >= '1' && c <= '9') {
            return c - '1'; // '1' => 0, '2' => 1, etc.
        } else {
            return c - 'A' + 9; // 'A' => 9, 'B' => 10, etc.
        }
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while (line != null && "".equals(line.trim())) // dealing with blank lines
            line = in.readLine();
        return line;
    }
}
/*
The CP3 book labels this problem as a DAG problem, with the accompanying explanations of topological sort, etc. It is
in fact a DAG problem but I think it's a bit counterintuitive to think of it like that. In my opinion it's more
intuitive to see it as a regular DP problem. Although it's true that it is a good exercise trying to understand how
the chessboard for this particular problem could be mapped to an explicit DAG and how the order in which we update
the DP table is indeed a toposort, as well as why it's not needed to calculate the toposort explicitly.

We need to calculate all the ways to set 1 queen on each column so they don't threat the rest. For that we can use
a function ways(r,c), which is the ways to set a queen in cell r,c so that it doesn't threat any other queen previously
set on the board. To get the final result, we can sum ways(r,last_column) for every row.

ways(r,c) = 0 if the problem input mandates to set a queen in a cell (rb,c) with rb != r, i.e. in another cell of this column
in any other case:
 ways(r,0) = 1
 ways(r,c) = sum of ways(rb,c-1) if c > 0, for rb not in {r-1, r, r+1} such that the resulting rb,c-1 cell is not out of bounds

We do bottom up, starting in the first column and fill the table column by column. Take note it's not necessary to get
an explicit toposort, as the order in which the states (i.e. nodes) is visited is clear. We visit the nodes column by
column, i.e. layer by layer in a DAG context.

Space saving trick could be used, but no need as the table is very small.
*/
