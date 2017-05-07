package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.BitSet;

// 11926 - Bit Manipulation, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3077
class UVA11926Multitasking {

    private static final int MAX_MINUTES = 1_000_000;

    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static PrintWriter out = new PrintWriter(System.out);

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null && !"0 0".equals(line)) {
            String[] tasks = line.split("\\s+");
            int oneTimeTasks = Integer.parseInt(tasks[0]);
            int repeatingTasks = Integer.parseInt(tasks[1]);
            BitSet calendar = new BitSet(MAX_MINUTES + 1);
            boolean conflict = false;
            while (oneTimeTasks-- > 0) {
                line = in.readLine();
                if (!conflict) {
                    String[] range = line.split("\\s+");
                    int start = Integer.parseInt(range[0]);
                    int end = Integer.parseInt(range[1]);
                    conflict = isOverlapping(calendar, start, end);
                }
            }
            while (repeatingTasks-- > 0) {
                line = in.readLine();
                if (!conflict) {
                    String[] range = line.split("\\s+");
                    int start = Integer.parseInt(range[0]);
                    int end = Integer.parseInt(range[1]);
                    int period = Integer.parseInt(range[2]);
                    while (start < MAX_MINUTES) {
                        conflict = isOverlapping(calendar, start, end);
                        if (conflict)
                            break;
                        start += period;
                        end += period;
                    }
                }
            }
            printResult(conflict);
            line = in.readLine();
        }
        out.flush();
    }

    private static boolean isOverlapping(BitSet calendar, int start, int end) {
        for (int i = start; i < end && i <= MAX_MINUTES; i++) {
            if (calendar.get(i))
                return true;
            calendar.set(i);
        }
        return false;
    }

    private static void printResult(boolean conflict) {
        if (conflict) {
            out.println("CONFLICT");
        } else {
            out.println("NO CONFLICT");
        }
    }

}
