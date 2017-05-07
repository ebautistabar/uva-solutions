package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

// 11572 - C++ STL map (Java TreeMap), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2619
class UVA11572UniqueSnowflakes {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(System.out);

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        while (tests-- > 0) {
            int streamSize = Integer.parseInt(in.readLine().trim());
            int uniqueSequenceStart = 0;
            int maxSnowflakes = -1;
            Map<Integer, Integer> positions = new HashMap<>();
            int[] snowflakes = new int[streamSize];
            for (int i = 0; i < streamSize; i++) {
                int snowflake = Integer.parseInt(in.readLine().trim());
                // If the snow flake has been seen before
                Integer index = positions.get(snowflake);
                if (index != null) {
                    // Discard the snow flakes that were before the repeated
                    // snow flake
                    for (int j = uniqueSequenceStart; j < index; j++) {
                        positions.remove(snowflakes[j]);
                    }
                    // Update the length of the biggest sequence up to now
                    maxSnowflakes = Math.max(maxSnowflakes, i - uniqueSequenceStart);
                    // Start a new sequence right after the previous occurrence
                    // of this snow flake
                    uniqueSequenceStart = index + 1;
                }
                positions.put(snowflake, i);
                snowflakes[i] = snowflake;
            }
            maxSnowflakes = Math.max(maxSnowflakes, streamSize - uniqueSequenceStart);
            out.println(maxSnowflakes);
        }
        out.flush();
    }

}
