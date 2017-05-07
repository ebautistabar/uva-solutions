package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 12532 - Tree-related Data Structures, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3977
//
// Reference: http://pavelsimo.blogspot.com.es/2012/11/uva-12532-interval-product.html
class UVA12532IntervalProduct {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            int length = Integer.parseInt(st.nextToken());
            int rounds = Integer.parseInt(st.nextToken());
            FenwickTree zeros = new FenwickTree(length);
            FenwickTree negatives = new FenwickTree(length);
            st = new StringTokenizer(in.readLine());
            int[] numbers = new int[length + 1];
            for (int i = 1; i <= length; i++) {
                numbers[i] = Integer.parseInt(st.nextToken());
                if (numbers[i] == 0)
                    zeros.adjust(i, 1);
                else if (numbers[i] < 0)
                    negatives.adjust(i, 1);
            }
            while (rounds-- > 0) {
                st = new StringTokenizer(in.readLine());
                char command = st.nextToken().charAt(0);
                int param1 = Integer.parseInt(st.nextToken());
                int param2 = Integer.parseInt(st.nextToken());
                if (command == 'C') {
                    // Remove old value
                    if (numbers[param1] == 0)
                        zeros.adjust(param1, -1);
                    else if (numbers[param1] < 0)
                        negatives.adjust(param1, -1);
                    // Set new value
                    if (param2 == 0)
                        zeros.adjust(param1, 1);
                    else if (param2 < 0)
                        negatives.adjust(param1, 1);
                    numbers[param1] = param2;
                } else { // assume command 'P'
                    if (zeros.rangeSumQuery(param1, param2) > 0)
                        out.print('0');
                    else if (negatives.rangeSumQuery(param1, param2) % 2 == 1) // is odd
                        out.print('-');
                    else
                        out.print('+');
                }
            }
            out.println();
            line = in.readLine();
        }
        out.flush();
    }

    private static class FenwickTree {
        private int[] sum;

        // Receives number of possible distinct values
        public FenwickTree(int size) {
            sum = new int[size + 1]; // it's 1-based, hence the plus 1
        }

        public int rangeSumQuery(int left, int right) {
            if (left == 1)
                return rangeSumQuery(right);
            else
                return rangeSumQuery(right) - rangeSumQuery(left - 1);
        }

        private int rangeSumQuery(int right) {
            int rangeSum = 0;
            while (right > 0) {
                rangeSum += sum[right];
                right -= leastSignificantOneBit(right);
            }
            return rangeSum;
        }

        private int leastSignificantOneBit(int num) {
            return num & -num;
        }

        public void adjust(int index, int value) {
            while (index < sum.length) {
                sum[index] += value;
                index += leastSignificantOneBit(index);
            }
        }
    }
}
