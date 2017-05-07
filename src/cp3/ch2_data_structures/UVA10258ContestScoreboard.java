package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

// 10258 - C++ STL Algorithm (Java Collections), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1199
class UVA10258ContestScoreboard {

    private static Comparator<Contestant> comparator = new Comparator<Contestant>() {
        @Override
        public int compare(Contestant arg0, Contestant arg1) {
            // contestants that don't submit are null, go to the back
            if (arg0 == null) {
                return arg1 == null ? 0 : 1;
            } else if (arg1 == null) {
                return -1;
            }
            // number of problems, greater goes first
            if (arg0.solved != arg1.solved) {
                return Integer.compare(arg1.solved, arg0.solved);
            }
            // penalty time, smaller goes first
            if (arg0.time != arg1.time) {
                return Integer.compare(arg0.time, arg1.time);
            }
            // break tie by team number
            return Integer.compare(arg0.team, arg1.team);
        }
    };

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int t = Integer.parseInt(in.readLine().trim());
        in.readLine(); // blank line
        while (t-- > 0) {
            Contestant[] contestants = new Contestant[100];
            String line = in.readLine();
            while (line != null && !line.isEmpty()) {
                String[] tokens = line.split("\\s+");
                int id = Integer.parseInt(tokens[0]);
                Contestant c = contestants[id - 1];
                if (c == null) {
                    c = new Contestant();
                    c.team = id;
                    contestants[id - 1] = c;
                }
                int problem = Integer.parseInt(tokens[1]) - 1;
                // only consider the submission if it's not correct yet
                if (c.penalty[problem] != -1) {
                    switch (tokens[3].charAt(0)) {
                    case 'C':
                        c.time += Integer.parseInt(tokens[2]) + c.penalty[problem];
                        c.penalty[problem] = -1;
                        c.solved++;
                        break;
                    case 'I':
                        c.penalty[problem] += 20;
                        break;
                    default:
                    }
                }
                line = in.readLine();
            }
            Arrays.sort(contestants, comparator);
            for (Contestant c : contestants) {
                if (c == null) {
                    // all the nulls are at the back, so stop loop
                    break;
                }
                out.print(c.team);
                out.print(' ');
                out.print(c.solved);
                out.print(' ');
                out.println(c.time);
            }
            if (t != 0) {
                out.println();
            }
        }
        out.close();
    }

    private static class Contestant {
        public int team = -1;
        public int time = 0;
        public int solved = 0;
        public int[] penalty = new int[9];
    }
}
