package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// 12047 - Single-Source Shortest Paths (SSSP) On Weighted Graph: Dijkstra's, Harder, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3198
// Dijkstra from source and goal, notes at the bottom
class UVA12047HighestPaidToll {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        int tests = Integer.parseInt(getLine().trim());
        for (int t = 1; t <= tests; t++) {
            StringTokenizer st = new StringTokenizer(getLine());
            int places = Integer.parseInt(st.nextToken());
            int roads = Integer.parseInt(st.nextToken());
            int start = Integer.parseInt(st.nextToken()) - 1;
            int goal = Integer.parseInt(st.nextToken()) - 1;
            int money = Integer.parseInt(st.nextToken());
            List<List<Tuple>> adjList = new ArrayList<>();
            List<List<Tuple>> reverseAdjList = new ArrayList<>();
            for (int i = 0; i < places; i++) {
                adjList.add(new ArrayList<Tuple>());
                reverseAdjList.add(new ArrayList<Tuple>());
            }
            for (int i = 0; i < roads; i++) {
                st = new StringTokenizer(getLine());
                int from = Integer.parseInt(st.nextToken()) - 1; // 1-based
                int to = Integer.parseInt(st.nextToken()) - 1;
                int cost = Integer.parseInt(st.nextToken());
                adjList.get(from).add(new Tuple(to, cost));
                reverseAdjList.get(to).add(new Tuple(from, cost));
            }

            long[] distanceFromStart = getDistanceFrom(start, adjList);
            long[] distanceFromGoal = getDistanceFrom(goal, reverseAdjList);

            long maxCost = -1;
            for (int i = 0; i < places; i++) {
                for (int j = 0; j < adjList.get(i).size(); j++) {
                    int next = adjList.get(i).get(j).place;
                    if (distanceFromStart[i] < Long.MAX_VALUE
                            && distanceFromGoal[next] < Long.MAX_VALUE) {
                        long roadCost = adjList.get(i).get(j).cost;
                        long totalCost = distanceFromStart[i] + roadCost + distanceFromGoal[next];
                        if (maxCost < roadCost && totalCost <= money) {
                            maxCost = roadCost;
                        }
                    }
                }
            }

            out.println(maxCost);
        }
        out.close();
    }

    private static long[] getDistanceFrom(int start, List<List<Tuple>> adjList) {
        long[] distance = new long[adjList.size()];
        Arrays.fill(distance, Long.MAX_VALUE);
        PriorityQueue<Tuple> pq = new PriorityQueue<Tuple>();
        pq.add(new Tuple(start, 0));
        distance[start] = 0;
        while (!pq.isEmpty()) {
            Tuple current = pq.remove();
            if (distance[current.place] < current.cost) continue;
            for (Tuple next : adjList.get(current.place)) {
                if (distance[next.place] > distance[current.place] + next.cost) {
                    distance[next.place] = distance[current.place] + next.cost;
                    pq.add(new Tuple(next.place, distance[next.place]));
                }
            }
        }
        return distance;
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }

    private static class Tuple implements Comparable<Tuple> {
        int place;
        long cost;
        public Tuple(int place, long cost) {
            this.place = place;
            this.cost = cost;
        }
        public int compareTo(Tuple o) {
            return Long.compare(cost, o.cost);
        }
        public String toString() {
            return String.format("[%d,%d]", place, cost);
        }
    }
}
/*
overall we want the cheapest path, so it fits the budget
but the path must contain the biggest edge that is possible
for each edge u,v
    path from s to u
    path from v to t
    sum both paths and edge
    if it fits the budget and the edge is the best so far take it

to get paths, run dijkstra from start without stopping
and run dijkstra from goal backwards without stopping (with reverse graph)

that way we know the best path from start to every other node
and from goal to every other node (or from every node to the goal)
*/
