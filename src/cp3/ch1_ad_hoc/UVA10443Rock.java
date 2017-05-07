package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// 10443 - Game (Others), Harder, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1384
class UVA10443Rock {

    private static char[] foes = new char[128];

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        foes['R'] = 'P';
        foes['P'] = 'S';
        foes['S'] = 'R';
        int t = Integer.parseInt(in.readLine());
        while (t-- > 0) {
            String[] line = in.readLine().split("\\s+");
            int r = Integer.parseInt(line[0]);
            int c = Integer.parseInt(line[1]);
            int n = Integer.parseInt(line[2]);
            char[][] grid = buildGrid(in, r, c);
            grid = wageWar(grid, r, c, n);
            printGrid(out, grid);
            if (t != 0) {
                out.println();
            }
        }
        out.close();
    }

    private static char[][] wageWar(char[][] grid, int r, int c, int n) {
        char[][] aux = new char[r][c];
        while (n-- > 0) {
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    char foe = foes[grid[i][j]];
                    if (j > 0 && grid[i][j - 1] == foe) {
                        aux[i][j] = foe;
                    } else if (j < grid[0].length - 1 && grid[i][j + 1] == foe) {
                        aux[i][j] = foe;
                    } else if (i > 0 && grid[i - 1][j] == foe) {
                        aux[i][j] = foe;
                    } else if (i < grid.length - 1 && grid[i + 1][j] == foe) {
                        aux[i][j] = foe;
                    } else {
                        aux[i][j] = grid[i][j];
                    }
                }
            }
            char[][] tmp = grid;
            grid = aux;
            aux = tmp;
        }
        return grid;
    }

    private static void printGrid(PrintWriter out, char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                out.print(grid[i][j]);
            }
            out.println();
        }
    }

    private static char[][] buildGrid(BufferedReader in, int r, int c) throws IOException {
        char[][] grid = new char[r][c];
        for (int i = 0; i < r; i++) {
            String row = in.readLine();
            for (int j = 0; j < c; j++) {
                grid[i][j] = (char) row.charAt(j);
            }
        }
        return grid;
    }

}
