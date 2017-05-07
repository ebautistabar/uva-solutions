package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

// 429 - Single-Source Shortest Paths (SSSP) On Unweighted Graph: BFS, Easier, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=370
// Direct application of BFS for SSSP. First build graph of words. Words are
//   connected if they are the same length and differ in exactly 1 character
class UVA00429WordTransformation {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws IOException {
        int tests = Integer.parseInt(getLine().trim());
        while (tests-- > 0) {
            List<String> dictionary = new ArrayList<>();
            Map<String, Integer> wordToIdx = new HashMap<>();
            String line = getLine().trim();
            while (!"*".equals(line)) {
                dictionary.add(line);
                wordToIdx.put(line, dictionary.size() - 1);
                line = getLine();
            }

            List<List<Integer>> adjList = buildGraph(dictionary, wordToIdx);

            int[] distance = new int[dictionary.size()];
            line = in.readLine();
            while (line != null && !"".equals(line.trim())) {
                Arrays.fill(distance, Integer.MAX_VALUE);
                StringTokenizer st = new StringTokenizer(line);
                String startWord = st.nextToken();
                String goalWord = st.nextToken();
                int start = wordToIdx.get(startWord);
                int goal = wordToIdx.get(goalWord);
                distance[start] = 0;
                Queue<Integer> q = new ArrayDeque<>();
                q.add(start);
                while (!q.isEmpty() && distance[goal] == Integer.MAX_VALUE) {
                    int current = q.remove();
                    for (int next : adjList.get(current)) {
                        if (next == goal) {
                            distance[next] = distance[current] + 1;
                            break;
                        }
                        if (distance[next] == Integer.MAX_VALUE) {
                            distance[next] = distance[current] + 1;
                            q.add(next);
                        }
                    }
                }
                
                out.printf("%s %s %d%n", startWord, goalWord, distance[goal]);
                line = in.readLine();
            }
            if (tests > 0) {
                out.println();
            }
        }
        out.close();
    }

    private static List<List<Integer>> buildGraph(List<String> dictionary, Map<String, Integer> wordToIdx) {
        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < dictionary.size(); i++) {
            adjList.add(i, new ArrayList<Integer>());
        }
        for (int i = 0; i < dictionary.size(); i++) {
            for (int j = i + 1; j < dictionary.size(); j++) {
                if (areAdjacent(dictionary.get(i), dictionary.get(j))) {
                    adjList.get(i).add(j);
                    adjList.get(j).add(i);
                }
            }
        }
        return adjList;
    }

    // strings are adjacent only if they have the same length and exactly
    //   1 difference
    private static boolean areAdjacent(String a, String b) {
        if (a.length() != b.length()) return false;
        boolean oneDiff = false;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                if (oneDiff) return false;
                oneDiff = true;
            }
        }
        return oneDiff;
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }

}
