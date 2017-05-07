package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 454 - Anagrams, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=395
class UVA00454Anagrams {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int t = Integer.parseInt(in.readLine());
        in.readLine();
        while (t-- > 0) {
            List<String> phrases = new ArrayList<>();
            String line = in.readLine();
            while (line != null && !line.equals("")) {
                phrases.add(line);
                line = in.readLine();
            }
            // sort in lexicographical order
            Collections.sort(phrases);
            List<String> anagrams = new ArrayList<>();
            // check each pair without repetition, with the first item always
            // being smaller in the lexicographical order
            for (int i = 0; i < phrases.size(); i++) {
                for (int j = i + 1; j < phrases.size(); j++) {
                    if (isAnagram(clean(phrases.get(i)), clean(phrases.get(j)))) {
                        anagrams.add(phrases.get(i) + " = " + phrases.get(j));
                    }
                }
            }
            for (String anagram : anagrams) {
                out.println(anagram);
            }
            if (t > 0) out.println();
        }
        out.close();
    }

    private static String clean(String line) {
        if (line == null) return "";
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) != ' ') {
                buffer.append(line.charAt(i));
            }
        }
        return buffer.toString();
    }

    private static boolean isAnagram(String phrase1, String phrase2) {
        if (phrase1.length() != phrase2.length()) {
            return false;
        }
        Map<Character, Integer> counts = new HashMap<Character, Integer>();
        for (int i = 0; i < phrase1.length(); i++) {
            char c = phrase1.charAt(i);
            Integer count = counts.get(c);
            if (count == null) {
                count = 0;
            }
            counts.put(c, count + 1);
        }
        for (int j = 0; j < phrase2.length(); j++) {
            char c = phrase2.charAt(j);
            Integer count = counts.get(c);
            if (count == null || count == 0) {
                return false;
            }
            counts.put(c, count - 1);
        }
        return true;
    }

}
