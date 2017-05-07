package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

// 10895 - Graph Data Structures Problems, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1836
class UVA10895MatrixTranspose {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line.trim());
            int rows = Integer.parseInt(st.nextToken());
            int cols = Integer.parseInt(st.nextToken());
            List<List<Pair>> transpose = new ArrayList<List<Pair>>();
            for (int i = 0; i < cols; i++)
                transpose.add(new ArrayList<Pair>());
            // Build transpose matrix on-the-fly
            for (int row = 1; row <= rows; row++) {
                st = new StringTokenizer(in.readLine().trim());
                StringTokenizer values = new StringTokenizer(in.readLine().trim());
                int actualCols = Integer.parseInt(st.nextToken());
                for (int j = 0; j < actualCols; j++) {
                    int col = Integer.parseInt(st.nextToken());
                    int value = Integer.parseInt(values.nextToken());
                    transpose.get(col - 1).add(new Pair(row, value));
                }
            }
            // Print results
            out.printf("%s %s\n", cols, rows);
            for (int i = 0; i < transpose.size(); i++) {
                out.print(transpose.get(i).size());
                for (Pair pair : transpose.get(i)) {
                    out.print(' ');
                    out.print(pair.col);
                }
                out.println();
                for (int j = 0; j < transpose.get(i).size(); j++) {
                    if (j != 0)
                        out.print(' ');
                    out.print(transpose.get(i).get(j).value);
                }
                out.println();
            }
            line = in.readLine();
        }
        out.flush();
    }

    private static class Pair {
        public int col;
        public int value;

        public Pair(int col, int value) {
            this.col = col;
            this.value = value;
        }
    }
}
