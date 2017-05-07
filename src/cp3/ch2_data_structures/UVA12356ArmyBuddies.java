package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 12356 - 1D Array Manipulation, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3778
class UVA12356ArmyBuddies {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int[] left = new int[100_000 + 1]; // max soldiers is 100_000
        int[] right = new int[100_000 + 1]; // +1 so we can use 1-based indices
        String line = in.readLine();
        while (line != null && !line.equals("0 0")) {
            StringTokenizer st = new StringTokenizer(line);
            int soldiers = Integer.parseInt(st.nextToken());
            int losses = Integer.parseInt(st.nextToken());
            for (int i = 1; i <= soldiers; i++) {
                left[i] = i - 1;
                right[i] = i + 1;
            }
            right[soldiers] = 0; // 0 represents 'no buddy'
            for (int i = 0; i < losses; i++) {
                st = new StringTokenizer(in.readLine());
                int l = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());
                // Print buddies at left and right of current gap in the line
                if (left[l] == 0) out.print('*');
                else out.print(left[l]);
                out.print(' ');
                if (right[r] == 0) out.println('*');
                else out.println(right[r]);
                // Update buddies of soldiers standing at the limits of the gap
                left[right[r]] = left[l];
                right[left[l]] = right[r];
            }
            out.println('-');
            line = in.readLine();
        }
        out.close();
    }

}
