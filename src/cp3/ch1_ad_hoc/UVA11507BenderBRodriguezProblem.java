package cp3.ch1_ad_hoc;
import java.io.*;
import java.util.*;

// 11507 - Medium, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2502
class UVA11507BenderBRodriguezProblem {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int l = Integer.parseInt(in.readLine());
        while (l != 0) {
            String current = "+x";
            StringTokenizer st = new StringTokenizer(in.readLine());
            for (int i = 1; i < l; i++) {
                String bend = st.nextToken();
                char currentAxis = current.charAt(1);
                if (!bend.equals("No")) {
                    if (currentAxis == 'x') {
                        if (current.charAt(0) == '+') {
                            current = bend;
                        } else {
                            String direction = bend.charAt(0) == '+' ? "-" : "+";
                            current = direction + bend.substring(1, 2);
                        }
                    } else if (currentAxis == bend.charAt(1)) {
                        if (current.charAt(0) == '-') {
                            current = bend.substring(0, 1) + "x";
                        } else {
                            String direction = bend.charAt(0) == '+' ? "-" : "+";
                            current = direction + "x";
                        }
                    }
                }
            }
            out.println(current);
            l = Integer.parseInt(in.readLine());
        }
        out.close();
    }

}
