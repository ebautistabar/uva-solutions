package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

// 787 - Dynamic Programming, Max 1D Range Sum, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=728
class UVA00787MaximumSubsequenceProduct {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final BigInteger limit = new BigInteger("-999999");

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null) {
            List<BigInteger> nums = readSequence(line);
            BigInteger maxProduct = getMaxSubsequenceProduct(nums);
            out.println(maxProduct);
            line = in.readLine();
        }
        out.close();
    }

    private static List<BigInteger> readSequence(String startingLine) throws IOException {
        List<BigInteger> nums = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(startingLine);
        BigInteger num = new BigInteger(st.nextToken());
        while (!num.equals(limit)) {
            nums.add(num);
            if (!st.hasMoreTokens()) {
                st = new StringTokenizer(in.readLine());
            }
            num = new BigInteger(st.nextToken());
        }
        return nums;
    }

    private static BigInteger getMaxSubsequenceProduct(List<BigInteger> nums) {
        BigInteger result = limit;
        for (int start = 0; start < nums.size(); start++) {
            // memoization is done simply by accumulating the products which
            //  start at position 'start'
            BigInteger product = new BigInteger("1");
            for (int end = start; end < nums.size(); end++) {
                product = product.multiply(nums.get(end));
                result = result.max(product);
            }
        }
        return result;
    }
}
