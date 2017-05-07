package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 11459 - Game (Others), Easier, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2454
class UVA11459SnakesAndLadders {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int t = Integer.parseInt(in.readLine());
        for (int i = 0; i < t; i++) {
            StringTokenizer st = new StringTokenizer(in.readLine());
            int players = Integer.parseInt(st.nextToken());
            int jumps = Integer.parseInt(st.nextToken());
            int rolls = Integer.parseInt(st.nextToken());
            int[] tokens = new int[players];
            int[] jumpPositions = new int[100];
            for (int j = 0; j < jumps; j++) {
                st = new StringTokenizer(in.readLine());
                jumpPositions[Integer.parseInt(st.nextToken()) - 1] = Integer.parseInt(st.nextToken());
            }
            boolean goal = false;
            int player = 0;
            while (rolls > 0) {
                int roll = Integer.parseInt(in.readLine());
                if (!goal) {
                    tokens[player] += roll;
                    if (tokens[player] >= 99) {
                        tokens[player] = 99;
                    }
                    if (jumpPositions[tokens[player]] != 0) {
                        tokens[player] = jumpPositions[tokens[player]] - 1;
                    }
                    if (tokens[player] >= 99) {
                        tokens[player] = 99;
                        goal = true;
                    }
                    player = (player + 1) % players;
                }
                rolls--;
            }
            for (int j = 1; j <= players; j++) {
                out.println("Position of player " + j + " is " + (tokens[j - 1] + 1) + ".");
            }
        }
        out.close();
    }

}
