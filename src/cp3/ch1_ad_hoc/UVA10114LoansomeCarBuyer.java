package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

// 10114 - Easy, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1055
public class UVA10114LoansomeCarBuyer {

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer tokenizer = new StringTokenizer(in.readLine());
        int months = Integer.parseInt(tokenizer.nextToken());
        while (months > 0) {
            double payment = Double.parseDouble(tokenizer.nextToken());
            double owed = Double.parseDouble(tokenizer.nextToken());
            double worth = owed + payment;
            payment = owed / months;
            int records = Integer.parseInt(tokenizer.nextToken());
            Map<Integer, Double> depreciation = new HashMap<Integer, Double>();
            for (int i = 0; i < records; i++) {
                tokenizer = new StringTokenizer(in.readLine());
                depreciation.put(Integer.parseInt(tokenizer.nextToken()),
                        Double.parseDouble(tokenizer.nextToken()));
            }
            Double decrease = depreciation.get(0);
            worth -= worth * decrease;
            int currentMonth = 0;
            while (owed >= worth) {
                owed -= payment;
                Double newDecrease = depreciation.get(++currentMonth);
                if (newDecrease == null) {
                    newDecrease = decrease;
                }
                worth -= worth * newDecrease;
                decrease = newDecrease;
            }
            out.print(currentMonth);
            out.print(" month");
            out.println(currentMonth == 1 ? "" : "s");
            tokenizer = new StringTokenizer(in.readLine());
            months = Integer.parseInt(tokenizer.nextToken());
        }
        out.close();
    }

}
