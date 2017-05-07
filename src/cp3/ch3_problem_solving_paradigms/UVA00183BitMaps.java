package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// 183 - Other Divide and Conquer Problems, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=119
class UVA00183BitMaps {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null && !"#".equals(line.trim())) {
            if (!"".equals(line.trim())) {
                StringTokenizer st = new StringTokenizer(line);
                String type = st.nextToken();
                int rows = Integer.parseInt(st.nextToken());
                int cols = Integer.parseInt(st.nextToken());
                if ("D".equals(type)) {
                    convertToExtended(rows, cols);
                } else if ("B".equals(type)) {
                    convertToCompact(rows, cols);
                }
            }
            line = in.readLine();
        }
        out.close();
    }

    private static void convertToExtended(int rows, int cols) throws IOException {
        int[][] bitmap = new int[rows][cols];
        convertToExtended(bitmap, 0, rows, 0, cols);
        print(bitmap);
    }

    private static void convertToExtended(int[][] bitmap, int startR, int endR, int startC, int endC) throws IOException {
        if (startR == endR || startC == endC) {
            return;
        }
        char c = getNextChar();
        if (c == 'D') {
            int newStartR = getNewStart(startR, endR);
            int newStartC = getNewStart(startC, endC);
            convertToExtended(bitmap, startR, newStartR, startC, newStartC);
            convertToExtended(bitmap, startR, newStartR, newStartC, endC);
            convertToExtended(bitmap, newStartR, endR, startC, newStartC);
            convertToExtended(bitmap, newStartR, endR, newStartC, endC);
        } else {
            fill(bitmap, startR, endR, startC, endC, c - '0');
        }
    }

    private static int getNewStart(int start, int end) {
        int len = end - start;
        return start + len / 2 + (len & 1);
    }

    private static void fill(int[][] bitmap, int startR, int endR, int startC, int endC, int value) {
        for (int i = startR; i < endR; i++) {
            for (int j = startC; j < endC; j++) {
                bitmap[i][j] = value;
            }
        }
    }

    private static void print(int[][] bitmap) {
        int rows = bitmap.length;
        int cols = bitmap[0].length;
        out.printf("B%4d%4d%n", rows, cols);
        int counter = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                out.print(bitmap[i][j]);
                if (++counter == 50) {
                    counter = 0;
                    out.println();
                }
            }
        }
        if (counter != 0) {
            out.println();
        }
    }

    private static void convertToCompact(int rows, int cols) throws IOException {
        int[][] bitmap = readBitmap(rows, cols);
        String result = convertToCompact(bitmap, 0, rows, 0, cols);
        print(rows, cols, reverse(result));
    }

    private static int[][] readBitmap(int rows, int cols) throws IOException {
        int[][] bitmap = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                bitmap[i][j] = getNextChar() - '0';
            }
        }
        return bitmap;
    }

    private static String convertToCompact(int[][] bitmap, int startR, int endR, int startC, int endC) {
        if (startR == endR || startC == endC) {
            return "";
        }
        if (endR - startR == 1 && endC - startC == 1) {
            return String.valueOf((char)(bitmap[startR][startC] + '0'));
        } else {
            int newStartR = getNewStart(startR, endR);
            int newStartC = getNewStart(startC, endC);
            // bottom to top, right to left
            String a = convertToCompact(bitmap, newStartR, endR, newStartC, endC);
            String b = convertToCompact(bitmap, newStartR, endR, startC, newStartC);
            String c = convertToCompact(bitmap, startR, newStartR, newStartC, endC);
            String d = convertToCompact(bitmap, startR, newStartR, startC, newStartC);
            String compacted = compact(a, b, c, d);
            if (compacted.isEmpty()) {
                return a + b + c + d + "D";
            } else {
                return compacted;
            }
        }
    }

    public static String compact(String a, String b, String c, String d) {
        String result = "";
        if (!a.isEmpty()) {
            if ('D' == a.charAt(a.length() - 1)) {
                return "";
            }
            result = a;
        }
        if (!b.isEmpty()) {
            if ('D' == b.charAt(b.length() - 1) || !(result.isEmpty() || result.equals(b))) {
                return "";
            }
            result = b;
        }
        if (!c.isEmpty()) {
            if ('D' == c.charAt(c.length() - 1) || !(result.isEmpty() || result.equals(c))) {
                return "";
            }
            result = c;
        }
        if (!d.isEmpty()) {
            if ('D' == d.charAt(d.length() - 1) || !(result.isEmpty() || result.equals(d))) {
                return "";
            }
            result = d;
        }
        return result;
    }

    private static char getNextChar() throws IOException {
        char c = (char)in.read();
        while (c != 'D' && c != '0' && c != '1') {
            c = (char)in.read();
        }
        return c;
    }

    private static void print(int rows, int cols, String result) {
        out.printf("D%4d%4d%n", rows, cols);
        int counter = 0;
        for (int i = 0; i < result.length(); i++) {
            out.print(result.charAt(i));
            if (++counter == 50) {
                counter = 0;
                out.println();
            }
        }
        if (counter != 0) {
            out.println();
        }
    }

    private static String reverse(String original) {
        return new StringBuilder(original).reverse().toString();
    }
}
