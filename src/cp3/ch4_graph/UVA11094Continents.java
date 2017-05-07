package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 11094 - Flood Fill/Finding Connected Components, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2035
class UVA11094Continents {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static int[] dr = {1, -1, 0, 0};
    private static int[] dc = {0, 0, 1, -1};

    public static void main(String args[]) throws Exception {
        String line = getLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            int rows = Integer.parseInt(st.nextToken());
            int cols = Integer.parseInt(st.nextToken());
            char[][] map = new char[rows][];
            for (int i = 0; i < rows; i++) {
                line = getLine().trim();
                map[i] = line.toCharArray();
            }
            st = new StringTokenizer(getLine());
            int manR = Integer.parseInt(st.nextToken());
            int manC = Integer.parseInt(st.nextToken());
            // find the characters for water and land, since they are different
            // in each test
            char land = map[manR][manC];
            char water = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (map[i][j] != land) {
                        water = map[i][j];
                        break;
                    }
                }
                if (water != 0) {
                    break;
                }
            }

            // we'll use the map itself to mark as visited by setting the component id.
            // thus, first component id must be > land and > water
            int componentId = Math.max(land, water) + 1;
            int bestSize = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (map[i][j] == land) {
                        // not yet in a component, i.e. it will be the start
                        // of a new component
                        int size = getSizeOfComponent(i, j, componentId, map, land);
                        if (bestSize < size && componentId != map[manR][manC]) {
                            // if the cell of the man has been visited, it will
                            // have a different component id.
                            // if it hasn't been visited, it will have the land
                            // character, which is always less than any
                            // component id, i.e. different
                            bestSize = size;
                        }
                        componentId++;
                    }
                }
            }
            out.println(bestSize);

            line = getLine();
        }
        out.close();
    }

    private static int getSizeOfComponent(int r, int c, int componentId,
            char[][] map, char land) {
        int size = 1;
        map[r][c] = (char)componentId;
        for (int i = 0; i < dr.length; i++) {
            int nr = r + dr[i];
            int nc = (c + dc[i]) % map[r].length; // to account for wrapping
            if (nc < 0) {                         // at first and last columns
                nc += map[r].length; // -a % b < 0, this line makes it positive
            }
            if (isInBounds(nr, nc, map) && map[nr][nc] == land) {
                size += getSizeOfComponent(nr, nc, componentId, map, land);
            }
        }
        return size;
    }

    private static boolean isInBounds(int r, int c, char[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }
}
