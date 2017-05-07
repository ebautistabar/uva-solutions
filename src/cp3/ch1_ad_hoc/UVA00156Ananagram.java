package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

// 156 - Anagrams, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=92
class UVA00156Ananagram {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String line = in.readLine();
        Map<String, Integer> counts = new HashMap<>();
        Map<String, String> originals = new HashMap<>();
        while (!line.equals("#")) {
            StringTokenizer st = new StringTokenizer(line);
            while (st.hasMoreTokens()) {
                String original = st.nextToken();
                char[] word = original.toLowerCase().toCharArray();
                Arrays.sort(word);
                String wordSt = new String(word);
                originals.put(wordSt, original);
                Integer count = counts.get(wordSt);
                if (count == null) {
                    count = 0;
                }
                counts.put(wordSt, count + 1);
            }
            line = in.readLine();
        }
        List<String> results = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() == 1) {
                results.add(originals.get(entry.getKey()));
            }
        }
        Collections.sort(results);
        for (String result : results) {
            out.println(result);
        }
        out.close();
    }

}
