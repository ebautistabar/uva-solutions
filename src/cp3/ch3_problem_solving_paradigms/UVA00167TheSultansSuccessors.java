package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 167 - Complete Search, Recursive Backtracking (Easy), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=103
class UVA00167TheSultansSuccessors {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    private static final int SIDE = 8;

    private static int[][] board = new int[SIDE][SIDE];
    private static int score;

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        for (int i = 1; i <= tests; i++) {
            board = readBoard();
            score = 0;
            int currentScore = 0;
            int currentCol = 0;
            int usedRows = 0; // bitset
            int rightDiagonals = 0; // bitset. There are 8x2 - 1 right diagonals
            int leftDiagonals = 0; // bitset. There are 8x2 - 1 left diagonals
            // The indices for the right diagonals go through the top row (from
            // left to right) and right column, from 1 to 15
            // The indices for the left diagonals go through the top row (from
            // right to left) and left column, from 1 to 15
            placeQueens(currentCol, usedRows, rightDiagonals, leftDiagonals, currentScore);
            out.printf("%5d%n", score);
        }
        out.close();
    }

    private static void placeQueens(int col, int usedRows,
            int rightDiagonals, int leftDiagonals, int currentScore) {
        if (col == SIDE) {
            score = Math.max(score, currentScore);
        } else {
            for (int row = 0; row < SIDE; row++) {
                if (isValid(row, col, usedRows, rightDiagonals, leftDiagonals)) {
                    usedRows |= 1 << row;
                    // e.g. 1+4, 2+3, 3+2, 4+1 represent the same right diagonal
                    rightDiagonals |= 1 << (row + col);
                    // e.g. 1-4+8, 2-5+8, 3-6+8, 4-7+8, 5-8+8 represent the same left diagonal
                    // row - col results in negative values, so we offset by 8
                    leftDiagonals |= 1 << (row - col + SIDE);
                    currentScore += board[row][col];
                    placeQueens(col + 1, usedRows, rightDiagonals, leftDiagonals, currentScore);
                    // undo changes in state
                    currentScore -= board[row][col];
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

    private static int[][] readBoard() throws IOException {
        for (int i = 0; i < SIDE; i++) {
            StringTokenizer st = new StringTokenizer(in.readLine());
            for (int j = 0; j < SIDE; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        return board;
    }

}
