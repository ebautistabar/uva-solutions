package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 00839 - Special Graph (Others), Trees, not starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=780
// notes below
class UVA00839NotSoMobile {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        int tests = parseInt(getLine());
        while (tests-- > 0) {
            Tree t = buildTree();
            int weight = getBalancedWeight(t);
            if (weight > 0) {
                out.println("YES");
            } else {
                out.println("NO");
            }
            if (tests > 0) {
                out.println();
            }
        }
        out.close();
    }

    private static Tree buildTree() {
        Tree t = new Tree(0);
        StringTokenizer st = new StringTokenizer(getLine());
        int leftWeight = parseInt(st.nextToken());
        t.leftDist = parseInt(st.nextToken());
        int rightWeight = parseInt(st.nextToken());
        t.rightDist = parseInt(st.nextToken());
        if (leftWeight == 0) {
            t.left = buildTree();
        } else {
            t.left = new Tree(leftWeight);
        }
        if (rightWeight == 0) {
            t.right = buildTree();
        } else {
            t.right = new Tree(rightWeight);
        }
        return t;
    }

    private static int getBalancedWeight(Tree t) {
        if (t.left == null && t.right == null) { // no sub-mobiles
            return t.value;
        }
        int leftWeight = getBalancedWeight(t.left);
        int rightWeight = getBalancedWeight(t.right);
        if (leftWeight == 0 || rightWeight == 0) {
            return 0; // if the sub-mobiles are unbalanced, propagate to root
        }
        if (leftWeight * t.leftDist == rightWeight * t.rightDist) {
            return leftWeight + rightWeight; // if balanced, return sum of weights
        }
        return 0; // if unbalanced, return 0 to represent it
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
        int leftDist;
        int rightDist;
        Tree left;
        Tree right;
        public Tree(int value) {
            this.value = value;
        }
    }
}
/*
A tree with no children represents a weight. If a tree has children, it represents a mobile. Tree is built with DFS.
When checking the balanced property, propagate weights upward. If a child is unbalanced, the whole mobile is unbalanced.
*/
