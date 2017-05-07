package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

// 10567 - Binary Search, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1508
class UVA10567HelpingFillBates {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        // Reads letters and associates them with the position
        int letters = 'z' - 'A' + 1;
        List<List<Integer>> positions = new ArrayList<List<Integer>>(letters);
        for (int i = 0; i < letters; i++) {
            positions.add(new ArrayList<Integer>());
        }
        String line = in.readLine();
        for (int i = 0; i < line.length(); i++) {
            char letter = line.charAt(i);
            int idx = letter - 'A';
            positions.get(idx).add(i);
        }
        // Answer queries
        int queries = Integer.parseInt(in.readLine().trim());
        for (int i = 0; i < queries; i++) {
            int firstCandidate = 0;
            int lastCandidate = 0;
            int candidate = -1;
            int position = -1;
            line = in.readLine();
            for (int j = 0; j < line.length(); j++) {
                int idx = line.charAt(j) - 'A';
                List<Integer> positionsForLetter = positions.get(idx);
                position = lowerBound(positionsForLetter, candidate + 1);
                if (position == -1) {
                    break;
                }
                candidate = positionsForLetter.get(position);
                if (j == 0) {
                    firstCandidate = candidate;
                }
                if (j == line.length() - 1) {
                    lastCandidate = candidate;
                }
            }
            if (position == -1) {
                out.println("Not matched");
            } else {
                out.printf("Matched %d %d%n", firstCandidate, lastCandidate);
            }
        }
        out.close();
    }

    // Returns index of first item with value >= target. Given array is sorted
    private static int lowerBound(List<Integer> values, int target) {
        int start = 0;
        int end = values.size() - 1;
        while (end - start + 1 > 1) { // finish on length < 2 (1 or 0)
            int mid = start + (end - start) / 2;
            if (values.get(mid) < target) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        if (start < values.size() && target <= values.get(start)) {
            return start;
        }
        return -1;
    }

}
