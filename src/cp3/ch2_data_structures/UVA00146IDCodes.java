package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// 146 - C++ STL Algorithm (Java Collections), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=82
class UVA00146IDCodes {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String line = in.readLine();
        while (line != null && !"#".equals(line)) {
            out.println(nextPermutation(line.toCharArray()));
            line = in.readLine();
        }
        out.close();
    }

    private static String nextPermutation(char[] word) {
        // next_permutation algorithm, similar to the C++ function
        // http://stackoverflow.com/questions/2920315/permutation-of-array
        // http://wordaligned.org/articles/next-permutation#tocwhats-happening-here
        for(int tail = word.length - 1; tail > 0; tail--) {
            // as long as the string is monotonically decreasing, keep
            // going. If it increases, it means we have found a suffix
            // for which we have completed all the possible permutations
            if (word[tail - 1] < word[tail]) {
                // That means we have to increase the item at the left
                // of the suffix by the min. amount, i.e. finding the
                // next biggest element in the suffix and swapping them
                int s = word.length - 1;
                while (word[tail - 1] >= word[s]) {
                    s--;
                }
                swap(word, tail - 1, s);
                // As we incremented the item at the left of the suffix,
                // we have a new suffix we have to permute, so we need
                // to reverse the suffix into a monotonically increasing
                // order again to start over
                for(int i = tail, j = word.length - 1; i < j; i++, j--) {
                    swap(word, i, j);
                }
                return new String(word);
            }
        }
        return "No Successor";
    }

    private static void swap(char[] word, int a, int b) {
        char aux = word[a];
        word[a] = word[b];
        word[b] = aux;
    }
}
