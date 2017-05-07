package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// 10646 - Game (Card), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1587
class UVA10646WhatIsTheCard {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int t = Integer.parseInt(in.readLine());
        for (int i = 1; i <= t; i++) {
            String[] cards = in.readLine().split(" ");
            int top = 52 - 26; // top of the pile is the end of the array, then we take 25 cards
            int y = -1; // to account for 0-based indexing
            for (int j = 0; j < 3; j++) {
                char value = cards[top--].charAt(0);
                int x = value > '9' ? 10 : value - '2';
                y += x;
                top -= 10 - x;
            }
            String target = null;
            if (y <= top) {
                target = cards[y];
            } else {
                // we jump all the discarded cards and continue with the top 25 that were in our hand
                target = cards[52 - 25 + y - top - 1];
            }
            out.println("Case " + i + ": " + target);
        }
        out.close();
    }

}
