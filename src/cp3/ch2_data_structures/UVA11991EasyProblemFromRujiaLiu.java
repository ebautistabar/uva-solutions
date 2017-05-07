package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

// 11991 - Graph Data Structures Problems, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3142
class UVA11991EasyProblemFromRujiaLiu {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line.trim());
            int items = Integer.parseInt(st.nextToken());
            int queries = Integer.parseInt(st.nextToken());
            Map<Integer, List<Integer>> positions = new HashMap<>();
            st = new StringTokenizer(in.readLine().trim());
            for (int i = 1; i <= items; i++) {
                int num = Integer.parseInt(st.nextToken());
                List<Integer> indices = positions.get(num);
                if (indices == null) {
                    indices = new ArrayList<>();
                    positions.put(num, indices);
                }
                indices.add(i);
            }
            for (int i = 0; i < queries; i++) {
                st = new StringTokenizer(in.readLine().trim());
                int kthOccurrence = Integer.parseInt(st.nextToken()) - 1;
                int num = Integer.parseInt(st.nextToken());
                List<Integer> indices = positions.get(num);
                if (indices == null || indices.size() <= kthOccurrence)
                    out.println(0);
                else
                    out.println(indices.get(kthOccurrence));
            }
            line = in.readLine();
        }
        out.flush();
    }
}
