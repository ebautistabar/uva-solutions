package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

// 11057 - Binary Search, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1998
class UVA11057ExactSum {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null) {
            int books = Integer.parseInt(line.trim());
            StringTokenizer st = new StringTokenizer(in.readLine());
            int[] price = new int[books];
            for (int i = 0; i < price.length; i++) {
                price[i] = Integer.parseInt(st.nextToken());
            }
            int money = Integer.parseInt(in.readLine().trim());
            Arrays.sort(price);
            int price1 = 0;
            int price2 = 0;
            int diff = Integer.MAX_VALUE;
            for (int i = 0; i < price.length; i++) {
                int complement = money - price[i];
                if (Math.abs(price[i] - complement) < diff) {
                    int j = Arrays.binarySearch(price, complement);
                    if (i == j) {
                        if (j - 1 >= 0 && price[j - 1] == price[j]) {
                            j--;
                        } else if (j + 1 < price.length && price[j + 1] == price[j]) {
                            j++;
                        }
                    }
                    if (i < j) {
                        price1 = price[i];
                        price2 = price[j];
                    }
                }
            }
            out.printf("Peter should buy books whose prices are %d and %d.%n%n", price1, price2);
            line = in.readLine(); // empty line
            line = in.readLine();
        }
        out.close();
    }

}
