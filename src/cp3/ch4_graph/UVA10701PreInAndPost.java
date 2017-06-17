package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 10701 - Special Graph (Others), Trees, not starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1642
// notes below
class UVA10701PreInAndPost {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static int preorderIdx;

    public static void main(String args[]) throws IOException {
        int tests = parseInt(getLine());
        while (tests-- > 0) {
            StringTokenizer st = new StringTokenizer(getLine());
            st.nextToken(); // ignore number of nodes
            String preorder = st.nextToken();
            String inorder = st.nextToken();
            int[] inorderIndices = new int[60]; // 26 uppercase, 26 lowercase and some padding for the chars in the middle
            for (int i = 0; i < inorder.length(); i++) {
                int letter = inorder.charAt(i) - 'A'; // uppercase is the first valid char
                inorderIndices[letter] = i;
            }
            preorderIdx = 0;
            Tree tree = buildTree(preorder, inorder, 0, inorder.length(), inorderIndices);
            StringBuilder sb = new StringBuilder();
            getPostOrder(tree, sb);
            out.println(sb);
        }
        out.close();
    }

    private static void getPostOrder(Tree tree, StringBuilder sb) {
        if (tree.left != null)
            getPostOrder(tree.left, sb);
        if (tree.right != null)
            getPostOrder(tree.right, sb);
        sb.append(tree.value);
    }

    private static Tree buildTree(String preorder, String inorder, int start, int end, int[] inorderIndices) {
        if (start == end) {
            return null;
        }
        Tree tree = new Tree();
        tree.value = preorder.charAt(preorderIdx++);
        int inorderIdx = inorderIndices[tree.value - 'A'];
        tree.left = buildTree(preorder, inorder, start, inorderIdx, inorderIndices);
        tree.right = buildTree(preorder, inorder, inorderIdx + 1, end, inorderIndices);
        return tree;
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
        char value;
        Tree left;
        Tree right;
    }
}
/*
Reconstructing tree from preorder and inorder. We know the first item in the preorder is the root. If we then search
for that item in the inorder, we can split the inorder in two: items in the left subtree and items in the right
subtree. We then recurse on each of the halves, picking items from the preorder 1 by 1 from left to right. If the range
in the inorder is empty, it means this subtree is empty. Once the tree is built, postorder is straightforward.
*/
