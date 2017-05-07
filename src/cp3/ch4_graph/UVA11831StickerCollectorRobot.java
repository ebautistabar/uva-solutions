package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 11831 - Just Graph Traversal, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2931
class UVA11831StickerCollectorRobot {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int[] dr = {-1, 0, 1, 0};
    private static final int[] dc = {0, 1, 0, -1};
    private static final char STICKER = '*';
    private static final char PILLAR = '#';

    public static void main(String args[]) throws Exception {
        String line = getLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            int rows = Integer.parseInt(st.nextToken());
            int cols = Integer.parseInt(st.nextToken());
            int steps = Integer.parseInt(st.nextToken());
            if (rows == 0 && cols == 0 && steps == 0) break;
            int startRow = -1;
            int startCol = -1;
            char[][] arena = new char[rows][cols];
            for (int i = 0; i < rows; i++) {
                line = getLine();
                for (int j = 0; j < cols; j++) {
                    arena[i][j] = line.charAt(j);
                    if (arena[i][j] >= 'L' && arena[i][j] <= 'S') {
                        startRow = i;
                        startCol = j;
                    }
                }
            }
            line = getLine();

            int stickers = 0;
            int dir = getDirection(arena[startRow][startCol]);
            int row = startRow;
            int col = startCol;
            for (int i = 0; i < line.length(); i++) {
                char step = line.charAt(i);
                if (step == 'F') { // move forward
                    int nr = row + dr[dir];
                    int nc = col + dc[dir];
                    if (nr >= 0 && nc >= 0 && nr < rows && nc < cols && arena[nr][nc] != PILLAR) {
                        if (arena[nr][nc] == STICKER) {
                            arena[nr][nc] = '.';
                            stickers++;
                        }
                        row = nr;
                        col = nc;
                    }
                } else if (step == 'D') { // turn right
                    dir = (dir + 1) % dr.length;
                } else { // turn left
                    dir = (dir - 1) % dr.length;
                    if (dir < 0) dir += dr.length;
                }
            }
            
            out.println(stickers);

            line = getLine();
        }
        out.close();
    }

    private static int getDirection(char c) {
        if (c == 'N') return 0;
        if (c == 'L') return 1;
        if (c == 'S') return 2;
        if (c == 'O') return 3;
        return -1;
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }
}
