package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

// 579 - Time, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=520
class UVA00579ClockHands {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String line = in.readLine();
        while (line != null && !line.equals("0:00")) {
            StringTokenizer st = new StringTokenizer(line);
            int hours = Integer.parseInt(st.nextToken(":"));
            int minutes = Integer.parseInt(st.nextToken());
            double hourAngle = ((hours % 12) + (minutes / 60.0)) / 12.0;
            double minuteAngle = minutes / 60.0;
            double angle = Math.abs(hourAngle - minuteAngle) * 360;
            if (angle > 180.0) {
                angle = 360 - angle;
            }
            out.println(String.format(Locale.US, "%.3f", angle));
            line = in.readLine();
        }
        out.close();
    }

}
