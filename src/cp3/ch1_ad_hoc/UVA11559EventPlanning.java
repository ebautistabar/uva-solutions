package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 11559 - Easy, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2595
public class UVA11559EventPlanning {

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String line = null;
        while ((line = in.readLine()) != null) {
            int cheapest = Integer.MAX_VALUE;
            StringTokenizer st = new StringTokenizer(line);
            int participants = Integer.parseInt(st.nextToken());
            int budget = Integer.parseInt(st.nextToken());
            int hotels = Integer.parseInt(st.nextToken());
            int weeks = Integer.parseInt(st.nextToken());
            for (int i = 0; i < hotels; i++) {
                int roomPrice = Integer.parseInt(in.readLine());
                st = new StringTokenizer(in.readLine());
                for (int w = 0; w < weeks; w++) {
                    int rooms = Integer.parseInt(st.nextToken());
                    if (rooms >= participants) {
                        int price = participants * roomPrice;
                        if (cheapest > price && budget >= price) {
                            cheapest = price;
                        }
                    }
                }
            }
            if (cheapest == Integer.MAX_VALUE) {
                out.println("stay home");
            } else {
                out.println(cheapest);
            }
        }
        out.close();
    }

}
