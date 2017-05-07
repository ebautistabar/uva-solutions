package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// 11933 - Bit Manipulation, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3084
class UVA11933SplittingNumbers {

    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static PrintWriter out = new PrintWriter(System.out);

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null && !"0".equals(line)) {
            int ones = 0;
            int a = 0;
            int b = 0;
            int num = Integer.parseInt(line);
            for (int i = 0; i < 32; i++) { // we deal with ints, ie 32 bits
                if (isBitOne(num, i)) {
                    ones++;
                    if (isOdd(ones))
                        a = setBit(a, i);
                    else
                        b = setBit(b, i);
                }
            }
            printResult(a, b);
            line = in.readLine();
        }
        out.flush();
    }

    private static void printResult(int a, int b) {
        out.print(a);
        out.print(' ');
        out.println(b);
    }

    private static int setBit(int num, int i) {
        return num | (1 << i);
    }

    private static boolean isOdd(int num) {
        return (num & 1) == 1;
    }

    private static boolean isBitOne(int num, int i) {
        return (num & (1 << i)) > 0;
    }

}
