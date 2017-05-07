package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// 584 - Game (Others), Harder, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=525
class UVA00584Bowling {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String line = in.readLine();
        while (line != null && !line.equals("Game Over")) {
            String[] scores = line.trim().split(" ");
            int total = 0;
            int i = 0;
            int rolls = 1;
            while (rolls <= 20) {
                if (scores[i].equals("X")) {
                    rolls += 2;
                    total += 10;
                    if (i + 1 < scores.length) {
                        if (scores[i + 1].equals("X")) {
                            total += 10;
                        } else {
                            // can't be a spare, as spares only appear in the
                            // second roll in a frame, and we just had a strike
                            total += Integer.parseInt(scores[i + 1]);
                        }
                    }
                    if (i + 2 < scores.length) {
                        if (scores[i + 2].equals("X")) {
                            total += 10;
                        } else if (scores[i + 2].equals("/")) {
                            total += 10 - Integer.parseInt(scores[i + 1]);
                        } else {
                            total += Integer.parseInt(scores[i + 2]);
                        }
                    }
                } else if (scores[i].equals("/")) {
                    rolls++;
                    // we delete the previous roll so as not to count it twice
                    // it's safe to use i-1, because a spare cannot happen on
                    // the first roll of the game
                    total += 10 - Integer.parseInt(scores[i - 1]);
                    if (i + 1 < scores.length) {
                        if (scores[i + 1].equals("X")) {
                            total += 10;
                        } else {
                            // can't be a spare, as spares only appear in the
                            // second roll in a frame, and we just had a spare
                            total += Integer.parseInt(scores[i + 1]);
                        }
                    }
                } else {
                    rolls++;
                    total += Integer.parseInt(scores[i]);
                }
                i++;
            }
            out.println(total);
            line = in.readLine();
        }
        out.close();
    }

}
