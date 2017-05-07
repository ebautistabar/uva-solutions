package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

// 161 - Interesting Real Life Problems, Easier, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=97
class UVA00161TrafficLights {

    private static final int LIMIT = 5 * 3600;

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String line = in.readLine();
        List<Integer> cycleTimes = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        while (line != null && !line.equals("0 0 0")) {
            StringTokenizer st = new StringTokenizer(line);
            while (st.hasMoreTokens()) {
                int cycleTime = Integer.parseInt(st.nextToken());
                if (cycleTime == 0) {
                    // Time starts counting with all lights starting their green
                    // phase. We search for the next time they are all green.
                    // When do we start the simulation? Instead of starting from
                    // t=1, or when the quickest light goes orange, we start
                    // when the quickest one goes green again (min * 2). That's
                    // the first moment that we could actually reach a solution
                    int seconds = processCycles(cycleTimes, min * 2);
                    out.println(format(seconds));
                    cycleTimes = new ArrayList<>();
                    min = Integer.MAX_VALUE;
                } else {
                    cycleTimes.add(cycleTime);
                    min = Math.min(min, cycleTime);
                }
            }
            line = in.readLine();
        }
        out.close();
    }

    private static String format(int seconds) {
        if (seconds > LIMIT) {
            return "Signals fail to synchronise in 5 hours";
        }
        StringBuilder buffer = new StringBuilder();
        int hours = seconds / 3600;
        seconds %= 3600;
        int minutes = seconds / 60;
        int secs = seconds % 60;
        if (hours < 10) {
            buffer.append('0');
        }
        buffer.append(hours);
        buffer.append(':');
        if (minutes < 10) {
            buffer.append('0');
        }
        buffer.append(minutes);
        buffer.append(':');
        if (secs < 10) {
            buffer.append('0');
        }
        buffer.append(secs);
        return buffer.toString();
    }

    private static int processCycles(List<Integer> cycleTimes, int start) {
        int seconds = start - 1;
        while (++seconds <= LIMIT) {
            int light = -1;
            while (++light < cycleTimes.size()) {
                // Use mod to scale the time back to 1 cycle.
                // If the time is not in green range, we stop checking and skip
                // to next second.
                if ((seconds % (cycleTimes.get(light) * 2)) >= (cycleTimes.get(light) - 5)) {
                    break;
                }
            }
            if (light == cycleTimes.size()) {
                return seconds;
            }
        }
        return seconds;
    }

}
