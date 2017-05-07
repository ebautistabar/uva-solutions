package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// 10038 - 1D Array Manipulation, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=979
class UVA10038JollyJumpers {

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String line = in.readLine();
        while (line != null) {
            String[] numbers = line.split("\\s+");
            if (isJolly(numbers)) {
                out.println("Jolly");
            } else {
                out.println("Not jolly");
            }
            line = in.readLine();
        }
        out.close();
    }

    private static boolean isJolly(String[] numbers) {
        int n = numbers.length - 1;
        if (n == 1) {
            return true;
        }
        boolean[] diff = new boolean[n];
        for (int i = 2; i < numbers.length; i++) {
            int currentDiff = Math.abs(Integer.parseInt(numbers[i]) - Integer.parseInt(numbers[i - 1]));
            if (currentDiff >= n || diff[currentDiff]) {
                return false;
            }
            diff[currentDiff] = true;
        }
        // we fail every time a diff is invalid (out of the array or duplicate)
        // so if we reach this point it means all the diffs are valid. We don't
        // need to check the array again
        return true;
    }

}
