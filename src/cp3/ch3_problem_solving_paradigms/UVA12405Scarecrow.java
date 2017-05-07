package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

// 12405 - Greedy, Classical, Usually Easier, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3836
class UVA12405Scarecrow {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine());
        for (int j = 1; j <= tests; j++) {
            int n = Integer.parseInt(in.readLine());
            String field = in.readLine();
            char[] sections = new char[n];
            for (int i = 0; i < n; i++) {
                sections[i] = field.charAt(i);
            }
            int scarecrows = 0;
            int i = 0;
            while (i < n) {
                if (sections[i] == '#') {
                    i++;
                } else {
                    scarecrows++;
                    if (i < n - 1) {
                        i += 3;
                    } else {
                        i += 2;
                    }
                }
            }
            out.printf("Case %d: %d%n", j, scarecrows);
        }
        out.close();
    }

}
