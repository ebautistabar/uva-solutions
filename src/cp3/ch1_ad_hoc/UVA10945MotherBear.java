package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// 10945 - Palindrome, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1886
class UVA10945MotherBear {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String line = in.readLine();
        while (!line.equals("DONE")) {
            if (isPalindrome(cleanLine(line))) {
                out.println("You won't be eaten!");
            } else {
                out.println("Uh oh..");
            }
            line = in.readLine();
        }
        out.close();
    }

    private static boolean isPalindrome(String line) {
        int l = 0, r = line.length() - 1;
        while (l <= r) {
            if (line.charAt(l) != line.charAt(r)) {
                return false;
            }
            l++;
            r--;
        }
        return true;
    }

    private static String cleanLine(String line) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c > 'Z') { // it's lower case
                c -= 'a' - 'A';
            }
            if (c >= 'A') { // it's a letter
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

}
