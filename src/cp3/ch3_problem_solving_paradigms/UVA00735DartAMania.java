package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

// 735 - Complete Search, Iterative (Three or More Nested Loops, Easier), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=676
class UVA00735DartAMania {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    
    public static void main(String args[]) throws NumberFormatException, IOException {
        int[] validScores = buildValidScores();
        // First build maps from "sum score of 3 darts" to actual combinations and permutations
        // I only need to build the maps once, as the scoring system is always the same, i.e. it
        // doesn't depend on the test case data
        Map<Integer, Set<List<Integer>>> combinations = new HashMap<>();
        Map<Integer, Set<List<Integer>>> permutations = new HashMap<>();
        for (int i = 0; i < validScores.length; i++) {
            for (int j = 0; j < validScores.length; j++) {
                for (int k = 0; k < validScores.length; k++) {
                    int total = validScores[i] + validScores[j] + validScores[k];
                    // Combinations
                    List<Integer> list = Arrays.asList(i, j, k);
                    Collections.sort(list);
                    Set<List<Integer>> set = combinations.get(total);
                    if (set == null) {
                        set = new HashSet<>();
                        combinations.put(total, set);
                    }
                    set.add(list);
                    // Permutations
                    list = Arrays.asList(i, j, k);
                    set = permutations.get(total);
                    if (set == null) {
                        set = new HashSet<>();
                        permutations.put(total, set);
                    }
                    set.add(list);
                }
            }
        }
        // Then, for each target score, just get the size of the combinations
        // and permutations sets calculated above
        int score = Integer.parseInt(in.readLine().trim());
        while (score > 0) {
            Set<List<Integer>> set = combinations.get(score);
            if (set == null) {
                out.printf("THE SCORE OF %d CANNOT BE MADE WITH THREE DARTS.%n", score);
            } else {
                out.printf("NUMBER OF COMBINATIONS THAT SCORES %d IS %d.%n", score, set.size());
                set = permutations.get(score);
                out.printf("NUMBER OF PERMUTATIONS THAT SCORES %d IS %d.%n", score, set.size());
            }
            out.println("**********************************************************************");
            score = Integer.parseInt(in.readLine().trim());
        }
        out.println("END OF OUTPUT");
        out.close();
    }

    public static void slow(String args[]) throws NumberFormatException, IOException {
        int[] validScores = buildValidScores();
        int score = Integer.parseInt(in.readLine().trim());
        while (score > 0) {
            int combinations = 0;
            int permutations = 0;
            for (int i = 0; i < validScores.length; i++) {
                for (int j = 0; j < validScores.length; j++) {
                    if (validScores[i] > validScores[j]) // To get the combinations right, only allow monotonically increasing scores
                        continue;
                    for (int k = 0; k < validScores.length; k++) {
                        if (validScores[j] > validScores[k])
                            continue;
                        if (score - validScores[i] - validScores[j] - validScores[k] == 0) {
                            combinations++;
                            if (validScores[i] == validScores[j]
                                    && validScores[j] == validScores[k]) {
                                permutations++; // 3!/3! - ways to pick 3 divided by ways to pick 3
                            } else if (validScores[i] == validScores[j]
                                    || validScores[j] == validScores[k]
                                    || validScores[i] == validScores[k]) {
                                permutations += 3; // 3!/2! - ways to pick 3 divided by ways to pick 2
                            } else {
                                permutations += 6; // 3! - ways to pick 3
                            }
                        }
                    }
                }
            }
            if (combinations > 0) {
                out.printf("NUMBER OF COMBINATIONS THAT SCORES %d IS %d.%n", score, combinations);
                out.printf("NUMBER OF PERMUTATIONS THAT SCORES %d IS %d.%n", score, permutations);
            } else {
                out.printf("THE SCORE OF %d CANNOT BE MADE WITH THREE DARTS.%n", score);
            }
            out.println("**********************************************************************");
            score = Integer.parseInt(in.readLine().trim());
        }
        out.println("END OF OUTPUT");
        out.close();
    }

    private static int[] buildValidScores() {
        Set<Integer> scores = new TreeSet<>();
        scores.add(0);
        for (int i = 1; i <= 20; i++) {
            scores.add(i);
            scores.add(2 * i);
            scores.add(3 * i);
        }
        scores.add(50);
        int[] uniqueScores = new int[scores.size()];
        int i = 0;
        for (Integer score : scores)
            uniqueScores[i++] = score;
        return uniqueScores;
    }

}
