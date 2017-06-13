package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.TreeMap;

// 00699 - Special Graph (Others), Trees, not starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=640
// tree building and traversal, notes below
class UVA00699TheFallingLeaves {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static StringTokenizer st;

    public static void main(String args[]) throws IOException {
        int test = 1;
        st = new StringTokenizer(getLine());
        while (true) {
            Tree tree = buildTree();
            if (tree == null) {
                break;
            }
            TreeMap<Integer, Integer> piles = getPiles(tree);
            print(test++, piles);
        }
        out.close();
    }

    private static TreeMap<Integer, Integer> getPiles(Tree tree) {
        TreeMap<Integer, Integer> piles = new TreeMap<Integer, Integer>();
        int centralPileId = 0;
        populatePiles(tree, piles, centralPileId);
        return piles;
    }

    private static void populatePiles(Tree tree, TreeMap<Integer, Integer> piles, int pileId) {
        if (tree == null) {
            return;
        }
        Integer leaves = piles.get(pileId);
        if (leaves == null) {
            leaves = 0;
        }
        piles.put(pileId, leaves + tree.value);
        populatePiles(tree.left, piles, pileId - 1);
        populatePiles(tree.right, piles, pileId + 1);
    }

    private static void print(int test, TreeMap<Integer, Integer> piles) {
        out.printf("Case %d:%n", test);
        StringBuilder sb = new StringBuilder();
        for (int pile : piles.keySet()) {
            int leaves = piles.get(pile);
            sb.append(leaves);
            sb.append(' ');
        }
        sb.setLength(sb.length() - 1);
        out.println(sb);
        out.println();
    }

    private static Tree buildTree() {
        refreshTokenizer();
        int value = parseInt(st.nextToken());
        if (value == -1) {
            return null;
        }
        Tree tree = new Tree(value);
        tree.left = buildTree();
        tree.right = buildTree();
        return tree;
    }

    private static void refreshTokenizer() {
        if (!st.hasMoreTokens()) {
            st = new StringTokenizer(getLine());
        }
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
        public Tree(int value) {
            this.value = value;
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
Each pile has a pile id. Start the central pile with an arbitrary pile number (I chose 0). Every time you move right,
use id + 1. Every time you move left, use id - 1.
*/
