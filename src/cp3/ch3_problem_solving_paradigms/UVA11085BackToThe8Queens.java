package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 11085 - Complete Search, Recursive Backtracking (Easy), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2026
class UVA11085BackToThe8Queens {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    private static final int SIDE = 8;

    private static int[] rows = new int[SIDE];
    private static int minMovements;

    public static void main(String args[]) throws NumberFormatException, IOException {
        int test = 1;
        String line = in.readLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            for (int i = 0; i < rows.length; i++)
                rows[i] = Integer.parseInt(st.nextToken()) - 1;
            minMovements = Integer.MAX_VALUE;
            int usedRows = 0;
            int rightDiagonals = 0;
            int leftDiagonals = 0;
            int movements = 0;
            int col = 0;
            moveQueens(col, usedRows, rightDiagonals, leftDiagonals, movements);
            out.printf("Case %d: %d%n", test, minMovements);
            test++;
            line = in.readLine();
        }
        out.close();
    }

    private static void moveQueens(int col, int usedRows, int rightDiagonals,
            int leftDiagonals, int movements) {
        if (col == SIDE) {
            minMovements = Math.min(minMovements, movements);
        } else {
            for (int row = 0; row < SIDE; row++) {
                if (isValid(row, col, usedRows, rightDiagonals, leftDiagonals)) {
                    usedRows |= 1 << row;
                    rightDiagonals |= 1 << (row + col);
                    leftDiagonals |= 1 << (row - col + SIDE);
                    if (row != rows[col])
                        movements += 1;
                    moveQueens(col + 1, usedRows, rightDiagonals, leftDiagonals, movements);
                    if (row != rows[col])
                        movements -= 1;
                    usedRows &= ~(1 << row);
                    rightDiagonals &= ~(1 << (row + col));
                    leftDiagonals &= ~(1 << (row - col + SIDE));
                }
            }
        }
    }

    private static boolean isValid(int row, int col,
            int usedRows, int rightDiagonals, int leftDiagonals) {
        return ((usedRows & (1 << row)) == 0)
                && ((rightDiagonals & (1 << (row + col))) == 0)
                && ((leftDiagonals & (1 << (row - col + SIDE))) == 0);
    }

}
