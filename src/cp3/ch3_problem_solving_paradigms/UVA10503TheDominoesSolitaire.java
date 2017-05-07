package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 10503 - Complete Search, Recursive Backtracking (Medium), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1444
class UVA10503TheDominoesSolitaire {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static int left;
    private static int right;
    private static int spaces;
    private static int[][] values;

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null && !"0".equals(line.trim())) {
            spaces = Integer.parseInt(line.trim());
            int pieces = Integer.parseInt(in.readLine().trim());
            StringTokenizer st = new StringTokenizer(in.readLine());
            Integer.parseInt(st.nextToken()); // don't care
            left = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(in.readLine());
            right = Integer.parseInt(st.nextToken());
            values = new int[2][pieces];
            for (int i = 0; i < pieces; i++) {
                st = new StringTokenizer(in.readLine());
                values[0][i] = Integer.parseInt(st.nextToken());
                values[1][i] = Integer.parseInt(st.nextToken());
            }

            int position = 0;
            int current = left;
            boolean[] used = new boolean[pieces];
            if (canFillSpace(position, used, current)) {
                out.println("YES");
            } else {
                out.println("NO");
            }

            line = in.readLine();
        }
        out.close();
    }

    private static boolean canFillSpace(int position, boolean[] used, int previous) {
        if (position == spaces) {
            return previous == right;
        } else {
            for (int i = 0; i < used.length; i++) {
                if ((values[0][i] == previous || values[1][i] == previous)
                        && !used[i]) {
                    used[i] = true;
                    int current = values[0][i] == previous ? values[1][i] : values[0][i];
                    if (canFillSpace(position + 1, used, current)) {
                        return true;
                    }
                    used[i] = false;
                }
            }
            return false;
        }
    }

}
