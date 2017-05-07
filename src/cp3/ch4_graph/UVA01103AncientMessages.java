package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

// 1103 - Flood Fill/Finding Connected Components, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3544
// Explanation:
//   http://www.questtosolve.com/browse.php?pid=1103
class UVA01103AncientMessages {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int WHITE_COLOR = 0;
    private static final int BLACK_COLOR = 1;
    private static final int BACKGROUND_COLOR = 2;
    private static final int HIEROGLYPH_COLOR = 3;
    private static int[] dr = {1, -1, 0, 0};
    private static int[] dc = {0, 0, -1, 1};
    private static char[] holesToType = {'W', 'A', 'K', 'J', 'S', 'D'};

    public static void main(String args[]) throws IOException {
        int test = 1;
        String line = getLine();
        while (line != null) {
            int[][] image = readImage(line);
            if (image.length == 0) break;

            markBackground(image);
            List<Character> hieroglyphs = findHieroglyphs(image);

            printSolution(test, hieroglyphs);

            line = getLine();
            test++;
        }
        out.close();
    }

    private static int[][] readImage(String line) throws IOException {
        StringTokenizer st = new StringTokenizer(line);
        int height = Integer.parseInt(st.nextToken());
        int width = Integer.parseInt(st.nextToken()) * 4;

        int[][] image = new int[height][width];
        for (int i = 0; i < height; i++) {
            line = getLine();
            for (int j = 0; j < line.length(); j++) {
                char digit = line.charAt(j);
                int num = digit < 'a' ? digit - '0' : digit - 'A' + 10;
                image[i][4 * j] = (num & (1 << 3)) > 0 ? 1 : 0;
                image[i][4 * j + 1] = (num & (1 << 2)) > 0 ? 1 : 0;
                image[i][4 * j + 2] = (num & (1 << 1)) > 0 ? 1 : 0;
                image[i][4 * j + 3] = (num & 1) > 0 ? 1 : 0;
            }
        }

        return image;
    }

    private static List<Character> findHieroglyphs(int[][] image) {
        List<Character> hieroglyphs = new ArrayList<Character>();
        int height = image.length;
        int width = image[0].length;
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                if (image[r][c] == BLACK_COLOR) {
                    // if it's plain black it means it's a new hieroglyph
                    int holes = floodFillHieroglyph(image, r, c);
                    hieroglyphs.add(holesToType[holes]);
                }
            }
        }
        return hieroglyphs;
    }

    private static int floodFillHieroglyph(int[][] image, int r, int c) {
        int holes = 0;
        int height = image.length;
        int width = image[0].length;
        if (image[r][c] != BLACK_COLOR) return -1;
        image[r][c] = HIEROGLYPH_COLOR;
        Queue<Cell> queue = new ArrayDeque<Cell>();
        queue.add(new Cell(r, c));
        while (!queue.isEmpty()) {
            Cell current = queue.remove();
            for (int i = 0; i < dr.length; i++) {
                int nr = current.r + dr[i];
                int nc = current.c + dc[i];
                if (!isOutOfBounds(nr, nc, height, width)) {
                    if (image[nr][nc] == BLACK_COLOR) {
                        // we continue in the hieroglyph body
                        image[nr][nc] = HIEROGLYPH_COLOR;
                        queue.add(new Cell(nr, nc));
                    } else if (image[nr][nc] == WHITE_COLOR) {
                        // we found a hole: fill with new color
                        holes++;
                        int holeColor = HIEROGLYPH_COLOR + holes;
                        floodfill(image, nr, nc, WHITE_COLOR, holeColor);
                    }
                }
            }
        }
        return holes;
    }

    // Floodfill from white cells at the borders to mark them as background
    // That'll leave us with unmarked hieroglyphs and unmarked holes
    private static void markBackground(int[][] image) {
        int height = image.length;
        int width = image[0].length;
        for (int c = 0; c < width; c++) {
            floodfill(image, 0, c, WHITE_COLOR, BACKGROUND_COLOR);
        }
        for (int c = 0; c < width; c++) {
            floodfill(image, height - 1, c, WHITE_COLOR, BACKGROUND_COLOR);
        }
        for (int r = 0; r < height; r++) {
            floodfill(image, r, 0, WHITE_COLOR, BACKGROUND_COLOR);
        }
        for (int r = 0; r < height; r++) {
            floodfill(image, r, width - 1, WHITE_COLOR, BACKGROUND_COLOR);
        }
    }

    private static void floodfill(int[][] image, int r, int c, int originalColor, int newColor) {
        int height = image.length;
        int width = image[0].length;
        if (isOutOfBounds(r, c, height, width)) return;
        if (image[r][c] != originalColor) return;
        image[r][c] = newColor;
        Queue<Cell> queue = new ArrayDeque<Cell>();
        queue.add(new Cell(r, c));
        while (!queue.isEmpty()) {
            Cell current = queue.remove();
            for (int i = 0; i < dr.length; i++) {
                int nr = current.r + dr[i];
                int nc = current.c + dc[i];
                if (!isOutOfBounds(nr, nc, height, width) && image[nr][nc] == originalColor) {
                    image[nr][nc] = newColor;
                    queue.add(new Cell(nr, nc));
                }
            }
        }
    }

    private static boolean isOutOfBounds(int r, int c, int height, int width) {
        return r < 0 || c < 0 || r >= height || c >= width;
    }

    private static void printSolution(int test, List<Character> hieroglyphs) {
        Collections.sort(hieroglyphs);
        StringBuilder sb = new StringBuilder();
        sb.append("Case ");
        sb.append(test);
        sb.append(": ");
        for (int i = 0; i < hieroglyphs.size(); i++) {
            sb.append(hieroglyphs.get(i));
        }
        out.println(sb.toString());
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }

    private static class Cell {
        public int r;
        public int c;
        public Cell(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
}
