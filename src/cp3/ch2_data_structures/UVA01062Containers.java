package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

// 1062 - C++ STL stack (Java Stack), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3503
class UVA01062Containers {

    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static PrintWriter out = new PrintWriter(System.out);

    public static void main(String args[]) throws NumberFormatException, IOException {
        int test = 1;
        String line = in.readLine();
        while (line != null && !"end".equals(line)) {
            int bitSetShips = 0;
            List<Deque<Integer>> terminal = new ArrayList<>(26);
            for (int i = 0; i < line.length(); i++) {
                int ship = line.charAt(i) - 'A';
                bitSetShips |= 1 << ship;
                Deque<Integer> stack = getNextStack(terminal, ship);
                if (stack == null) {
                    stack = new ArrayDeque<>();
                    terminal.add(stack);
                }
                stack.push(ship);
            }
            int ships = countShips(bitSetShips);
            printResult(test++, Math.min(ships, terminal.size()));
            line = in.readLine();
        }
        out.flush();
    }

    private static Deque<Integer> getNextStack(List<Deque<Integer>> terminal, int ship) {
        Deque<Integer> bestStack = null;
        for (int i = 0; i < terminal.size(); i++) {
            Deque<Integer> stack = terminal.get(i);
            if (isValid(stack, ship) && isBetter(stack, bestStack))
                bestStack = terminal.get(i);
        }
        return bestStack;
    }

    private static boolean isBetter(Deque<Integer> stack, Deque<Integer> best) {
        assert !stack.isEmpty();
        return best == null || stack.peek() < best.peek();
    }

    private static boolean isValid(Deque<Integer> stack, int ship) {
        assert !stack.isEmpty();
        return stack.peek() >= ship;
    }

    private static int countShips(int bitSetShips) {
        int ships = 0;
        for (int i = 0; i < 32; i++) {
            if ((bitSetShips & (1 << i)) > 0)
                ships++;
        }
        return ships;
    }

    private static void printResult(int test, int stacks) {
        out.print("Case ");
        out.print(test);
        out.print(": ");
        out.println(stacks);
    }

}
