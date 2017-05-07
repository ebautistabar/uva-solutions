package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;

// 11242 - Complete Search, Iterative (Two Nested Loops), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2183
class UVA11242TourDeFrance {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final DecimalFormat df = buildDecimalFormat();

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null && !"0".equals(line.trim())) {
            StringTokenizer st = new StringTokenizer(line);
            int frontSprockets = Integer.parseInt(st.nextToken());
            int rearSprockets = Integer.parseInt(st.nextToken());
            int[] frontTeeth = new int[frontSprockets];
            int[] rearTeeth = new int[rearSprockets];
            st = new StringTokenizer(in.readLine());
            for (int i = 0; i < frontTeeth.length; i++) {
                frontTeeth[i] = Integer.parseInt(st.nextToken());
            }
            st = new StringTokenizer(in.readLine());
            for (int i = 0; i < rearTeeth.length; i++) {
                rearTeeth[i] = Integer.parseInt(st.nextToken());
            }
            // Calculate all the drive ratios
            double[] driveRatios = new double[frontSprockets * rearSprockets];
            int d = 0;
            for (int f = 0; f < frontSprockets; f++) {
                for (int r = 0; r < rearSprockets; r++) {
                    driveRatios[d++] = (double)rearTeeth[r] / (double)frontTeeth[f];
                }
            }
            Arrays.sort(driveRatios);
            // Get the max spread
            double maxSpread = 0.0;
            for (int d1 = 0; d1 < driveRatios.length - 1; d1++) {
                double spread = driveRatios[d1 + 1] / driveRatios[d1];
                maxSpread = Math.max(spread, maxSpread);
            }
            out.println(df.format(maxSpread));
            line = in.readLine();
        }
        out.close();
    }

    private static DecimalFormat buildDecimalFormat() {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat df = (DecimalFormat) nf;
        df.applyPattern("#0.00");
        return df;
    }

}
