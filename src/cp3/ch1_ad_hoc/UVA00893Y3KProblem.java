package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.StringTokenizer;

// 893 - Time, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=834
class UVA00893Y3KProblem {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String line = in.readLine();
        while (!line.equals("0 0 0 0")) {
            StringTokenizer st = new StringTokenizer(line);
            int offset = Integer.parseInt(st.nextToken());
            int day = Integer.parseInt(st.nextToken());
            int month = Integer.parseInt(st.nextToken()) - 1;
            int year = Integer.parseInt(st.nextToken());
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(year, month, day);
            calendar.add(Calendar.DATE, offset);
            out.println(calendar.get(Calendar.DATE) + " " + (calendar.get(Calendar.MONTH) + 1) + " " + calendar.get(Calendar.YEAR));
            line = in.readLine();
        }
        out.close();
    }

}
