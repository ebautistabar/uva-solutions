package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

// 732 - C++ STL stack (Java Stack), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=673
class UVA00732AnagramsByStack {

    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static PrintWriter out = new PrintWriter(System.out);

    private static List<String> sequences;

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = getNonEmptyLine();
        while (line != null) {
            sequences = new ArrayList<String>();
            String source = line.trim();
            String target = getNonEmptyLine().trim();
            getSequences(source, target);
            printSequences(sequences);
            line = getNonEmptyLine();
        }
        out.flush();
    }

    private static String getNonEmptyLine() throws IOException {
        String line = in.readLine();
        while (line != null && "".equals(line.trim())) {
            line = in.readLine();
        }
        return line;
    }

    private static void getSequences(String source, String target) {
        int posSource = 0;
        int posTarget = 0;
        StringBuilder operations = new StringBuilder();
        Deque<Character> stack = new ArrayDeque<>();
        getSequences(source, target, posSource, posTarget, stack, operations);
    }

    private static void getSequences(String source, String target,
            int posSource, int posTarget, Deque<Character> stack, StringBuilder operations) {
        if (posTarget == target.length()) {
            sequences.add(operations.toString());
        } else {
            if (posSource != source.length()) {
                stack.push(source.charAt(posSource));
                posSource++;
                operations.append('i');
                getSequences(source, target, posSource, posTarget, stack, operations);
                operations.setLength(operations.length() - 1);
                posSource--;
                stack.pop();
            }
            if (!stack.isEmpty() && stack.peek() == target.charAt(posTarget)) {
                char item = stack.pop();
                posTarget++;
                operations.append('o');
                getSequences(source, target, posSource, posTarget, stack, operations);
                operations.setLength(operations.length() - 1);
                posTarget--;
                stack.push(item);
            }
        }
    }

    private static void printSequences(List<String> sequences) {
        Collections.sort(sequences);
        out.println('[');
        for (String sequence : sequences) {
            out.print(sequence.charAt(0));
            for (int i = 1; i < sequence.length(); i++) {
                out.print(' ');
                out.print(sequence.charAt(i));
            }
            out.println();
        }
        out.println(']');
    }
}
