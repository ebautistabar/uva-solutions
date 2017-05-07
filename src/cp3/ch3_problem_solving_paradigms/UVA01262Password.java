package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

// 1262 - Complete Search, Recursive Backtracking (Harder), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3703
class UVA01262Password {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static char[][] first;
    private static char[][] second;
    private static char[] lastPassword;
    private static int matches;
    private static int k;

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        while (tests-- > 0) {
            k = Integer.parseInt(in.readLine().trim());
            first = buildMatrix();
            second = buildMatrix();
            lastPassword = new char[]{0, 0, 0, 0, 0};
            matches = 0;
            int row = 0;
            char[] password = new char[5];
            if (findKthPassword(row, password)) {
                out.println(new String(password));
            } else {
                out.println("NO");
            }
        }
        out.close();
    }

    private static char[][] buildMatrix() throws IOException {
        char[][] matrix = new char[5][6];
        for (int i = 0; i < matrix[0].length; i++) {
            String line = in.readLine().trim();
            for (int j = 0; j < matrix.length; j++) {
                matrix[j][i] = line.charAt(j);
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            Arrays.sort(matrix[i]);
        }
        return matrix;
    }

    private static boolean findKthPassword(int row, char[] password) {
        if (row == password.length) {
            if (isLessThan(lastPassword, password)) {
                matches++;
                System.arraycopy(password, 0, lastPassword, 0, password.length);
            }
            return matches == k;
        }
        for (int i = 0; i < first[row].length; i++) {
            char target = first[row][i];
            for (int j = 0; j < second[row].length; j++) {
                if (second[row][j] == target) {
                    password[row] = target;
                    if (findKthPassword(row + 1, password)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isLessThan(char[] a1, char[] a2) {
        for (int i = 0; i < a1.length; i++) {
            if (a1[i] < a2[i]) {
                return true;
            } else if (a1[i] > a2[i]) {
                return false;
            }
        }
        return false; // arrays are the same
    }

}
