package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 750 - Recursive Backtracking (Easy), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=691
class UVA00750_8QueensChessProblem {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    private static final int SIDE = 8;

    private static int solutions;
    private static int occupiedRow;
    private static int occupiedCol;

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        for (int i = 1; i <= tests; i++) {
            if (i > 1)
                out.println();
            printHeader();
            in.readLine(); // blank line
            StringTokenizer st = new StringTokenizer(in.readLine());
            occupiedRow = Integer.parseInt(st.nextToken());
            occupiedCol = Integer.parseInt(st.nextToken());
            solutions = 0;
            int[] queens = new int[SIDE + 1]; // 1-based
            int currentCol = 1;
            int usedRows = 0; // bitset
            int rightDiagonals = 0; // bitset. There are 8x2 - 1 right diagonals
            int leftDiagonals = 0; // bitset. There are 8x2 - 1 left diagonals
            // The indices for the right diagonals go through the top row (from
            // left to right) and right column, from 1 to 15
            // The indices for the left diagonals go through the top row (from
            // right to left) and left column, from 1 to 15
            placeQueens(queens, currentCol, usedRows, rightDiagonals, leftDiagonals);
        }
        out.close();
    }

    private static void placeQueens(int[] queens, int col, int usedRows,
            int rightDiagonals, int leftDiagonals) {
        if (col == queens.length) {
            if (queens[occupiedCol] == occupiedRow)
                // Cannot pass the queens array to printf because it's not an
                // array of Objects, but primitives
                out.printf("%2d      %d %d %d %d %d %d %d %d%n", ++solutions,
                        queens[1], queens[2], queens[3], queens[4], queens[5],
                        queens[6], queens[7], queens[8]);
        } else {
            for (int row = 1; row < queens.length; row++) {
                if (isValid(row, col, usedRows, rightDiagonals, leftDiagonals)) {
                    queens[col] = row;
                    usedRows |= 1 << row;
                    // e.g. 1+4, 2+3, 3+2, 4+1 represent the same right diagonal
                    rightDiagonals |= 1 << (row + col);
                    // e.g. 1-4+8, 2-5+8, 3-6+8, 4-7+8, 5-8+8 represent the same left diagonal
                    // row - col results in negative values, so we offset by 8
                    leftDiagonals |= 1 << (row - col + SIDE);
                    placeQueens(queens, col + 1, usedRows, rightDiagonals, leftDiagonals);
                    // Undo state changes
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

    private static void printHeader() {
        out.println("SOLN       COLUMN");
        out.println(" #      1 2 3 4 5 6 7 8");
        out.println();
    }

}
