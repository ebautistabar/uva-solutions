package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Queue;

// 11995 - C++ STL priority_queue (Java PriorityQueue), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3146
class UVA11995ICanGuessTheDataStructure {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(System.out);

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null) {
            boolean isStack = true;
            boolean isQueue = true;
            boolean isPriorityQueue = true;
            Deque<Integer> stack = new ArrayDeque<>();
            Queue<Integer> queue = new ArrayDeque<>();
            Queue<Integer> priorityQueue = new PriorityQueue<>();
            int commands = Integer.parseInt(line.trim());
            while (commands-- > 0) {
                String[] tokens = in.readLine().split("\\s+");
                int value = Integer.parseInt(tokens[1]);
                if ("1".equals(tokens[0])) {
                    if (isStack)
                        stack.push(value);
                    if (isQueue)
                        queue.add(value);
                    if (isPriorityQueue)
                        // I want a max queue. Easiest way to get it is using
                        // negative numbers
                        priorityQueue.add(-value);
                } else {// I assume there can't be anything else than 1 and 2
                    if (isStack && (stack.isEmpty() || stack.pop() != value))
                        isStack = false;
                    if (isQueue && (queue.isEmpty() || queue.remove() != value))
                        isQueue = false;
                    if (isPriorityQueue && (priorityQueue.isEmpty() || priorityQueue.remove() != -value))
                        isPriorityQueue = false;
                }
            }
            if ((isStack && isQueue) || (isStack && isPriorityQueue) || (isQueue && isPriorityQueue))
                out.println("not sure");
            else if (isStack)
                out.println("stack");
            else if (isQueue)
                out.println("queue");
            else if (isPriorityQueue)
                out.println("priority queue");
            else
                out.println("impossible");
            line = in.readLine();
        }
        out.flush();
    }

}
