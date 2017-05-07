package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

// 524 - Complete Search, Recursive Backtracking (Medium), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=465
class UVA00524PrimeRingProblem {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static int primes = buildPrimeSet();

    public static void main(String args[]) throws NumberFormatException, IOException {
        int test = 1;
        String line = in.readLine();
        while (line != null) {
            if ("".equals(line.trim())) {
                line = in.readLine();
            }
            int n = Integer.parseInt(line.trim());
            if (test > 1)
                out.println();
            out.printf("Case %d:%n", test++);

            int position = 1;
            int[] num = new int[n];
            num[0] = 1;
            int used = 0b10;
            printValidRings(num, position, used);

            line = in.readLine();
        }
        out.close();
    }

    private static void printValidRings(int[] nums, int position, int used) {
        if (position == nums.length) {
            if (isPrime(nums[0] + nums[position - 1])) {
                printRing(nums);
            }
        } else {
            for (int num = 2; num <= nums.length; num++) {
                if ((used & (1 << num)) == 0
                        && isPrime(num + nums[position - 1])) {
                    used |= 1 << num;
                    nums[position] = num;
                    printValidRings(nums, position + 1, used);
                    used &= ~(1 << num); // undo bit set
                }
            }
        }
    }

    private static boolean isPrime(int num) {
        return (primes & (1 << num)) != 0;
    }

    private static void printRing(int[] nums) {
        out.print(nums[0]);
        for (int i = 1; i < nums.length; i++) {
            out.print(' ');
            out.print(nums[i]);
        }
        out.println();
    }

    private static int buildPrimeSet() {
        // n <= 16, which means that the max possible prime is 16 + 15 = 31
        int[] primesUnder32 = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31};
        int bitset = 0;
        for (int i = 0; i < primesUnder32.length; i++) {
            bitset |= 1 << primesUnder32[i];
        }
        return bitset;
    }
}
