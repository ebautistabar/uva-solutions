package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

// 574 - Complete Search, Recursive Backtracking (Medium), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=515
class UVA00574SumItUp {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static int target;
    private static int nums;
    private static int[] values;
    private static Set<String> solutions;

    public static void main(String args[]) throws NumberFormatException, IOException {
        StringTokenizer st = new StringTokenizer(in.readLine());
        target = Integer.parseInt(st.nextToken());
        nums = Integer.parseInt(st.nextToken());
        while (nums != 0) {
            values = new int[nums];
            for (int i = 0; i < nums; i++) {
                values[i] = Integer.parseInt(st.nextToken());
            }
            solutions = new HashSet<String>();
            out.printf("Sums of %d:%n", target);

            int position = 0;
            int sum = 0;
            int used = 0;
            printSums(position, sum, used);

            if (solutions.isEmpty()) {
                out.println("NONE");
            }
            st = new StringTokenizer(in.readLine());
            target = Integer.parseInt(st.nextToken());
            nums = Integer.parseInt(st.nextToken());
        }
        out.close();
    }

    private static void printSums(int position, int sum, int used) {
        if (position == values.length) {
            if (sum == target) {
                printSum(used);
            }
        } else {
            // Picking the item
            if (sum + values[position] <= target) {
                used |= 1 << position;
                printSums(position + 1, sum + values[position], used);
                used &= ~(1 << position);
            }
            // Leaving the item
            printSums(position + 1, sum, used);
        }
    }

    private static void printSum(int used) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if ((used & (1 << i)) != 0) {
                sb.append(values[i]);
                sb.append('+');
            }
        }
        sb.setLength(sb.length() - 1);
        String solution = sb.toString();
        if (!solutions.contains(solution)) {
            solutions.add(solution);
            out.printf("%s%n", solution);
        }
    }

}
