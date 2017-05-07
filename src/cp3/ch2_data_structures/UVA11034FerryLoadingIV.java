package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

// 11034 - C++ STL queue and deque (Java Queue and Deque), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1975
class UVA11034FerryLoadingIV {

    private static final int LEFT = 0;
    private static final int RIGHT = 1;

    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static PrintWriter out = new PrintWriter(System.out);

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        while (tests-- > 0) {
            String[] params = in.readLine().trim().split("\\s+");
            int lengthInCentimeters = Integer.parseInt(params[0]) * 100;
            int cars = Integer.parseInt(params[1]);
            int currentBank = LEFT;
            int trips = 0;
            List<Queue<Integer>> queues = buildQueues(cars);
            while (isCarWaiting(queues)) {
                if (isCarReadyInBank(queues, currentBank)) {
                    moveCars(queues.get(currentBank), lengthInCentimeters);
                    currentBank = getOppositeBank(currentBank);
                    trips++;
                } else {
                    moveCars(queues.get(getOppositeBank(currentBank)), lengthInCentimeters);
                    trips += 2;
                }
            }
            out.println(trips);
        }
        out.flush();
    }

    private static void moveCars(Queue<Integer> queue, int capacity) {
        while (!queue.isEmpty() && queue.peek() <= capacity) {
            capacity -= queue.remove();
        }
    }

    private static int getOppositeBank(int bank) {
        return bank ^ 1;
    }

    private static boolean isCarReadyInBank(List<Queue<Integer>> queues,
            int bank) {
        return !queues.get(bank).isEmpty();
    }

    private static List<Queue<Integer>> buildQueues(int cars) throws IOException {
        List<Queue<Integer>> queues = new ArrayList<Queue<Integer>>(2);
        queues.add(new ArrayDeque<Integer>());
        queues.add(new ArrayDeque<Integer>());
        String[] params;
        for (int i = 0; i < cars; i++) {
            params = in.readLine().trim().split("\\s+");
            int length = Integer.parseInt(params[0]);
            int bank = getBank(params[1]);
            queues.get(bank).add(length);
        }
        return queues;
    }

    private static int getBank(String name) {
        return "left".equals(name) ? LEFT : RIGHT;
    }

    private static boolean isCarWaiting(List<Queue<Integer>> queues) {
        return queues.get(LEFT).size() > 0 || queues.get(RIGHT).size() > 0;
    }

}
