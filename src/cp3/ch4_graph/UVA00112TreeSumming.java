package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

// 00112 - Special Graph (Others), Trees, not starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1400
// tree building and traversal, notes below
class UVA00112TreeSumming {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static String line = "";
    private static int pointer = 0;

    public static void main(String args[]) throws IOException {
        refreshLine();
        while (line != null) {
            int goal = readInt();
            Tree tree = buildTree();
            if (isTherePathToLeafWithSum(tree, goal)) {
                out.println("yes");
            } else {
                out.println("no");
            }
            refreshLine();
        }
        out.close();
    }

    private static Tree buildTree() {
        readOneParens();
        Tree tree = null;
        if (isThereNumber()) {
            tree = new Tree(readInt(), buildTree(), buildTree());
        }
        readOneParens();
        return tree;
    }

    private static boolean isThereNumber() {
        refreshLine();
        while (pointer < line.length() && Character.isWhitespace(line.charAt(pointer))) {
            pointer++;
        }
        return line.charAt(pointer) == '-' || Character.isDigit(line.charAt(pointer));
    }

    // it assumes that the next non-whitespace char is a parenthesis (either opening or closing)
    private static void readOneParens() {
        refreshLine();
        while (pointer < line.length() && Character.isWhitespace(line.charAt(pointer))) {
            pointer++;
        }
        pointer++; // we've consumed whitespace and we were expecting a parens, so it must be the next char
    }

    private static int readInt() {
        refreshLine();
        StringBuilder sb = new StringBuilder();
        while (pointer < line.length() && (line.charAt(pointer) == '-' || Character.isDigit(line.charAt(pointer)))) {
            sb.append(line.charAt(pointer));
            pointer++;
        }
        return parseInt(sb.toString());
    }

    private static void refreshLine() {
        if (pointer == line.length()) {
            line = getLine();
            if (line != null) {
                line = line.trim(); // remove whitespace at start and end, the rest of methods assume there's none
            }
            pointer = 0;
        }
    }

    private static boolean isTherePathToLeafWithSum(Tree t, int valueLeft) {
        if (t == null) {
            return false;
        }
        valueLeft -= t.value;
        if (t.left == null && t.right == null) { // it's leaf
            return valueLeft == 0;
        }
        if (isTherePathToLeafWithSum(t.left, valueLeft)) {
            return true;
        }
        if (isTherePathToLeafWithSum(t.right, valueLeft)) {
            return true;
        }
        return false;
    }

    private static String getLine() {
        try {
            String line = in.readLine();
            while (line != null && "".equals(line.trim())) // dealing with blank lines
                line = in.readLine();
            return line;
        } catch(Exception e) {
            return null;
        }
    }
    private static int parseInt(String text) {
        return Integer.parseInt(text.trim());
    }


    private static class Tree {
        int value;
        Tree left;
        Tree right;
        public Tree(int value, Tree left, Tree right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
        public String toString() {
            String s = "(" + value;
            if (left == null) {
                s += "()";
            } else {
                s += left.toString();
            }
            if (right == null) {
                s += "()";
            } else {
                s += right.toString();
            }
            return s + ")";
        }
    }
}
/*
Simply traverse the whole tree keeping track of the sum. The most tricky part, at least in Java, is parsing the input.
*/
