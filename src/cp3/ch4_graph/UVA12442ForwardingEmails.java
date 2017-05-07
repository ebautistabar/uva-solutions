package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 12442 - Just Graph Traversal, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3873
// Notes at the end: DFS, cycle detection
class UVA12442ForwardingEmails {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws Exception {
        int tests = Integer.parseInt(getLine().trim());
        for (int t = 1; t <= tests; t++) {
            int martians = Integer.parseInt(getLine().trim());
            int[] next = new int[martians];
            for (int i = 0; i < martians; i++) {
                StringTokenizer st = new StringTokenizer(getLine());
                int origin = Integer.parseInt(st.nextToken()) - 1;
                int destination = Integer.parseInt(st.nextToken()) - 1;
                next[origin] = destination;
            }

            int bestStart = -1;
            int bestChain = -1;
            boolean[] onStack = new boolean[martians];
            int[] reached = new int[martians];
            for (int i = 0; i < martians; i++) {
                if (reached[i] == 0) {
                    dfs(i, next, reached, onStack);
                    if (bestChain < reached[i]) {
                        bestChain = reached[i];
                        bestStart = i;
                    }
                }
            }
            out.printf("Case %d: %d%n", t, bestStart + 1);
        }
        out.close();
    }

    private static int dfs(int current, int[] next, int[] reached, boolean[] onStack) {
        if (onStack[current]) {
            // found cycle
            reached[current] = 0;
            return current; // return start of cycle
        }
        onStack[current] = true;
        int startOfCycle = dfs(next[current], next, reached, onStack);
        reached[current] = reached[next[current]] + 1;
        if (current == startOfCycle) {
            // all nodes in the cycle should have the same value == length of the cycle
            for (int i = next[startOfCycle]; i != startOfCycle; i = next[i]) {
                reached[i] = reached[startOfCycle];
            }
        }
        onStack[current] = false;
        return startOfCycle;
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
each martian has a sum of people it can reach
if the sum is empty, start calculation
recurse until cycle is found, marking the start of the cycle
go back, incrementing the sum of people reached of all the nodes in the way
when you reach the start of the cycle, make loop going forward in the cycle
 and setting all nodes of the cycle to the same sum of people reached
keep going back in the nodes before the cycle, updating sum
*/