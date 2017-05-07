package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Deque;

// 514 - C++ STL stack (Java Stack), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=455
class UVA00514Rails {

    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static PrintWriter out = new PrintWriter(System.out);

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null && !"0".equals(line)) {
            Integer.parseInt(line); // number of coaches, don't really need them
            line = in.readLine();
            while (line != null && !"0".equals(line)) {
                int[] order = getOrder(line);
                if (isPossible(order))
                    out.println("Yes");
                else
                    out.println("No");
                line = in.readLine();
            }
            out.println();
            line = in.readLine();
        }
        out.flush();
    }

    private static int[] getOrder(String line) {
        String[] coaches = line.split("\\s+");
        int[] order = new int[coaches.length];
        for (int i = 0; i < order.length; i++) {
            order[i] = Integer.parseInt(coaches[i]);
        }
        return order;
    }

    private static boolean isPossible(int[] outgoingCoaches) {
        Deque<Integer> station = new ArrayDeque<>();
        int incoming = 1;
        int nextOutgoing = 1;
        int coachAmount = outgoingCoaches.length;
        while (incoming <= coachAmount || !station.isEmpty()) {
            if (!station.isEmpty() && station.peek() == outgoingCoaches[nextOutgoing - 1]) {
                station.pop();
                nextOutgoing++;
            } else if (incoming <= coachAmount)
                station.push(incoming++);
            else
                break;
        }
        return incoming > coachAmount && station.isEmpty();
    }
}
