package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// 12085 - 'Time Waster' Problems, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3237
class UVA12085MobileCasanova {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int j = 1;
        String line = in.readLine();
        while (line != null && !line.equals("0")) {
            out.print("Case ");
            out.print(j);
            out.println(':');
            int n = Integer.parseInt(line);
            String start = in.readLine();
            int startNum = Integer.parseInt(start);
            String previous = start;
            int previousNum = startNum;
            int counter = 0;
            for (int i = 1; i < n; i++) {
                String current = in.readLine();
                int currentNum = Integer.parseInt(current);
                if (previousNum + 1 == currentNum) {
                    counter++;
                } else {
                    printNumber(out, start, previous, counter);
                    counter = 0;
                    start = current;
                }
                previous = current;
                previousNum = currentNum;
            }
            printNumber(out, start, previous, counter);
            out.println();
            j++;
            line = in.readLine();
        }
        out.close();
    }

    private static void printNumber(PrintWriter out, String start,
            String previous, int counter) {
        out.print(start);
        if (counter > 0) {
            int k = 0;
            while (k < start.length() && k < previous.length()) {
                if (start.charAt(k) != previous.charAt(k)) {
                    break;
                }
                k++;
            }
            // assumes numbers have always the same length
            if (k < start.length() && k < previous.length()) {
                out.print('-');
                out.print(previous.substring(k));
            }
        }
        out.println();
    }

}
