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
import java.util.Queue;
import java.util.StringTokenizer;

// 10801 - Single-Source Shortest Paths (SSSP) On Weighted Graph: Dijkstra's, Harder, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1742
// Dijkstra with a somewhat tricky graph modeling
class UVA10801LiftHopping {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int MAX_ELEVATORS = 5;
    private static final int MAX_FLOORS = 100;
    private static final int START_COST = 0;
    private static final int CHANGE_COST = 60;

    public static void main(String args[]) throws IOException {
        int[] time = new int[MAX_ELEVATORS * MAX_FLOORS];
        String line = getLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            int elevators = Integer.parseInt(st.nextToken());
            int goal = Integer.parseInt(st.nextToken());
            int[] speed = new int[elevators];
            st = new StringTokenizer(getLine());
            for (int i = 0; i < elevators; i++) {
                speed[i] = Integer.parseInt(st.nextToken());
            }
            List<List<Tuple>> adjList = new ArrayList<>(elevators * MAX_FLOORS);
            for (int i = 0; i < elevators * MAX_FLOORS; i++) {
                adjList.add(new ArrayList<Tuple>());
            }
            // edges for a particular elevator
            for (int elevator = 0; elevator < elevators; elevator++) {
                st = new StringTokenizer(getLine());
                int floor = Integer.parseInt(st.nextToken()) + elevator * MAX_FLOORS;
                while (st.hasMoreTokens()) {
                    int nextFloor = Integer.parseInt(st.nextToken()) + elevator * MAX_FLOORS;
                    int distance = nextFloor - floor;
                    adjList.get(floor).add(new Tuple(nextFloor, speed[elevator] * distance));
                    adjList.get(nextFloor).add(new Tuple(floor, speed[elevator] * distance));
                    floor = nextFloor;
                }
            }
            // edges between elevators
            for (int floor = 1; floor < MAX_FLOORS; floor++) {
                for (int elevator = 0; elevator < elevators; elevator++) {
                    for (int otherElev = elevator + 1; otherElev < elevators; otherElev++) {
                        int id1 = floor + elevator * MAX_FLOORS;
                        int id2 = floor + otherElev * MAX_FLOORS;
                        // if both stop on this floor
                        if (adjList.get(id1).size() > 0 && adjList.get(id2).size() > 0) {
                            adjList.get(id1).add(new Tuple(id2, CHANGE_COST));
                            adjList.get(id2).add(new Tuple(id1, CHANGE_COST));
                        }
                    }
                }
            }

            int totalTime = -1;
            int[] previous = new int[elevators * MAX_FLOORS];
            Arrays.fill(time, Integer.MAX_VALUE);
            Queue<Tuple> pq = new PriorityQueue<>();
            // enqueue the elevators that stop at floor 0
            for (int elevator = 0; elevator < elevators; elevator++) {
                int id = 0 + elevator * MAX_FLOORS;
                if (adjList.get(id).size() > 0) {
                    pq.add(new Tuple(id, START_COST));
                    time[id] = START_COST;
                    previous[id] = -1;
                }
            }
            while (!pq.isEmpty()) {
                Tuple current = pq.remove();
                if (time[current.node] < current.weight) continue; // know better path already
                if (current.node % MAX_FLOORS == goal) { // got to goal floor
                    totalTime = time[current.node];
                    break;
                }
                for (Tuple adj : adjList.get(current.node)) {
                    if (time[adj.node] > time[current.node] + adj.weight) {
                        time[adj.node] = time[current.node] + adj.weight;
                        pq.add(new Tuple(adj.node, time[adj.node]));
                        previous[adj.node] = current.node;
                    }
                }
            }

            out.println(totalTime == -1 ? "IMPOSSIBLE" : totalTime);
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

    private static class Tuple implements Comparable<Tuple> {
        int weight;
        int node;
        public Tuple(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }
        public int compareTo(Tuple arg0) {
            int wCmp = Integer.compare(weight, arg0.weight);
            return wCmp == 0 ? Integer.compare(node, arg0.node) : wCmp;
        }
        public String toString() {
            return String.format("[w:%d,n:%d]", weight, node);
        }
    }
}
/*
start 0
goal k
each elevator has a speed
we have a list of floors where a elevator stops, i.e. a list of floors connected by an elevator
there is an edge of weight speed_i*d between e_i,f_j and e_i,f_j+1, where d = f_j+1 - f_j
there is an edge of weight 60 between e_i,f_x and e_j,f_y if f_x==f_y

nodes are (elevator, floor), encoded in one integer like: floor + elevator * max_floors
edges are time measured in seconds
start from (x, 0)
stop on (y, k)
for any x and y

when the goal is reached, stop
afterwards, we cannot scan for the solution over all the elevators on floor goal,
  because the rest will still on the queue and thus have no valid time.
we must store the exact node id when we stop, and use it later to output the time
  for that exact node
*/
