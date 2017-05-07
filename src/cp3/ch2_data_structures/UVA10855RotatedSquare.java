package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 10855 - 2D Array Manipulation, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1796
class UVA10855RotatedSquare {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String line = in.readLine();
        while (line != null && !line.equals("0 0")) {
            StringTokenizer st = new StringTokenizer(line);
            int N = Integer.parseInt(st.nextToken());
            int n = Integer.parseInt(st.nextToken());
            char[][] bigSquare = new char[N][];
            for (int i = 0; i < N; i++) {
                bigSquare[i] = in.readLine().toCharArray();
            }
            char[][] littleSquare = new char[n][];
            for (int i = 0; i < n; i++) {
                littleSquare[i] = in.readLine().toCharArray();
            }
            out.print(getAppearances(bigSquare, littleSquare));
            out.print(' ');
            out.print(getAppearances(bigSquare, rotate(littleSquare)));
            out.print(' ');
            out.print(getAppearances(bigSquare, rotate(littleSquare)));
            out.print(' ');
            out.println(getAppearances(bigSquare, rotate(littleSquare)));
            line = in.readLine();
        }
        out.close();
    }

    // Rotates the given square 90º clockwise, in place, and returns it
    private static char[][] rotate(char[][] square) {
        int n = square.length;
        for (int i = 0; i < n / 2; i++) {
            char tmp = 0;
            for (int j = i; j < n - i - 1; j++) {
                tmp = square[i][j];
                square[i][j] = square[n - 1 - j][i];
                square[n - 1 - j][i] = square[n - 1 - i][n - 1 - j];
                square[n - 1 - i][n - 1 - j] = square[j][n - 1 - i];
                square[j][n - 1 - i] = tmp;
            }
        }
        return square;
    }

    // Gets number of appearances of littleSquare inside bigSquare
    private static int getAppearances(char[][] bigSquare,
            char[][] littleSquare) {
        int num = 0;
        int limit = bigSquare.length - littleSquare.length + 1;
        for (int i = 0; i < limit; i++) {
            for (int j = 0; j < limit; j++) {
                if (areEqual(bigSquare, littleSquare, i, j)) {
                    num++;
                }
            }
        }
        return num;
    }

    private static boolean areEqual(char[][] bigSquare, char[][] littleSquare,
            int r, int c) {
        for (int i = 0; i < littleSquare.length; i++) {
            for (int j = 0; j < littleSquare.length; j++) {
                if (littleSquare[i][j] != bigSquare[r + i][c + j]) {
                    return false;
                }
            }
        }
        return true;
    }

}
