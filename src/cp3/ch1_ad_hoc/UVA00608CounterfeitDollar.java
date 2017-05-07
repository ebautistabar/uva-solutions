package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

// 608 - Interesting Real Life Problems, Harder, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=549
class UVA00608CounterfeitDollar {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(in.readLine());
        while (t-- > 0) {
            Set<Character> even = new HashSet<>();
            Set<Character> up = new HashSet<>();
            Set<Character> down = new HashSet<>();
            for (int i = 0; i < 3; i++) {
                String[] tokens = in.readLine().split("\\s+");
                switch (tokens[2]) {
                case "even":
                    add(even, tokens[0]);
                    add(even, tokens[1]);
                    break;
                case "up":
                    up = intersect(up, tokens[1]);
                    down = intersect(down, tokens[0]);
                    break;
                case "down":
                    down = intersect(down, tokens[1]);
                    up = intersect(up, tokens[0]);
                    break;
                }
            }
            up.removeAll(even);
            if (up.size() == 1) {
                printResult(up, "light");
            } else {
                down.removeAll(even);
                // we are guaranteed to have a solution, so don't bother
                // checking down.size == 1
                printResult(down, "heavy");
            }
        }
    }

    private static void printResult(Set<Character> set, String weight) {
        for (Character c : set) {
            System.out.println(c + " is the counterfeit coin and it is " + weight + ".");
        }
    }

    private static Set<Character> intersect(Set<Character> set, String letters) {
        if (set.size() == 0) {
            for (int i = 0; i < letters.length(); i++) {
                set.add(letters.charAt(i));
            }
            return set;
        } else {
            Set<Character> result = new HashSet<>();
            for (int i = 0; i < letters.length(); i++) {
                if (set.contains(letters.charAt(i))) {
                    result.add(letters.charAt(i));
                }
            }
            return result;
        }
    }

    private static void add(Set<Character> set, String letters) {
        for (int j = 0; j < letters.length(); j++) {
            set.add(letters.charAt(j));
        }
    }

}
