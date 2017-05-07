package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

// 10172 - C++ STL queue and deque (Java Queue and Deque), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&category=24&problem=1113&mosmsg=Submission+received+with+ID+18538695
class UVA10172TheLonesomeCargoDistributor {

    private static final int CARGO_MOVEMENT_IN_MINUTES = 1;
    private static final int DISTANCE_BETWEEN_STATIONS_IN_MINUTES = 2;

    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static PrintWriter out = new PrintWriter(System.out);

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        while (tests-- > 0) {
            String[] params = in.readLine().trim().split("\\s+");
            int stations = Integer.parseInt(params[0]);
            int carrierCapacity = Integer.parseInt(params[1]);
            int platformCapacity = Integer.parseInt(params[2]);
            int cargosLeft = 0;
            List<Queue<Integer>> queues = new ArrayList<Queue<Integer>>();
            for (int i = 0; i < stations; i++) {
                StringTokenizer st = new StringTokenizer(in.readLine());
                cargosLeft += Integer.parseInt(st.nextToken());
                Queue<Integer> queue = new ArrayDeque<Integer>();
                while (st.hasMoreTokens()) {
                    queue.add(Integer.parseInt(st.nextToken()));
                }
                queues.add(queue);
            }
            // Simulation of process
            Deque<Integer> carrier = new ArrayDeque<Integer>();
            int currentStation = 0;
            int elapsedMinutes = 0;
            while (cargosLeft > 0) {
                Queue<Integer> queue = queues.get(currentStation);
                // Unloading
                while (carrier.size() > 0) {
                    if (carrier.peek() == currentStation + 1) {
                        carrier.pop();
                        elapsedMinutes += CARGO_MOVEMENT_IN_MINUTES;
                        cargosLeft--;
                    } else if (queue.size() < platformCapacity) {
                        queue.add(carrier.pop());
                        elapsedMinutes += CARGO_MOVEMENT_IN_MINUTES;
                    } else {
                        break;
                    }
                }
                // Loading
                while (carrier.size() < carrierCapacity && queue.size() > 0) {
                    carrier.push(queue.remove());
                    elapsedMinutes += CARGO_MOVEMENT_IN_MINUTES;
                }
                // Move to next station
                if (cargosLeft > 0)
                    elapsedMinutes += DISTANCE_BETWEEN_STATIONS_IN_MINUTES;
                currentStation = (currentStation + 1) % stations;
            }
            // Print result
            out.println(elapsedMinutes);
        }
        out.flush();
    }

}
