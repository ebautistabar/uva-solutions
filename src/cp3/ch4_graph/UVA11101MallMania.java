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

// 11101 - Single-Source Shortest Paths (SSSP) On Unweighted Graph: BFS, Harder, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2042
// BFS with multiple sources and goals, notes below
class UVA11101MallMania {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int[] DR = {-1, 1, 0, 0};
    private static final int[] DC = {0, 0, -1, 1};

    public static void main(String args[]) throws IOException {
        String line = getLine();
        while (line != null) {
            int intersections1 = Integer.parseInt(line.trim());
            if (intersections1 == 0) break;
            List<Intersection> sources = readMallPerimeter(intersections1);
            int intersections2 = Integer.parseInt(getLine().trim());
            List<Intersection> goals = readMallPerimeter(intersections2);

            // Don't know exact size of grid, so create one which is big enough
            int[][] distance = new int[2005][2005];
            for (int i = 0; i < distance.length; i++) {
                Arrays.fill(distance[i], Integer.MAX_VALUE);
            }
            Queue<Intersection> q = new ArrayDeque<>();
            for (Intersection source : sources) {
                q.add(source);
                distance[source.r][source.c] = 0;
            }
            while (!q.isEmpty()) {
                Intersection current = q.remove();
                for (int i = 0; i < DR.length; i++) {
                    int nr = current.r + DR[i];
                    int nc = current.c + DC[i];
                    Intersection next = new Intersection(nr, nc);
                    if (next.isValid(distance)
                            && distance[next.r][next.c] == Integer.MAX_VALUE) {
                        distance[next.r][next.c] = distance[current.r][current.c] + 1;
                        q.add(next);
                    }
                }
            }

            // Find the minimum distance among all the intersections surrounding
            //   the other mall
            int minDistance = Integer.MAX_VALUE;
            for (Intersection goal : goals) {
                minDistance = Math.min(minDistance, distance[goal.r][goal.c]);
            }
            out.println(minDistance);

            line = getLine();
        }
        out.close();
    }

    private static List<Intersection> readMallPerimeter(int junctions) throws IOException  {
        List<Intersection> intersections = new ArrayList<>(junctions);
        while (intersections.size() < junctions) {
            StringTokenizer st = new StringTokenizer(getLine());
            while (st.hasMoreTokens()) {
                int col = Integer.parseInt(st.nextToken());
                int row = Integer.parseInt(st.nextToken());
                intersections.add(new Intersection(row, col));
            }
        }
        return intersections;
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }


    private static class Intersection {
        int r;
        int c;
        public Intersection(int r, int c) {
            this.r = r;
            this.c = c;
        }
        public boolean isValid(int[][] distance) {
            return r >= 0 && r < distance.length && c >= 0 && c < distance[r].length;
        }
        @Override
        public String toString() {
            return String.format("[%d,%d]", r, c);
        }
    }
}
/*
grid composed by streets and avenues
avenues go north-south, so they are like columns
streets go east-west, so they are like rows
from 0 to 2000, thus max grid is 2000x2000
coordinates are given like (a,s)=(avenue,street)=(col,row)

the fact that the rows are in decreasing order is not relevant. the resulting grid will be a mirror image of the usual order. it has no relevance in terms of distances.

for each mall, we are given the number of intersections surrounding the mall, and their coordinates.

Sample input:      Mirrored input:

 012345             012345
4                  0++
3  +++             1++
2  +++             2  +++
1++                3  +++
0++                4

find minimum distance between the 2 malls, i.e. the minimum distance between any of the intersections surrounding one mall and any of the intersections surrounding the other mall

BFS with multiple sources and multiple goals. Insert all sources in the queue
at the start. At the end, get the minimum distance among all the goals.

the conditions in the statement make clear there are no edge cases

*/
