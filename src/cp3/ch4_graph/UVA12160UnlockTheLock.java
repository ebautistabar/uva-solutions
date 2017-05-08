package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

// 12160 - Single-Source Shortest Paths (SSSP) On Unweighted Graph: BFS, Harder, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3312
// Direct application of BFS, notes below
class UVA12160UnlockTheLock {

    private static final int NUM_LIMIT = 10_000;
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        int[] distance = new int[NUM_LIMIT];
        int test = 1;
        String line = getLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            int startLock = Integer.parseInt(st.nextToken());
            int unlock = Integer.parseInt(st.nextToken());
            int buttons = Integer.parseInt(st.nextToken());
            if (startLock == 0 && unlock == 0 && buttons == 0) break;
            st = new StringTokenizer(getLine());
            int[] buttonValues = new int[buttons];
            for (int i = 0; i < buttons; i++) {
                buttonValues[i] = Integer.parseInt(st.nextToken());
            }

            Arrays.fill(distance, Integer.MAX_VALUE);
            distance[startLock] = 0;
            Queue<Integer> q = new ArrayDeque<>();
            q.add(startLock);
            while (!q.isEmpty() && distance[unlock] == Integer.MAX_VALUE) {
                int current = q.remove();
                for (int i = 0; i < buttons; i++) {
                    int next = (current + buttonValues[i]) % NUM_LIMIT;
                    if (distance[next] == Integer.MAX_VALUE) {
                        distance[next] = distance[current] + 1;
                        q.add(next);
                    }
                }
            }

            if (distance[unlock] == Integer.MAX_VALUE) {
                out.printf("Case %d: Permanently Locked%n", test);
            } else {
                out.printf("Case %d: %d%n", test, distance[unlock]);
            }
            test++;
            line = getLine();
        }
        out.close();
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
Exploring how to determine the type of problem... The goal is to minimize number
of movements. If we see the movements as transitions, it implies there's a state
space. There exist start and end states. It cannot be solved with DP because
there *may* be cycles.
*/
