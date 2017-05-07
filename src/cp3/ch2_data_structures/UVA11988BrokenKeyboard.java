package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// 11988 - C++ STL list (Java LinkedList), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3139
class UVA11988BrokenKeyboard {

    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static PrintWriter out = new PrintWriter(System.out);

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null) {
            Node result = getRealText(line);
            printResult(result);
            line = in.readLine();
        }
        out.flush();
    }

    private static Node getRealText(String line) {
        Node head = new Node();
        Node tail = head;
        Node current = head;
        boolean isAppend = true;
        for (int i = 0; i < line.length(); i++) {
            switch (line.charAt(i)) {
            case '[':
                current = head;
                isAppend = current.next == null;
                break;
            case ']':
                current = tail;
                isAppend = true;
                break;
            default:
                Node newNode = new Node();
                newNode.character = line.charAt(i);
                newNode.next = current.next;
                current.next = newNode;
                current = newNode;
                if (isAppend) {
                    tail = current;
                }
            }
        }
        return head.next;
    }

    private static void printResult(Node node) {
        while (node != null) {
            out.print(node.character);
            node = node.next;
        }
        out.println();
    }

    private static class Node {
        public Node next;
        public char character;
    }
}
