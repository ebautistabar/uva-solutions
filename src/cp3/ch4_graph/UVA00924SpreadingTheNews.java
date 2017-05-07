package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

// 924 - Single-Source Shortest Paths (SSSP) On Unweighted Graph: BFS, Easier, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=865
// Nice way to count the unvisited items in each level of BFS. Another option
//   is doing BFS tracking the distance from the source. Afterwards, count the
//   distance which is repeated most often. Another option is doing that, but
//   instead of looping over all distances at the end, we can accumulate the
//   amount of items with distance d in a map while doing the BFS traversal
class UVA00924SpreadingTheNews {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        int employees = Integer.parseInt(getLine().trim());
        List<List<Integer>> adjList = new ArrayList<>(employees);
        for (int i = 0; i < employees; i++) {
            StringTokenizer st = new StringTokenizer(getLine());
            int friends = Integer.parseInt(st.nextToken());
            adjList.add(new ArrayList<Integer>(friends));
            for (int j = 0; j < friends; j++) {
                adjList.get(i).add(Integer.parseInt(st.nextToken()));
            }
        }
        int tests = Integer.parseInt(getLine().trim());
        while (tests-- > 0) {
            int source = Integer.parseInt(getLine().trim());

            boolean[] visited = new boolean[employees];
            visited[source] = true;
            Queue<Integer> q = new ArrayDeque<>();
            q.add(source);
            int people = q.size();
            int day = 0;
            int maxPeople = 0;
            int bestDay = 0;
            while (!q.isEmpty()) {
                int current = q.remove();
                for (int next : adjList.get(current)) {
                    if (!visited[next]) {
                        visited[next] = true;
                        q.add(next);
                    }
                }
                people--;
                if (people == 0) {
                    // we have finished a "level" (or a "day"), a new one begins
                    people = q.size();
                    day++;
                    if (maxPeople < people) {
                        maxPeople = people;
                        bestDay = day;
                    }
                }
            }

            if (maxPeople == 0) {
                out.println('0');
            } else {
                out.printf("%d %d%n", maxPeople, bestDay);
            }
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
