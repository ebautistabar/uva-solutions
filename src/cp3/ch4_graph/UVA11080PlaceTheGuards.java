package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

// 11080 - Bipartite Graph Check, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2021
// Notes at the bottom
class UVA11080PlaceTheGuards {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    // UNGUARDED = 0
    private static final int GUARDED = 1;
    private static final int UNVISITED = 2;

    public static void main(String args[]) throws IOException {
        int tests = Integer.parseInt(getLine().trim());
        for (int t = 0; t < tests; t++) {
            StringTokenizer st = new StringTokenizer(getLine());
            int junctions = Integer.parseInt(st.nextToken());
            int streets = Integer.parseInt(st.nextToken());
            List<List<Integer>> adjList = new ArrayList<>();
            for (int i = 0; i < junctions; i++) {
                adjList.add(new ArrayList<Integer>());
            }
            for (int i = 0; i < streets; i++) {
                st = new StringTokenizer(getLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                adjList.get(a).add(b);
                adjList.get(b).add(a);
            }

            int guards = 0;
            int[] colors = new int[junctions];
            Arrays.fill(colors, UNVISITED);
            for (int i = 0; i < junctions; i++) {
                if (colors[i] == UNVISITED) {
                    int guardCount = colorComponent(i, adjList, colors);
                    if (guardCount == -1) {
                        // must stop as soon as we find 1 impossible component
                        guards = -1;
                        break;
                    } else {
                        guards += guardCount;
                    }
                }
            }

            out.println(guards);
        }
        out.close();
    }

    private static int colorComponent(int node, List<List<Integer>> adjList,
            int[] colors) {
        colors[node] = GUARDED;
        int junctions = 1;
        int guards = 1;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            int current = queue.remove();
            List<Integer> adjacent = adjList.get(current);
            for (int junction : adjacent) {
                if (colors[junction] == UNVISITED) {
                    colors[junction] = 1 - colors[current]; // flip the color: 1-0=1, 1-1=0
                    queue.add(junction);
                    junctions++;
                    if (colors[junction] == GUARDED) {
                        guards++;
                    }
                } else if (colors[junction] == colors[current]) {
                    return -1; // the guards will fight
                }
            }
        }
        // if we have n guards, then we have junctions-n unguarded spots.
        // labels can be flipped. We want to minimize the junctions labeled GUARDED
        guards = Math.min(guards, junctions - guards);
        // if we have 1 junction and 1 guard and we flip, we get 0 guards which
        // is wrong. We have to return at least 1 guard
        guards = Math.max(guards, 1);
        return guards;
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
if a guard is set in a junction, it also controls the adjacent streets and
junctions. The fact it controls adjacent junctions means that those junctions
must have no guard on them.

This is a bipartite check problem, in which we try to divide the graph into 2
sets: guarded and unguarded. Bear in mind that those are just labels, i.e. when
we finish we could switch the labels: let those guarded be unguarded, and
viceversa. We actually need to do this because we want to minimize the number
of guards. If we ended up having less unguarded spots than guarded spots, we
flip the labels and finish with less guarded spots.

*/