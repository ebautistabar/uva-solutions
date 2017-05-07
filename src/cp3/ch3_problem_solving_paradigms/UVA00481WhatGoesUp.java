package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 481 - Dynamic Programming, Longest Increasing Subsequence (LIS), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=422
class UVA00481WhatGoesUp {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        List<Integer> smallestEndingOfLISOfLength = new ArrayList<Integer>();
        List<Integer> indices = new ArrayList<Integer>();
        List<Integer> sequence = new ArrayList<Integer>();
        List<Integer> previous = new ArrayList<Integer>();
        int i = 0;
        String line = in.readLine();
        while (line != null) {
            sequence.add(Integer.parseInt(line.trim()));
            int pos = Collections.binarySearch(smallestEndingOfLISOfLength, sequence.get(i));
            if (pos < 0) pos = -(pos + 1); // if not found, pos is (-(insertion point) - 1)
            if (pos >= smallestEndingOfLISOfLength.size()) {
                smallestEndingOfLISOfLength.add(sequence.get(i)); // none are smaller than this
                indices.add(i); // index in 'sequence' of the ending for the current LIS of size pos + 1 (the one just updated)
            } else {
                smallestEndingOfLISOfLength.set(pos, sequence.get(i)); // overwrite the current smallest ending
                indices.set(pos, i); // index in 'sequence' of the ending for the current LIS of size pos + 1 (the one just updated)
            }
            previous.add(pos == 0 ? -1 : indices.get(pos - 1)); // index of the previous item in the subsequence
            line = in.readLine();
            i++;
        }
        out.println(smallestEndingOfLISOfLength.size());
        out.println('-');
        int[] subsequence = new int[smallestEndingOfLISOfLength.size()];
        int current = indices.get(smallestEndingOfLISOfLength.size() - 1); // index of ending for the longest LIS
        for (int j = subsequence.length - 1; j >= 0; j--) {
            subsequence[j] = sequence.get(current);
            current = previous.get(current);
        }
        for (int j = 0; j < subsequence.length; j++) {
            out.println(subsequence[j]);
        }
        out.close();
    }

}
