package cp3.ch1_ad_hoc;
import java.io.*;
import java.util.*;

// 11172 - Super Easy, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2113
class UVA11172RelationalOperator {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(in.readLine());
        for (int i = 0; i < t; i++) {
            StringTokenizer tokenizer = new StringTokenizer(in.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());
            if (a < b) {
                System.out.println('<');
            } else if (a > b) {
                System.out.println('>');
            } else {
                System.out.println('=');
            }
        }
    }

}
