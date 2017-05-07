package cp3.ch1_ad_hoc;
import java.io.*;
import java.util.*;

// 10141 - Medium, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1082
class UVA10141RequestForProposal {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String line = in.readLine();
        int p = 1;
        while (!line.equals("0 0")) {
            if (p > 1) {
                out.println();
            }
            StringTokenizer st = new StringTokenizer(line);
            int requirements = Integer.parseInt(st.nextToken());
            int proposals = Integer.parseInt(st.nextToken());
            for (int i = 0; i < requirements; i++) {
                in.readLine(); // don't need the names
            }
            int bestMet = -1;
            double bestPrice = -1.0;
            String bestName = null;
            for (int i = 0; i < proposals; i++) {
                String name = in.readLine();
                st = new StringTokenizer(in.readLine());
                double price = Double.parseDouble(st.nextToken());
                int requirementsMet = Integer.parseInt(st.nextToken());
                if (bestMet < requirementsMet || (bestMet == requirementsMet && Double.compare(bestPrice, price) > 0)) {
                    bestMet = requirementsMet;
                    bestPrice = price;
                    bestName = name;
                }
                for (int j = 0; j < requirementsMet; j++) {
                    in.readLine(); // don't need the names
                }
            }
            out.println("RFP #" + p++);
            out.println(bestName);
            line = in.readLine();
        }
        out.close();
    }

}
