package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

// 462 - Game (Card), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=403
class UVA00462BridgeHandEvaluator {

    private final static int CARDS_IN_HAND = 13;
    private final static int SUITS = 4;

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        Map<Character, Integer> suitIdx = getSuitIndices();
        Map<Character, Integer> rankPoints = getRankPoints();
        char[] suitLetters = getSuitLetters();
        String line = null;
        while ((line = in.readLine()) != null) {
            int points = 0;
            int[] cardsInSuit = new int[SUITS];
            boolean[] aceInSuit = new boolean[SUITS];
            boolean[] kingInSuit = new boolean[SUITS];
            boolean[] queenInSuit = new boolean[SUITS];
            boolean[] jackInSuit = new boolean[SUITS];
            StringTokenizer st = new StringTokenizer(line);
            for (int i = 0; i < CARDS_IN_HAND; i++) {
                String card = st.nextToken();
                char rank = card.charAt(0);
                int suit = suitIdx.get(card.charAt(1));
                cardsInSuit[suit]++;
                if (rank == 'A') aceInSuit[suit] = true;
                else if (rank == 'K') kingInSuit[suit] = true;
                else if (rank == 'Q') queenInSuit[suit] = true;
                else if (rank == 'J') jackInSuit[suit] = true;
                // Rule 1
                points += rankPoints.get(rank);
            }
            // Rules 2, 3, 4
            for (int i = 0; i < SUITS; i++) {
                if (kingInSuit[i] && cardsInSuit[i] == 1) {
                    points -= 1;
                }
                if (queenInSuit[i] && cardsInSuit[i] < 3) {
                    points -= 1;
                }
                if (jackInSuit[i] && cardsInSuit[i] < 4) {
                    points -= 1;
                }
            }
            // Rules 5, 6, 7
            int pointsWithoutLastRules = points;
            int suitWithMoreCards = 0;
            for (int i = 0; i < SUITS; i++) {
                int count = cardsInSuit[i];
                if (count < 2) {
                    points += 2;
                } else if (count == 2) {
                    points += 1;
                }
                if (count > cardsInSuit[suitWithMoreCards]) {
                    suitWithMoreCards = i;
                }
            }
            // Stopped suits
            int stopped = 0;
            for (int i = 0; i < SUITS; i++) {
                if (aceInSuit[i] || (kingInSuit[i] && cardsInSuit[i] > 1) || (queenInSuit[i] && cardsInSuit[i] > 2)){
                    stopped++;
                }
            }
            // Assessment
            if (points < 14) {
                out.println("PASS");
            } else if (stopped == 4 && pointsWithoutLastRules > 15) {
                out.println("BID NO-TRUMP");
            } else {
                out.println("BID " + suitLetters[suitWithMoreCards]);
            }
        }
        out.close();
    }

    private static char[] getSuitLetters() {
        char[] letters = new char[SUITS];
        letters[0] = 'S';
        letters[1] = 'H';
        letters[2] = 'D';
        letters[3] = 'C';
        return letters;
    }

    private static Map<Character, Integer> getSuitIndices() {
        Map<Character, Integer> multipliers = new HashMap<>();
        multipliers.put('S', 0);
        multipliers.put('H', 1);
        multipliers.put('D', 2);
        multipliers.put('C', 3);
        return multipliers;
    }

    private static Map<Character, Integer> getRankPoints() {
        Map<Character, Integer> offsets = new HashMap<>();
        for (int i = 2; i < 10; i++) {
            offsets.put((char)('0' + i), 0);
        }
        offsets.put('T', 0);
        offsets.put('J', 1);
        offsets.put('Q', 2);
        offsets.put('K', 3);
        offsets.put('A', 4);
        return offsets;
    }

}
