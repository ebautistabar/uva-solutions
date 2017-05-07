package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

// 624 - Complete Search, Recursive Backtracking (Easy), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=565
class UVA00624CD {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static int maxMinutes;
    private static int bestMinutes;
    private static int[] durations;
    private static Deque<Integer> bestDurations;

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null && !line.trim().isEmpty()) {
            StringTokenizer st = new StringTokenizer(line);
            maxMinutes = Integer.parseInt(st.nextToken());
            int tracks = Integer.parseInt(st.nextToken()); // <= 20
            durations = new int[tracks];
            for (int i = 0; i < durations.length; i++) {
                durations[i] = Integer.parseInt(st.nextToken());
            }

            // I could go over all subsets using a bitset, but the CP3 suggests
            // recursive approach. Let's try that
            bestMinutes = 0;
            Deque<Integer> chosenDurations = new ArrayDeque<>();
            int minutes = 0;
            int currentTrack = 0;
            recordBestSolution(currentTrack, minutes, chosenDurations);

            StringBuilder sb = new StringBuilder();
            while (!bestDurations.isEmpty()) {
                sb.append(bestDurations.removeFirst());
                sb.append(' ');
            }
            sb.append("sum:");
            sb.append(bestMinutes);
            out.println(sb.toString());
            line = in.readLine();
        }
        out.close();
    }

    private static void recordBestSolution(int track, int minutes, Deque<Integer> chosenDurations) {
        if (track == durations.length) {
            if (bestMinutes < minutes) {
                bestMinutes = minutes;
                bestDurations = new ArrayDeque<Integer>(chosenDurations);
            }
        } else {
            // Option 1: picking the track
            int duration = durations[track];
            if (minutes + duration <= maxMinutes) {
                chosenDurations.addLast(duration);
                recordBestSolution(track + 1, minutes + duration, chosenDurations);
                chosenDurations.removeLast();
            }
            // Option 2: leaving the track
            recordBestSolution(track + 1, minutes, chosenDurations);
        }
    }

}
