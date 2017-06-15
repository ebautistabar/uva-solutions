package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

// 10308 - Special Graph (Others), Trees, not starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1249
// notes below
class UVA10308RoadsInTheNorth {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        String line = getLine();
        while (line != null) {
            Map<Integer, List<Edge>> tree = new HashMap<>();
            while (line != null && !"".equals(line.trim())) {
                StringTokenizer st = new StringTokenizer(line);
                int from = parseInt(st.nextToken()) - 1; // 1-based
                int to = parseInt(st.nextToken()) - 1; // 1-based
                int distance = parseInt(st.nextToken());
                List<Edge> edges = tree.get(from);
                if (edges == null) {
                    edges = new ArrayList<Edge>();
                    tree.put(from, edges);
                }
                edges.add(new Edge(to, distance));
                edges = tree.get(to);
                if (edges == null) {
                    edges = new ArrayList<Edge>();
                    tree.put(to, edges);
                }
                edges.add(new Edge(from, distance));
                line = in.readLine();
            }

            int[] distance = new int[tree.size()];
            Arrays.fill(distance, -1);
            distance[0] = 0;
            int start = getMostRemote(0, tree, distance);
            Arrays.fill(distance, -1);
            distance[start] = 0;
            int end = getMostRemote(start, tree, distance);

            out.println(distance[end]);
            line = getLine();
        }
        out.close();
    }

    private static int getMostRemote(int start, Map<Integer, List<Edge>> tree, int[] distance) {
        int maxDistance = distance[start];
        int mostRemote = start;
        for (Edge edge : tree.get(start)) {
            if (distance[edge.to] == -1) {
                distance[edge.to] = distance[start] + edge.distance;
                int remote = getMostRemote(edge.to, tree, distance);
                if (maxDistance < distance[remote]) {
                    maxDistance = distance[remote];
                    mostRemote = remote;
                }
            }
        }
        return mostRemote;
    }

    private static String getLine() {
        try {
            String line = in.readLine();
            while (line != null && "".equals(line.trim())) // dealing with blank lines
                line = in.readLine();
            return line;
        } catch(Exception e) {
            return null;
        }
    }
    private static int parseInt(String text) {
        return Integer.parseInt(text.trim());
    }

    private static class Edge {
        int to;
        int distance;
        public Edge(int to, int distance) {
            this.to = to;
            this.distance = distance;
        }
        public String toString() {
            return String.format("(%d, %d)", to + 1, distance);
        }
    }
}
/*
The road network represents a tree, and the statement asks for the distance between the 2 most remote villages. The
distance between the 2 most remote nodes of a tree is the diameter. To get it, pick any node and look for the furthest
node. This node will be one extreme of the path. Then from that extreme, again look for the furthest node. That's the
other extreme of the path and the distance is the diameter.
*/
