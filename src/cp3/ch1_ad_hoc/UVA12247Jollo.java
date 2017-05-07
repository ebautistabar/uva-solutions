package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

// 12247 - Game (Card), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3399
class UVA12247Jollo {

    private static final int CARDS_IN_HAND = 3;
    private static final int MINIMUM_WINS = 2;

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        List<List<Integer>> permutations = getPermutations();
        String line = in.readLine();
        while (!line.equals("0 0 0 0 0")) {
            // reads both hands
            StringTokenizer st = new StringTokenizer(line);
            List<Integer> princess = new ArrayList<>();
            for (int i = 0; i < CARDS_IN_HAND; i++) {
                princess.add(Integer.parseInt(st.nextToken()));
            }
            List<Integer> prince = new ArrayList<>();
            for (int i = 0; i < CARDS_IN_HAND - 1; i++) {
                prince.add(Integer.parseInt(st.nextToken()));
            }
            // track the cards already in use
            Set<Integer> used = new HashSet<Integer>();
            used.addAll(prince);
            used.addAll(princess);
            // brute force over all unused cards for the prince's last card
            // from lowest to highest, take the first that wins
            int card = -1;
            for (int i = 1; i <= 52; i++) {
                if (!used.contains(i)) {
                    prince.add(i);
                    if (wins(prince, princess, permutations)) {
                        card = i;
                        break;
                    }
                    prince.remove(CARDS_IN_HAND - 1);
                }
            }
            out.println(card);
            line = in.readLine();
        }
        out.close();
    }

    private static boolean wins(List<Integer> prince, List<Integer> princess, List<List<Integer>> permutations) {
        // play every permutation of the princess against the prince. No need
        // to permute the prince (just draw the possibilities to check why)
        for (int i = 0; i < permutations.size(); i++) {
            int wins = 0;
            for (int k = 0; k < CARDS_IN_HAND; k++) {
                if (princess.get(permutations.get(i).get(k)) < prince.get(k)) {
                    wins++;
                }
            }
            // We want an invincible hand. If it fails even once, it's not valid
            if (wins < MINIMUM_WINS) {
                return false;
            }
        }
        return true;
    }

    private static List<List<Integer>> getPermutations() {
        List<List<Integer>> permutations = new ArrayList<>();
        ArrayList<Integer> sequence = new ArrayList<>(CARDS_IN_HAND);
        for (int i = 0; i < CARDS_IN_HAND; i++) {
            sequence.add(i);
        }
        getPermutations(sequence, 0, permutations);
        return permutations;
    }

    private static void getPermutations(ArrayList<Integer> sequence, int k,
            List<List<Integer>> permutations) {
        if (k == sequence.size() - 1) {
            permutations.add(new ArrayList<Integer>(sequence));
        } else {
            for (int i = k; i < sequence.size(); i++) {
                Collections.swap(sequence, i, k);
                getPermutations(sequence, k + 1, permutations);
                Collections.swap(sequence, k, i);
            }
        }
    }

}
