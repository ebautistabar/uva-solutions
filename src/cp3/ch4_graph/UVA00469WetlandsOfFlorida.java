package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

// 469 - Flood Fill/Finding Connected Components, not starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=410
// We do graph traversal, marking water cells as visited and counting them
class UVA00469WetlandsOfFlorida {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static int[] dr = {1, 1, 1, 0, 0, 0, -1, -1, -1};
    private static int[] dc = {1, 0, -1, 1, 0, -1, 1, 0, -1};

    public static void main(String args[]) throws Exception {
        int tests = Integer.parseInt(getLine().trim());
        while (tests-- > 0) {
            List<List<Character>> grid = new ArrayList<List<Character>>();
            String line = getLine();
            while (line != null && !line.trim().isEmpty()) {
                line = line.trim();
                if (line.charAt(0) == 'L' || line.charAt(0) == 'W') { // read grid
                    List<Character> row = new ArrayList<Character>();
                    for (int i = 0; i < line.length(); i++) {
                        row.add(line.charAt(i));
                    }
                    grid.add(row);
                } else { // read queries
                    boolean[][] visited = new boolean[grid.size()][grid.get(0).size()];
                    StringTokenizer st = new StringTokenizer(line);
                    int r = Integer.parseInt(st.nextToken()) - 1;
                    int c = Integer.parseInt(st.nextToken()) - 1;
                    int area = floodfill(grid, visited, r, c);
                    out.println(area);
                }
                line = in.readLine();
            }
            if (tests > 0) {
                out.println();
            }
        }
        out.close();
    }

    private static int floodfill(List<List<Character>> grid, boolean[][] visited, int r, int c) {
        if (r < 0 || c < 0 || r >= grid.size() || c >= grid.get(r).size()) { // out of bounds
            return 0;
        }
        if (grid.get(r).get(c) != 'W' || visited[r][c]) { // it's not water or it's been processed already
            return 0;
        }
        visited[r][c] = true;
        int area = 1; // at this point we know this cell is water
        for (int i = 0; i < dr.length; i++) {
            area += floodfill(grid, visited, r + dr[i], c + dc[i]);
        }
        return area;
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }
}
