package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.regex.Pattern;

// 556 - 'Time Waster' Problems, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=497
class UVA00556Amazing {

    private static Pattern end = Pattern.compile("\\s*0\\s+0\\s*");
    private static int[] movements = {0, 1, 1, 0, 0, -1, -1, 0};

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String line = in.readLine();
        while (line != null && !end.matcher(line).matches()) {
            String[] st = line.split("\\s+");
            int rows = Integer.parseInt(st[0]);
            int cols = Integer.parseInt(st[1]);
            int[][] visits = new int[rows][cols];
            boolean[][] blocked = new boolean[rows][cols];
            for (int i = 0; i < rows; i++) {
                line = in.readLine();
                for (int j = 0; j < cols; j++) {
                    blocked[i][j] = line.charAt(j) == '1';
                }
            }
            int r = rows - 1, c = 0, direction = 0;
            do {
                if (isWall(getRightCell(r, c, direction), blocked)) {
                    int nr = r + movements[direction];
                    int nc = c + movements[direction + 1];
                    if (isWall(nr, nc, blocked)) {
                        direction = turnLeft(direction);
                    } else {
                        r = nr;
                        c = nc;
                        visits[r][c] += 1;
                    }
                } else {
                    direction = turnRight(direction);
                    r = r + movements[direction];
                    c = c + movements[direction + 1];
                    visits[r][c] += 1;
                }
            } while (!isStart(r, c, rows));
            int[] result = new int[5];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (!blocked[i][j] && visits[i][j] < 5) {
                        result[visits[i][j]]++;
                    }
                }
            }
            for (int count : result) {
                out.printf("%3s", count);
            }
            out.println();
            line = in.readLine();
        }
        out.close();
    }

    private static boolean isWall(int[] rightCell, boolean[][] blocked) {
        return isWall(rightCell[0], rightCell[1], blocked);
    }

    private static boolean isWall(int r, int c, boolean[][] blocked) {
        return isOutOfBounds(r, blocked.length)
                || isOutOfBounds(c, blocked[0].length) || blocked[r][c];
    }

    private static int[] getRightCell(int r, int c, int direction) {
        int rr = r, rc = c;
        switch (direction) {
        case 0: // going east
            rr += 1;
            break;
        case 2: // going south
            rc -= 1;
            break;
        case 4: // going west
            rr -= 1;
            break;
        case 6: // going north
            rc += 1;
            break;
        }
        return new int[]{rr, rc};
    }

    private static boolean isOutOfBounds(int i, int limit) {
        return i < 0 || i >= limit;
    }

    private static int turnLeft(int direction) {
        return (direction + 6) % movements.length;
    }

    private static int turnRight(int direction) {
        return (direction + 2) % movements.length;
    }

    private static boolean isStart(int r, int c, int rows) {
        return r == rows - 1 && c == 0;
    }

}
