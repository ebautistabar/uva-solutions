package cp3.ch3_problem_solving_paradigms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

// 416 - Complete Search, Recursive Backtracking (Harder), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=357
class UVA00416LEDTest {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int[] numbers = buildNumbers();
    private static int[] leds;

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null && !"0".equals(line.trim())) {
            int n = Integer.parseInt(line.trim());
            leds = readLEDs(n);
            boolean success = false;
            int workingLights = 0b1111111;
            for (int i = 9; i >= n - 1; i--) {
                if (isCountdown(i + 1, 0, workingLights)) {
                    success = true;
                    break;
                }
            }
            if (!success) {
                out.print("MIS");
            }
            out.println("MATCH");
            line = in.readLine();
        }
        out.close();
    }

    private static int[] readLEDs(int n) throws IOException {
        int[] lights = new int[n];
        for (int i = 0; i < n; i++) {
            String line = in.readLine().trim();
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == 'Y') {
                    lights[i] |= 1 << j;
                }
            }
        }
        return lights;
    }

    private static boolean isCountdown(int prev, int position, int workingLights) {
        if (position == leds.length) {
            return true;
        }
        int current = prev - 1;
        if (canRepresent(position, current, workingLights)) {
            workingLights &= getBrokenLights(position, current);
            return isCountdown(current, position + 1, workingLights); 
        }
        return false;
    }

    private static boolean canRepresent(int position, int num, int workingLights) {
        int led = leds[position] & workingLights;
        // 1. we assumed some segments were broken, but they have just lit up,
        // so our assumption was wrong: they actually were switched off, and
        // this branch of the state space is not valid
        if ((leds[position] & workingLights) != leds[position]) {
            return false;
        }
        // 2. check that the led doesn't have any segment that shouldn't be lit
        return (numbers[num] | led) == numbers[num];
    }

    private static int getBrokenLights(int position, int current) {
        return ~(leds[position] ^ numbers[current]);
    }

    private static int[] buildNumbers() {
        return new int[]{
                0b0111111,
                0b0000110,
                0b1011011,
                0b1001111,
                0b1100110,
                0b1101101,
                0b1111101,
                0b0000111,
                0b1111111,
                0b1101111
        };
    }
}
