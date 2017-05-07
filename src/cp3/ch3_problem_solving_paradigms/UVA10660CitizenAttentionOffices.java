package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 10660 - Complete Search, Iterative (Three or More Nested Loops, Harder), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1601
class UVA10660CitizenAttentionOffices {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        while (tests-- > 0) {
            int nonNullAreas = Integer.parseInt(in.readLine().trim());
            int[][] areas = new int[5][5];
            for (int i = 0; i < nonNullAreas; i++) {
                StringTokenizer st = new StringTokenizer(in.readLine());
                int row = Integer.parseInt(st.nextToken());
                int col = Integer.parseInt(st.nextToken());
                int population = Integer.parseInt(st.nextToken());
                areas[row][col] = population;
            }
            // Iterate all combinations of office positions
            int minDistance = Integer.MAX_VALUE;
            int[] bestAllocation = new int[5];
            for (int a = 0; a < 25; a++) {
                for (int b = a + 1; b < 25; b++) {
                    for (int c = b + 1; c < 25; c++) {
                        for (int d = c + 1; d < 25; d++) {
                            for (int e = d + 1; e < 25; e++) {
                                // For each area, calculate distance to the offices
                                // and pick the minimum
                                int distance = 0;
                                for (int i = 0; i < 5; i++) {
                                    for (int j = 0; j < 5; j++) {
                                        int toA = manhattanDistance(i, j, a / 5, a % 5) * areas[i][j];
                                        int toB = manhattanDistance(i, j, b / 5, b % 5) * areas[i][j];
                                        int toC = manhattanDistance(i, j, c / 5, c % 5) * areas[i][j];
                                        int toD = manhattanDistance(i, j, d / 5, d % 5) * areas[i][j];
                                        int toE = manhattanDistance(i, j, e / 5, e % 5) * areas[i][j];
                                        distance += Math.min(toA, Math.min(toB, Math.min(toC, Math.min(toD, toE))));
                                    }
                                }
                                // If the sum of these distances says this is a
                                // better allocation of offices, keep it
                                if (minDistance > distance) {
                                    minDistance = distance;
                                    bestAllocation[0] = a;
                                    bestAllocation[1] = b;
                                    bestAllocation[2] = c;
                                    bestAllocation[3] = d;
                                    bestAllocation[4] = e;
                                }
                            }
                        }
                    }
                }
            }
            out.printf("%d %d %d %d %d%n", bestAllocation[0],
                    bestAllocation[1], bestAllocation[2], bestAllocation[3],
                    bestAllocation[4]);
        }
        out.close();
    }

    private static int manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

}
