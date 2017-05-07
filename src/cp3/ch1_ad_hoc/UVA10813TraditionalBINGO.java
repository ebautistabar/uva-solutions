package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 10813 - Game (Others), Harder, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1754
class UVA10813TraditionalBINGO {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int[][] card = new int[5][5];
        int[] numbers = new int[75];
        int t = Integer.parseInt(in.readLine());
        while (t-- > 0) {
            populateCard(card, in);
            readNumbers(numbers, in);
            int n = playBingo(card, numbers);
            out.println("BINGO after " + n + " numbers announced");
        }
        out.close();
    }

    private static int playBingo(int[][] card, int[] numbers) {
        int n = 0;
        boolean bingo = false;
        boolean[][] marked = new boolean[5][5];
        marked[2][2] = true;
        while (!bingo && n < numbers.length) {
            int num = numbers[n++];
            int c = getColumn(num);
            int r = getRow(card, num, c);
            if (r != -1) {
                marked[r][c] = true;
                bingo = isBingo(marked, r, c);
            }
        }
        return n;
    }

    private static boolean isBingo(boolean[][] marked, int r, int c) {
        // check column
        int count = 0;
        for (int i = 0; i < marked.length; i++) {
            if (marked[i][c]) {
                count++;
            }
        }
        if (count == 5) {
            return true;
        }
        // check row
        count = 0;
        for (int i = 0; i < marked[0].length; i++) {
            if (marked[r][i]) {
                count++;
            }
        }
        if (count == 5) {
            return true;
        }
        if (r == c) {
            // check right diagonal
            count = 0;
            for (int i = 0; i < marked.length; i++) {
                if (marked[i][i]) {
                    count++;
                }
            }
            if (count == 5) {
                return true;
            }
        }
        if (r == marked.length - 1 - c) {
            // check left diagonal
            count = 0;
            for (int i = 0; i < marked.length; i++) {
                if (marked[marked.length - 1 - i][i]) {
                    count++;
                }
            }
            if (count == 5) {
                return true;
            }
        }
        return false;
    }

    private static int getRow(int[][] card, int num, int col) {
        for (int r = 0; r < card[0].length; r++) {
            if (card[r][col] == num) {
                return r;
            }
        }
        return -1;
    }

    private static int getColumn(int num) {
        return (num - 1) / 15;
    }

    private static void readNumbers(int[] numbers, BufferedReader in) throws IOException {
        int num = 0;
        while (num < numbers.length) {
            StringTokenizer st = new StringTokenizer(in.readLine());
            while (st.hasMoreTokens()) {
                numbers[num++] = Integer.parseInt(st.nextToken());
            }
        }
    }

    private static void populateCard(int[][] card, BufferedReader in) throws IOException {
        for (int i = 0; i < card.length; i++) {
            String[] line = in.readLine().split("\\s+");
            for (int j = 0; j < line.length; j++) {
                // i == 2, j == 2 is the free central space
                if (i == 2 && j > 1) {
                    card[i][j + 1] = Integer.parseInt(line[j]);
                } else {
                    card[i][j] = Integer.parseInt(line[j]);
                }
            }
        }
    }

}
