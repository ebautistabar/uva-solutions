package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// 10954 - C++ STL priority_queue (Java PriorityQueue), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1895
class UVA10954AddAll {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null && !"0".equals(line.trim())) {
            int n = Integer.parseInt(line.trim());
            PriorityQueue<Integer> numbers = new PriorityQueue<>();
            StringTokenizer st = new StringTokenizer(in.readLine().trim());
            while (n-- > 0)
                numbers.add(Integer.parseInt(st.nextToken()));
            long cost = 0;
            while (numbers.size() > 1) {
                int sum = numbers.poll() + numbers.poll();
                numbers.add(sum);
                cost += sum;
            }
            out.println(cost);
            line = in.readLine();
        }
        out.flush();
    }

}
