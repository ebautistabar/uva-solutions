package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// 401 - Palindrome, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=342
class UVA00401Palindromes {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        char[] reverseTable = buildReverseTable();
        String line = null;
        while ((line = in.readLine()) != null) {
            boolean palindrome = true;
            boolean mirrored = true;
            int l = 0, r = line.length() - 1;
            while (l <= r) {
                if (line.charAt(l) != line.charAt(r)) {
                    palindrome = false;
                }
                if (line.charAt(l) != reverseTable[line.charAt(r)]) {
                    mirrored = false;
                }
                if (palindrome || mirrored) {
                    l++;
                    r--;
                } else {
                    l = r + 1; //break
                }
            }
            out.print(line);
            if (palindrome && mirrored) {
                out.println(" -- is a mirrored palindrome.");
            } else if (palindrome) {
                out.println(" -- is a regular palindrome.");
            } else if (mirrored) {
                out.println(" -- is a mirrored string.");
            } else {
                out.println(" -- is not a palindrome.");
            }
            out.println();
        }
        out.close();
    }

    private static char[] buildReverseTable() {
        char[] table = new char[100];
        table['A'] = 'A';
        table['E'] = '3';
        table['H'] = 'H';
        table['I'] = 'I';
        table['J'] = 'L';
        table['L'] = 'J';
        table['M'] = 'M';
        table['O'] = 'O';
        table['S'] = '2';
        table['T'] = 'T';
        table['U'] = 'U';
        table['V'] = 'V';
        table['W'] = 'W';
        table['X'] = 'X';
        table['Y'] = 'Y';
        table['Z'] = '5';
        table['1'] = '1';
        table['2'] = 'S';
        table['3'] = 'E';
        table['5'] = 'Z';
        table['8'] = '8';
        return table;
    }

}
