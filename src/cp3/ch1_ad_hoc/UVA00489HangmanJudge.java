package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// 489 - Game (Others), Easier, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=430
class UVA00489HangmanJudge {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int round = Integer.parseInt(in.readLine());
        while (round != -1) {
            // Map from letter to number of appearances in word
            Map<Character, Integer> chars = new HashMap<>();
            String word = in.readLine();
            for (int i = 0; i < word.length(); i++) {
                Integer count = chars.get(word.charAt(i));
                if (count == null) {
                    count = 0;
                }
                chars.put(word.charAt(i), count + 1);
            }
            // Process guesses
            String guesses = in.readLine();
            Set<Character> attemptedGuesses = new HashSet<>();
            int failures = 0, successes = 0;
            for (int i = 0; i < guesses.length(); i++) {
                char c = guesses.charAt(i);
                if (!attemptedGuesses.contains(c)) {
                    attemptedGuesses.add(c);
                    Integer count = chars.get(c);
                    if (count == null) {
                        if (++failures == 7) {
                            break;
                        }
                    } else {
                        successes += count;
                        if (successes == word.length()) {
                            break;
                        }
                    }
                }
            }
            // Output
            out.println("Round " + round);
            if (failures == 7) {
                out.println("You lose.");
            } else if (successes == word.length()) {
                out.println("You win.");
            } else {
                out.println("You chickened out.");
            }
            round = Integer.parseInt(in.readLine());
        }
        out.close();
    }

}
