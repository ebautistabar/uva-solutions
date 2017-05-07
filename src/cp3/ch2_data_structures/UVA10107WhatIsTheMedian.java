package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.PriorityQueue;

// 10107 - C++ STL Algorithm (Java Collections), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1048
class UVA10107WhatIsTheMedian {

    private static Comparator<Integer> reverseOrder = new Comparator<Integer>() {
        @Override
        public int compare(Integer arg0, Integer arg1) {
            return Integer.compare(arg1, arg0);
        }
    };

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String line = in.readLine();
        int num = Integer.parseInt(line.trim());
        out.println(num);
        line = in.readLine();
        if (line != null) {
            PriorityQueue<Integer> low = new PriorityQueue<>(reverseOrder);
            PriorityQueue<Integer> high = new PriorityQueue<>();
            // Process second number and add both to queues
            int num2 = Integer.parseInt(line.trim());
            if (num < num2) {
                low.add(num);
                high.add(num2);
            } else {
                low.add(num2);
                high.add(num);
            }
            printMedian(low, high, out);
            // Process the rest of the numbers
            line = in.readLine();
            while (line != null) {
                num = Integer.parseInt(line.trim());
                if (num < low.peek()) {
                    low.add(num);
                } else {
                    high.add(num);
                }
                // Balance the heaps: a difference of 1 item at most
                if (low.size() - high.size() > 1) {
                    high.add(low.poll());
                } else if (high.size() - low.size() > 1) {
                    low.add(high.poll());
                }
                printMedian(low, high, out);
                line = in.readLine();
            }
        }
        out.close();
    }

    private static void printMedian(PriorityQueue<Integer> small,
            PriorityQueue<Integer> big, PrintWriter out) {
        if (small.size() < big.size()) {
            out.println(big.peek());
        } else if (small.size() > big.size()) {
            out.println(small.peek());
        } else {
            out.println((small.peek() + big.peek()) / 2);
        }
    }

}
