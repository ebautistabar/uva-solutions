package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

// 10765 - Finding Articulation Points/Bridges, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1706
// Notes at the bottom
class UVA10765DovesAndBombs {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        String line = getLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            int stations = Integer.parseInt(st.nextToken());
            int candidates = Integer.parseInt(st.nextToken());
            if (stations == 0 && candidates == 0) break;
            List<List<Integer>> adjList = new ArrayList<>();
            for (int i = 0; i < stations; i++) adjList.add(new ArrayList<Integer>());
            st = new StringTokenizer(getLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            while (a != -1 && b != -1) {
                adjList.get(a).add(b);
                adjList.get(b).add(a);
                st = new StringTokenizer(getLine());
                a = Integer.parseInt(st.nextToken());
                b = Integer.parseInt(st.nextToken());
            }

            List<Station> toBomb = getStationsToBomb(adjList, candidates);

            Collections.sort(toBomb);
            for (int i = 0; i < candidates; i++) {
                Station station = toBomb.get(i);
                out.printf("%s%n", station);
            }
            out.println();
            line = getLine();
        }
        out.close();
    }

    private static List<Station> getStationsToBomb(List<List<Integer>> adjList, int candidates) {
        List<Station> stations = new ArrayList<>();
        int connectedComponents = 0;
        int[] depth = new int[adjList.size()];
        int[] lowpoint = new int[adjList.size()];
        int[] parent = new int[adjList.size()];
        int[] increase = new int[adjList.size()];
        Arrays.fill(parent, -1); // 0 is a valid parent, so fill with -1
        for (int i = 0; i < depth.length; i++) {
            if (depth[i] == 0) { // unvisited
                connectedComponents++;
                int currentDepth = 1;
                findArticulationPoints(i, currentDepth, adjList, depth,
                        lowpoint, parent, increase);
            }
        }
        // include every station in the list, in order to fill the quota, as it
        // may not be enough with just the articulation points
        for (int i = 0; i < adjList.size(); i++) {
            stations.add(new Station(i, connectedComponents + increase[i]));
        }
        return stations;
    }

    private static void findArticulationPoints(int node, int currentDepth,
            List<List<Integer>> adjList, int[] depth, int[] lowpoint,
            int[] parent, int[] increase) {
        int childCount = 0;
        depth[node] = lowpoint[node] = currentDepth;
        for (int neighbor : adjList.get(node)) {
            if (depth[neighbor] == 0) { // unvisited
                parent[neighbor] = node;
                findArticulationPoints(neighbor, currentDepth + 1, adjList,
                        depth, lowpoint, parent, increase);
                childCount++;
                if (lowpoint[neighbor] >= depth[node]) {
                    // this condition means that 'node' is the lowest node that
                    // this neighbor and all its descendants can reach, i.e.
                    // this is their connection to the rest of the graph,
                    // and thus it's an articulation point
                    increase[node]++;
                    // increase will contain the number of new components that
                    // will be created when splitting the graph at this point.
                    // the parent of this node represents the original component
                    // (we will never reach this code with the parent, as it's
                    // already visited by definition, i.e. the parent won't be
                    // counted as an increase).
                    // the increase corresponds to the new components. Basically
                    // it's one new component per children, with the exception
                    // of the children which complete a cycle (i.e. when we
                    // try to process them, they are already processed)
                }
                // The neighbor and its descendants may have reached an even
                // lower point, so we update our lowpoint just in case
                lowpoint[node] = Math.min(lowpoint[node], lowpoint[neighbor]);
            } else if (neighbor != parent[node]) { // it's a cycle, but not direct back edge
                // This is the case where we have a cycle and thus update our
                // lowpoint with the depth of the node which starts the cycle
                lowpoint[node] = Math.min(lowpoint[node], depth[neighbor]);
            }
        }
        // The root is a special case: at this point the algorithm always thinks
        // it's an articulation point, but that's only possible when it has
        // 2+ children
        if (parent[node] == -1) { // if it's root
            if (childCount > 1) { // it's actually an articulation point
                // This -1 accounts for this original cc that has been split.
                // We don't want to count it twice. e.g. let's say we had 2
                // components and this root node has 2 children, so we have
                // 2 + (2 - 1) components in the new graph
                increase[node] = childCount - 1;
            } else {
                // it's not an articulation point. The algorithm has written
                // incremented the increase variable for this node wrongly.
                // We know this is not an articulation point, i.e. if we removed
                // this node the connected components wouldn't change
                increase[node] = 0;
            }
            
        }
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }

    private static class Station implements Comparable<Station> {
        int id;
        int pigeonValue;
        public Station(int id, int pigeonValue) {
            this.id = id;
            this.pigeonValue = pigeonValue;
        }
        public int compareTo(Station o) {
            int cmp = -Integer.compare(pigeonValue, o.pigeonValue);
            return cmp != 0 ? cmp : Integer.compare(id, o.id);
        }
        public String toString() {
            return String.format("%d %d", id, pigeonValue);
        }
    }
}
/*
- this is an articulation points problem
- an articulation point is a node that, if removed, will
increase the number of connected components in the graph
- pigeon value is number of connected components in graph
after removing the station
- bear in mind that we must output a specific number of candidates, and the number of articulation points may be smaller
- then we must output both articulation points (which will have a greater pigeon value) and fill the rest of the candidates with regular nodes
- by definition, a node which is not an articulation
point will have a pigeon value == original number of
connected components
- to get the pigeon value of an articulation point first
we must find it, and then check how many new components
it creates if removed
- its pigeon value == original number of components - 1 + number of components created when removing the node
- the -1 accounts for the original component that has been split
- apply the usual algorithm to find the articulation points
- in the algo, every time the isArticulationPoint condition holds we increase by 1 the number of connected components generated on the split; bear in mind, it's not the number of children, but the number of times the condition holds; it's not the number of children because it may be much bigger, but we are interested in the "entrance" to every new connected component. If a new connected component cycles back to our articulation point, we only want to count the entrance. And indeed we will only count the entrance, because by the time we try to process a child pertaining to the cycle after we've processed the entrance, such child will be marked as visited already and will be ignored

*/