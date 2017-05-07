package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 10189 - Game (Others), Easier, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1130
class UVA10189Minesweeper {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String line = in.readLine();
        int field = 0;
        while (!line.equals("0 0")) {
            StringTokenizer st = new StringTokenizer(line);
            int[][] minefield = new int[Integer.parseInt(st.nextToken())][Integer.parseInt(st.nextToken())];
            for (int r = 0; r < minefield.length; r++) {
                String row = in.readLine();
                for (int c = 0; c < minefield[0].length; c++) {
                    if (row.charAt(c) == '*') {
                        minefield[r][c] = 9;
                        incrementMineCount(minefield, r, c);
                    }
                }
            }
            if (field++ != 0) {
                out.println();
            }
            out.println("Field #" + field + ":");
            for (int r = 0; r < minefield.length; r++) {
                for (int c = 0; c < minefield[0].length; c++) {
                    if (minefield[r][c] > 8) {
                        out.print('*');
                    } else {
                        out.print(minefield[r][c]);
                    }
                }
                out.println();
            }
            line = in.readLine();
        }
        out.close();
    }

    private static void incrementMineCount(int[][] minefield, int r, int c) {
        for (int i = -1; i < 2; i ++) {
            for (int j = -1; j < 2; j++) {
                int nr = r + i, nc = c + j;
                if (nr >= 0 && nc >= 0 && nr < minefield.length && nc < minefield[0].length) {
                    minefield[nr][nc]++;
                }
            }
        }
    }

}
