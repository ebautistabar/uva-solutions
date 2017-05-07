package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

// 10102 - Complete Search, Iterative (Three or More Nested Loops, Easier), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1043
class UVA10102ThePathInTheColoredField {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    // Another possibility is tracking in separate arrays the coordinates of
    // every 1 and 3 when reading the input. Then we use the positions directly
    // instead of searching for the numbers every time. If there are lots of
    // 1s and 3s, storing their coordinates could be heavier than storing the
    // matrix though
    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null) {
            int side = Integer.parseInt(line.trim());
            char[][] field = new char[side][side];
            for (int i = 0; i < side; i++) {
                line = in.readLine();
                for (int j = 0; j < side; j++) {
                    field[i][j] = line.charAt(j);
                }
            }
            int maxDistance = Integer.MIN_VALUE;
            // For each 1
            for (int i = 0; i < side; i++) {
                for (int j = 0; j < side; j++) {
                    if (field[i][j] == '1') {
                        int minDistance = Integer.MAX_VALUE;
                        // For each 3
                        for (int k = 0; k < side; k++) {
                            for (int m = 0; m < side; m++) {
                                if (field[k][m] == '3') {
                                    int manhattanDistance = Math.abs(i - k) + Math.abs(j - m);
                                    minDistance = Math.min(manhattanDistance, minDistance);
                                }
                            }
                        }
                        maxDistance = Math.max(minDistance, maxDistance);
                    }
                }
            }
            out.println(maxDistance);
            line = in.readLine();
        }
        out.close();
    }

}
