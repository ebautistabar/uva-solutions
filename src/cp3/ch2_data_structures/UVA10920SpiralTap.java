package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// 10920 - 2D Array Manipulation, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1861
class UVA10920SpiralTap {

    private final static int[] movement = {1, 0, 0, -1, -1, 0, 0, 1};

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String line = in.readLine();
        while (line != null && !line.equals("0 0")) {
            String[] nums = line.split("\\s+");
            int s = Integer.parseInt(nums[0]);
            long p = Long.parseLong(nums[1]);
            // In each concentric square, the top right cell is 1, 9, 25...
            // It's i squared, for odd values of i. If we know p, we know
            // the concentric ring that contains it (more precisely its top
            // right square)
            double sqrtp = Math.sqrt(p);
            int topsqrt = (int) Math.ceil(sqrtp);
            if (topsqrt % 2 == 0) {
                topsqrt++;
            }
            long topright = (long) topsqrt * (long) topsqrt;
            // We get the coordinates of the top right square
            int row = topsqrt + (s - topsqrt) / 2;
            int col = row;
            // From the diff between the target and the top right square, we
            // can get the coordinates of p
            int diff = (int) (topright - p);
            if (diff < topsqrt) {
                row -= diff;
            } else if (diff < topsqrt * 2 - 2) {
                row -= topsqrt - 1;
                // Last term is offset from the right in case topsqrt != s
                col = topsqrt - (diff - topsqrt + 2) + 1 + (s - topsqrt) / 2;
            } else if (diff < topsqrt * 3 - 2) {
                // Last term is offset from the right in case topsqrt != s
                row = diff - topsqrt * 2 + 3 + (s - topsqrt) / 2;
                col -= topsqrt - 1;
            } else {
                // First 2 terms would be correct if topsqrt == s. We add the
                // third term to offset from the right in case they're different
                col = topsqrt - (int)(p - (topsqrt - 2) * (topsqrt - 2)) + (s - topsqrt) / 2;
            }
            out.print("Line = ");
            out.print(row);
            out.print(", column = ");
            out.print(col);
            out.println('.');
            line = in.readLine();
        }
        out.close();
    }

    @SuppressWarnings("unused")
    private static void slow(BufferedReader in, PrintWriter out)
            throws IOException {
        String line = in.readLine();
        while (line != null && !line.equals("0 0")) {
            String[] nums = line.split("\\s+");
            int s = Integer.parseInt(nums[0]);
            long p = Long.parseLong(nums[1]) - 1;
            int row = s / 2 + 1;
            int col = row;
            long current = 0;
            int len = 1;
            int sides = 0;
            while (current < p) {
                row += len * movement[(2 * sides) % movement.length];
                col += len * movement[(2 * sides + 1) % movement.length];
                current += len;
                sides++;
                if (sides % 2 == 0) {
                    len++;
                }
            }
            // As we traverse one complete side at a time, we may go overboard.
            // Thus, we have to go back until we reach exactly p
            if (current > p) {
                sides--;
                row -= (current - p) * movement[(2 * sides) % movement.length];
                col -= (current - p) * movement[(2 * sides + 1) % movement.length];
            }
            out.print("Line = ");
            out.print(row);
            out.print(", column = ");
            out.print(col);
            out.println('.');
            line = in.readLine();
        }
    }

}
