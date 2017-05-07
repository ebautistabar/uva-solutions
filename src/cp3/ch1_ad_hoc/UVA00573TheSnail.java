package cp3.ch1_ad_hoc;
import java.io.*;
import java.util.*;

// 573 - Medium, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=514
class UVA00573TheSnail {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(in.readLine());
        int goal = Integer.parseInt(st.nextToken());
        while (goal != 0) {
            int up = Integer.parseInt(st.nextToken());
            int down = Integer.parseInt(st.nextToken());
            int fatigue = Integer.parseInt(st.nextToken());
            int days = 0;
            double decrease = up * fatigue / 100.0;
            double height = 0;
            double distance = up;
            while (height >= 0 && height <= goal) {
                days++;
                height += distance;
                if (height > goal) break;
                height -= down;
                distance = Math.max(0.0, distance - decrease);
            }
            if (height < 0) {
                out.print("failure on day ");
            } else {
                out.print("success on day ");
            }
            out.println(days);
            st = new StringTokenizer(in.readLine());
            goal = Integer.parseInt(st.nextToken());
        }
        out.close();
    }

}
