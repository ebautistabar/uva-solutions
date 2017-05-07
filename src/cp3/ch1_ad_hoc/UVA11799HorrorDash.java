package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 11799 - Easy, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2899
public class UVA11799HorrorDash {

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int t = Integer.parseInt(in.readLine());
        for (int i = 0; i < t; i++) {
            int speed = Integer.MIN_VALUE;
            StringTokenizer st = new StringTokenizer(in.readLine());
            int creatures = Integer.parseInt(st.nextToken());
            for (int j = 0; j < creatures; j++) {
                speed = Math.max(speed, Integer.parseInt(st.nextToken()));
            }
            out.print("Case ");
            out.print(i + 1);
            out.print(": ");
            out.println(speed);
        }
        out.close();
    }

}
