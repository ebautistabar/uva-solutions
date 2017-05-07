package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// 403 - Interesting Real Life Problems, Harder, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=344
class UVA00403Postscript {

    private static final int LARGE_FONT_WIDTH = 6;
    private static final int PAGE_LENGTH = 60;
    private static final int PAGE_WIDTH = 60;
    
    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        char[][] page = new char[PAGE_LENGTH + 1][PAGE_WIDTH + 1];
        String line = in.readLine();
        while (line != null && !line.equals("")) {
            if (line.equals(".EOP")) {
                printPage(out, page);
                page = new char[PAGE_LENGTH + 1][PAGE_WIDTH + 1];
            } else {
                String[] tokens = line.split("\\|");
                String text = tokens[1];
                tokens = tokens[0].split(" ");
                char command = tokens[0].charAt(1);
                int font = tokens[1].charAt(1) - '0';
                int row = Integer.parseInt(tokens[2]);
                switch (command) {
                case 'P':
                    int col = Integer.parseInt(tokens[3]);
                    write(page, font, row, col, text);
                    break;
                case 'L':
                    write(page, font, row, 1, text);
                    break;
                case 'R':
                    write(page, font, row, calculateRightJustifiedStart(text, font), text);
                    break;
                case 'C':
                    write(page, font, row, calculateCenteredStart(text, font), text);
                    break;
                }
            }
            line = in.readLine();
        }
        out.close();
    }

    private static int calculateRightJustifiedStart(String text, int font) {
        int len = text.length();
        font = font == 1 ? font : LARGE_FONT_WIDTH;
        return PAGE_WIDTH - (len * font) + 1;
    }

    private static void write(char[][] page, int font, int row, int col,
            String text) {
        for (int i = 0; i < text.length(); i++) {
            writeLetter(page, font, row, col, text.charAt(i));
            col += (font == 1 ? 1 : LARGE_FONT_WIDTH);
        }
    }

    private static void writeLetter(char[][] page, int font, int row, int col, char ch) {
        if (font == 1) {
            if (ch != ' ' && row >= 1 && row <= PAGE_LENGTH && col >= 1 && col <= PAGE_WIDTH) {
                page[row][col] = ch;
            }
        } else {
            writeBigLetter(page, row, col, ch);
        }
    }

    private static void writeBigLetter(char[][] page, int row, int col, char ch) {
        if (ch != ' ') {
            int letter = ch - 'A';
            for (int r = 0; r < letters.length; r++) {
                if (row + r >= 1 && row + r <= PAGE_LENGTH) {
                    for (int c = 0; c < LARGE_FONT_WIDTH; c++) {
                        if (col + c >= 1 && col + c <= PAGE_WIDTH) {
                            char pixel = letters[r].charAt(letter * LARGE_FONT_WIDTH + c);
                            if (pixel != '.') {
                                page[row + r][col + c] = letters[r].charAt(letter * LARGE_FONT_WIDTH + c);
                            }
                        }
                    }
                }
            }
        }
    }

    private static int calculateCenteredStart(String text, int font) {
        int len = text.length();
        font = font == 1 ? font : LARGE_FONT_WIDTH;
        return (PAGE_WIDTH / 2) - (len * font / 2) + 1;
    }

    private static void printPage(PrintWriter out, char[][] page) {
        for (int i = 1; i < page.length; i++) {
            for (int j = 1; j < page[0].length; j++) {
                if (page[i][j] == 0) {
                    System.out.print('.');
                } else {
                    System.out.print(page[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("------------------------------------------------------------");
        System.out.println();
    }

    private static final String[] letters = {
        ".***..****...****.****..*****.*****..****.*...*.*****...***.*...*.*.....*...*.*...*..***..****...***..****...****.*****.*...*.*...*.*...*.*...*.*...*.*****.",
        "*...*.*...*.*...*.*...*.*.....*.....*.....*...*...*......*..*..*..*.....**.**.**..*.*...*.*...*.*...*.*...*.*.....*.*.*.*...*.*...*.*...*..*.*...*.*.....*..",
        "*****.****..*.....*...*.***...***...*..**.*****...*......*..***...*.....*.*.*.*.*.*.*...*.****..*...*.****...***....*...*...*..*.*..*.*.*...*.....*.....*...",
        "*...*.*...*.*.....*...*.*.....*.....*...*.*...*...*...*..*..*..*..*.....*...*.*..**.*...*.*.....*..**.*..*......*...*...*...*..*.*..**.**..*.*....*....*....",
        "*...*.****...****.****..*****.*......***..*...*.*****..**...*...*.*****.*...*.*...*..***..*......****.*...*.****...***...***....*...*...*.*...*...*...*****."
    };
}
