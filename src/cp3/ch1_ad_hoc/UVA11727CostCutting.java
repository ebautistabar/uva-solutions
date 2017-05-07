package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

// 11727 - Super Easy, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2827
public class UVA11727CostCutting {

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int k = Integer.parseInt(in.readLine());
        for (int i = 0; i < k; i++) {
            StringTokenizer tokenizer = new StringTokenizer(in.readLine());
            ArrayList<Integer> salaries = new ArrayList<>(3);
            salaries.add(Integer.parseInt(tokenizer.nextToken()));
            salaries.add(Integer.parseInt(tokenizer.nextToken()));
            salaries.add(Integer.parseInt(tokenizer.nextToken()));
            Collections.sort(salaries);
            out.print("Case ");
            out.print(i + 1);
            out.print(": ");
            out.println(salaries.get(1));
        }
        out.close();
    }

}
