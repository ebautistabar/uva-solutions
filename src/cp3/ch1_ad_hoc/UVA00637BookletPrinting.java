package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// 637 - Interesting Real Life Problems, Easier, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=578
class UVA00637BookletPrinting {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int n = Integer.parseInt(in.readLine());
        while (n != 0) {
            boolean remainingPages = n % 4 > 0;
            int sheets = 4 * (n / 4 + (remainingPages ? 1 : 0));
            int[] pages = new int[sheets];
            int page = 1;
            // First half
            for (int i = 1; i < sheets; i += 4) {
                pages[i] = page++;
                if (page <= n) {
                    pages[i + 1] = page++;
                }
            }
            // Second half
            for (int i = sheets - 1; i > 0; i -= 4) {
                if (page <= n) {
                    pages[i] = page++;
                }
                if (page <= n) {
                    pages[i - 3] = page++;
                }
            }
            out.println("Printing order for " + n + " pages:");
            for (int i = 0; i < sheets; i += 4) {
                // If they are different then at least one is not 0, so we print
                if (pages[i] != pages[i + 1]) {
                    out.print("Sheet ");
                    out.print(i / 4 + 1);
                    out.print(", front: ");
                    out.print(pages[i] == 0 ? "Blank" : pages[i]);
                    out.print(", ");
                    out.println(pages[i + 1] == 0 ? "Blank" : pages[i + 1]);
                }
                if (pages[i + 2] != pages[i + 3]) {
                    out.print("Sheet ");
                    out.print(i / 4 + 1);
                    out.print(", back : ");
                    out.print(pages[i + 2] == 0 ? "Blank" : pages[i + 2]);
                    out.print(", ");
                    out.println(pages[i + 3] == 0 ? "Blank" : pages[i + 3]);
                }
            }
            n = Integer.parseInt(in.readLine());
            out.flush();
        }
        out.close();
    }

}
