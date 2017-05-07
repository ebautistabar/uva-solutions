package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 11742 - Complete Search, Iterative (Fancy Techniques), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2842
class UVA11742SocialConstraints {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine().trim();
        while (!"0 0".equals(line)) {
            StringTokenizer st = new StringTokenizer(line);
            int seats = Integer.parseInt(st.nextToken());
            int constraintNum = Integer.parseInt(st.nextToken());
            int[] firstTeen = new int[constraintNum];
            int[] secondTeen = new int[constraintNum];
            int[] constraint = new int[constraintNum];
            for (int i = 0; i < constraintNum; i++) {
                st = new StringTokenizer(in.readLine());
                firstTeen[i] = Integer.parseInt(st.nextToken());
                secondTeen[i] = Integer.parseInt(st.nextToken());
                constraint[i] = Integer.parseInt(st.nextToken());
            }
            // Number of seats is small <= 8, so we can calculate permutations
            // and check the constraint for each one without getting TLE
            int[] teens = new int[seats];
            for (int i = 0; i < seats; i++)
                teens[i] = i;
            int validArrangement = 0;
            boolean hasNext = true;
            while (hasNext) {
                if (isValidArrangement(teens, firstTeen, secondTeen, constraint))
                    validArrangement++;
                teens = nextPermutation(teens);
                hasNext = teens != null;
            }
            out.println(validArrangement);
            line = in.readLine();
        }
        out.close();
    }

    private static boolean isValidArrangement(int[] teens, int[] firstTeen, int[] secondTeen, int[] constraint) {
        for (int i = 0; i < constraint.length; i++) {
            int firstTeenSeat = getSeat(firstTeen[i], teens);
            int secondTeenSeat = getSeat(secondTeen[i], teens);;
            int seatsApart = Math.abs(firstTeenSeat - secondTeenSeat);
            if (constraint[i] > 0 && seatsApart > constraint[i]
                    || constraint[i] < 0 && seatsApart < -1 * constraint[i])
                return false;
        }
        return true;
    }

    private static int getSeat(int teen, int[] teens) {
        for (int i = 0; i < teens.length; i++)
            if (teens[i] == teen)
                return i;
        return -1; // will never happen
    }

    private static int[] nextPermutation(int[] values) {
        // next_permutation algorithm, similar to the C++ function
        // http://stackoverflow.com/questions/2920315/permutation-of-array
        // http://wordaligned.org/articles/next-permutation#tocwhats-happening-here
        for(int tail = values.length - 1; tail > 0; tail--) {
            // as long as the string is monotonically decreasing, keep
            // going. If it increases, it means we have found a suffix
            // for which we have completed all the possible permutations
            if (values[tail - 1] < values[tail]) {
                // That means we have to increase the item at the left
                // of the suffix by the min. amount, i.e. finding the
                // next biggest element in the suffix and swapping them
                int s = values.length - 1;
                while (values[tail - 1] >= values[s]) {
                    s--;
                }
                swap(values, tail - 1, s);
                // As we incremented the item at the left of the suffix,
                // we have a new suffix we have to permute, so we need
                // to reverse the suffix into a monotonically increasing
                // order again to start over
                for(int i = tail, j = values.length - 1; i < j; i++, j--) {
                    swap(values, i, j);
                }
                return values;
            }
        }
        return null;
    }

    private static void swap(int[] values, int a, int b) {
        int aux = values[a];
        values[a] = values[b];
        values[b] = aux;
    }
}
