package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// 12060 - 'Time Waster' Problems, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3212
class UVA12060AllIntegerAverage {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String line = in.readLine();
        int j = 1;
        while (line != null && !line.equals("0")) {
            String[] nums = line.split("\\s+");
            int n = Integer.parseInt(nums[0]);
            int sum = 0;
            for (int i = 1; i < nums.length; i++) {
                sum += Integer.parseInt(nums[i]);
            }
            int a = sum / n;
            int b = sum % n;
            int c = n;
            int gcd = Math.abs(b) > Math.abs(c) ? gcd(Math.abs(b), Math.abs(c)) : gcd(Math.abs(c), Math.abs(b));
            b /= gcd;
            c /= gcd;
            print(j, a, b, c, out);
            j++;
            line = in.readLine();
        }
        out.close();
    }

    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    private static void print(int j, int a, int b, int c, PrintWriter out) {
        System.out.print("Case ");
        System.out.print(j);
        System.out.println(":");
        StringBuilder sb = new StringBuilder();
        if (a < 0 || b < 0) {
            sb.append("- ");
            a *= -1;
            b *= -1;
        }
        if (a != 0 || b == 0) {
            sb.append(a);
        }
        if (b == 0) {
            System.out.println(sb.toString());
        } else {
            int lenA = sb.length();
            int lenC = getDigits(c);
            System.out.printf("%" + (lenA + lenC) + "d\n", b);
            System.out.print(sb.toString());
            for (int i = 0; i < lenC; i++) {
                System.out.print('-');
            }
            System.out.println();
            System.out.printf("%" + (lenA + lenC) + "d\n", c);
        }
    }

    private static int getDigits(int c) {
        int digits = 0;
        while (c != 0) {
            c /= 10;
            digits++;
        }
        return digits;
    }

}
