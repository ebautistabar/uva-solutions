package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

// 195 - Anagrams, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=131
class UVA00195Anagram {

    private static final CharComparator comparator = new CharComparator();

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int t = Integer.parseInt(in.readLine());
        while (t-- > 0) {
            String line = in.readLine();
            Character[] word = new Character[line.length()];
            for (int i = 0; i < word.length; i++) {
                word[i] = line.charAt(i);
            }
            Arrays.sort(word, comparator);
            // next_permutation algorithm, similar to the C++ function
            // http://stackoverflow.com/questions/2920315/permutation-of-array
            // http://wordaligned.org/articles/next-permutation#tocwhats-happening-here
            boolean hasNext = true;
            while (hasNext) {
                for (char c : word) {
                    out.print(c);
                }
                out.println();
                hasNext = false;
                // iterate from back to front
                for(int tail = word.length - 1; tail > 0; tail--) {
                    // as long as the string is monotonically decreasing, keep
                    // going. If it increases, it means we have found a suffix
                    // for which we have completed all the possible permutations
                    if (lessThan(word[tail - 1], word[tail])) {
                        // That means we have to increase the item at the left
                        // of the suffix by the min. amount, i.e. finding the
                        // next biggest element in the suffix and swapping them
                        int s = word.length - 1;
                        while (!lessThan(word[tail - 1], word[s])) {
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
                        hasNext = true;
                        break;
                    }
                }
            }
        }
        out.close();
    }

    private static boolean lessThan(char c1, char c2) {
        return comparator.compare(c1, c2) < 0;
    }

    private static void swap(Character[] arr, int a, int b) {
        char tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    private static class CharComparator implements Comparator<Character> {

        private static final int toLowerCase = 'a' - 'A';

        @Override
        public int compare(Character c1, Character c2) {
            // order is AaBbCc...
            // in ascii, upper-case goes before lower-case
            char lc1 = c1 > 'Z' ? c1 : (char)(c1 + toLowerCase);
            char lc2 = c2 > 'Z' ? c2 : (char)(c2 + toLowerCase);
            if (lc1 == lc2) {
                // upper-case (lower ascii value) goes first
                return Character.compare(c1, c2);
            } else {
                // case-insensitive alphabetical order
                // e.g. 'b' < 'C', despite 'C' < 'b' in the regular order
                return Character.compare(lc1, lc2);
            }
        }

    }
}
