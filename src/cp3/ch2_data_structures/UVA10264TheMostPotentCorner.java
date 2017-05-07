package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// 10264 - Bit Manipulation, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1205
class UVA10264TheMostPotentCorner {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String line = in.readLine();
        int[] weights = new int[1 << 14];
        int[] potencies = new int[1 << 14];
        while (line != null) {
            int dimensions = Integer.parseInt(line);
            int corners = 1 << dimensions;
            for (int i = 0; i < corners; i++) {
                weights[i] = Integer.parseInt(in.readLine());
            }
            // potencies of every corner
            for (int corner = 0; corner < corners; corner++) {
                potencies[corner] = 0;
                // to get to the neighbors, we must flip 1 bit at a time
                for (int dimension = 0; dimension < dimensions; dimension++) {
                    potencies[corner] += weights[corner ^ (1 << dimension)];
                }
            }
            // greatest sum of 2 potencies
            int max = -1;
            for (int corner = 0; corner < corners; corner++) {
                for (int dimension = 0; dimension < dimensions; dimension++) {
                    max = Math.max(max, potencies[corner] + potencies[corner ^ (1 << dimension)]);
                }
            }
            out.println(max);
            line = in.readLine();
        }
        out.close();
    }

}
