package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

// 11947 - Time, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3098
class UVA11947CancerOrScorpio {
    // each one associated with 2 months. e.g. signs[0] is last half of january
    // and first half of february, signs[1] the same with february and march, etc.
    private static String[] signs = { "aquarius", "pisces", "aries", "taurus",
            "gemini", "cancer", "leo", "virgo", "libra", "scorpio",
            "sagittarius", "capricorn" };
    // start[i] indicates the start date for signs[i] on month i. Any previous
    // month i date will correspond to the previous sign signs[i-1]
    private static int[] start = { 21, 20, 21, 21, 22, 22, 23, 22, 24, 24, 23, 23 };

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int n = Integer.parseInt(in.readLine());
        for (int i = 1; i <= n; i++) {
            String line = in.readLine();
            int month = Integer.parseInt(line.substring(0, 2));
            int day = Integer.parseInt(line.substring(2, 4));
            int year = Integer.parseInt(line.substring(4));
            GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
            calendar.add(Calendar.DATE, 7 * 40);
            String sign = null;
            day = calendar.get(Calendar.DATE);
            month = calendar.get(Calendar.MONTH);
            if (start[month] <= day) {
                sign = signs[month];
            } else {
                sign = signs[(month + 12 - 1) % 12];
            }
            out.print(i);
            out.print(' ');
            out.print(String.format("%02d/%02d/%04d", calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE), calendar.get(Calendar.YEAR)));
            out.print(' ');
            out.println(sign);
        }
        out.close();
    }

}
