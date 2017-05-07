package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// 10284 - Game (Chess), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1225
class UVA10284ChessboardInFEN {

    private static int[] knightMoves = {-2, -1, -2, 1, 2, -1, 2, 1};
    private static int[] kingMoves = {-1, 0, 1};

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String line = in.readLine();
        while (line != null) {
            boolean[][] unsafe = new boolean[8][8];
            boolean[][] occupied = new boolean[8][8];
            String[] rows = line.split("/");
            // first mark occupied cells
            for (int r = 0; r < rows.length; r++) {
                int c = 0;
                for (int k = 0; k < rows[r].length(); k++) {
                    char ch = rows[r].charAt(k);
                    if (ch >= '1' && ch <= '8') {
                        c += ch - '0';
                    } else {
                        occupied[r][c] = true;
                        c++;
                    }
                }
            }
            // then mark unsafe cells: occupied cells plus cells under attack
            for (int r = 0; r < rows.length; r++) {
                int c = 0;
                for (int k = 0; k < rows[r].length(); k++) {
                    char ch = rows[r].charAt(k);
                    if (ch >= '1' && ch <= '8') {
                        c += ch - '0';
                    } else {
                        markUnsafe(ch, unsafe, occupied, r, c);
                        c++;
                    }
                }
            }
            int count = 0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (unsafe[i][j]) {
                        count++;
                    }
                }
            }
            out.println(8 * 8 - count);
            line = in.readLine();
        }
        out.close();
    }

    private static void markUnsafe(char piece, boolean[][] unsafe, boolean[][] occupied, int r, int c) {
        unsafe[r][c] = true;
        switch (piece) {
        case 'P':
            // white pawns attack diagonally up
            if (isValidCell(r - 1) && isValidCell(c - 1)) {
                unsafe[r - 1][c - 1] = true;
            }
            if (isValidCell(r - 1) && isValidCell(c + 1)) {
                unsafe[r - 1][c + 1] = true;
            }
            break;
        case 'p':
            // black pawns attack diagonally down
            if (isValidCell(r + 1) && isValidCell(c - 1)) {
                unsafe[r + 1][c - 1] = true;
            }
            if (isValidCell(r + 1) && isValidCell(c + 1)) {
                unsafe[r + 1][c + 1] = true;
            }
            break;
        case 'N':
        case 'n':
            for (int i = 0; i < 8; i += 2) {
                if (isValidCell(r + knightMoves[i]) && isValidCell(c + knightMoves[i + 1])) {
                    unsafe[r + knightMoves[i]][c + knightMoves[i + 1]] = true;
                }
                if (isValidCell(r + knightMoves[i + 1]) && isValidCell(c + knightMoves[i])) {
                    unsafe[r + knightMoves[i + 1]][c + knightMoves[i]] = true;
                }
            }
            break;
        case 'B':
        case 'b':
            markDiagonals(unsafe, occupied, r, c);
            break;
        case 'R':
        case 'r':
            markStraightLines(unsafe, occupied, r, c);
            break;
        case 'Q':
        case 'q':
            markDiagonals(unsafe, occupied, r, c);
            markStraightLines(unsafe, occupied, r, c);
            break;
        case 'K':
        case 'k':
            for (int i = 0; i < kingMoves.length; i++) {
                int nr = r + kingMoves[i];
                if (nr >= 0 && nr < 8) {
                    for (int j = 0; j < kingMoves.length; j++) {
                        int nc = c + kingMoves[j];
                        if (nc >= 0 && nc < 8) {
                            unsafe[nr][nc] = true;
                        }
                    }
                }
            }
            break;
        }
    }

    private static void markStraightLines(boolean[][] unsafe, boolean[][] occupied, int r, int c) {
        for (int i = c - 1; i >= 0 && !occupied[r][i]; i--) {
            unsafe[r][i] = true;
        }
        for (int i = c + 1; i < 8 && !occupied[r][i]; i++) {
            unsafe[r][i] = true;
        }
        for (int i = r - 1; i >= 0 && !occupied[i][c]; i--) {
            unsafe[i][c] = true;
        }
        for (int i = r + 1; i < 8 && !occupied[i][c]; i++) {
            unsafe[i][c] = true;
        }
    }

    private static void markDiagonals(boolean[][] unsafe, boolean[][] occupied, int r, int c) {
        int nr = r - 1, nc = c - 1;
        while (nr >= 0 && nc >= 0 && !occupied[nr][nc]) {
            unsafe[nr][nc] = true;
            nr--;
            nc--;
        }
        nr = r + 1;
        nc = c + 1;
        while (nr < 8 && nc < 8 && !occupied[nr][nc]) {
            unsafe[nr][nc] = true;
            nr++;
            nc++;
        }
        nr = r + 1;
        nc = c - 1;
        while (nr < 8 && nc >= 0 && !occupied[nr][nc]) {
            unsafe[nr][nc] = true;
            nr++;
            nc--;
        }
        nr = r - 1;
        nc = c + 1;
        while (nr >= 0 && nc < 8 && !occupied[nr][nc]) {
            unsafe[nr][nc] = true;
            nr--;
            nc++;
        }
    }

    private static boolean isValidCell(int cell) {
        return cell >= 0 && cell < 8;
    }
}
