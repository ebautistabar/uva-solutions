package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// 978 - C++ STL set (Java TreeSet), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=919
class UVA00978LemmingsBattle {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(System.out);

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        while (tests-- > 0) {
            String[] params = in.readLine().trim().split("\\s+");
            int battlefields = Integer.parseInt(params[0]);
            int greenSoldiers = Integer.parseInt(params[1]);
            int blueSoldiers = Integer.parseInt(params[2]);
            // CP3 suggests using C++ multisets. They don't exist in Java, so
            // let's use a map of power -> soldiers. Must be a tree map because
            // we need ordered traversals
            TreeMap<Integer, Integer> greenPowerToSoldiers = buildArmy(greenSoldiers);
            TreeMap<Integer, Integer> bluePowerToSoldiers = buildArmy(blueSoldiers);
            // Simulate battles
            while (greenPowerToSoldiers.size() > 0 && bluePowerToSoldiers.size() > 0) {
                List<Integer> greenWinners = new ArrayList<>();
                List<Integer> blueWinners = new ArrayList<>();
                int battles = battlefields;
                while (battles-- > 0 && greenPowerToSoldiers.size() > 0 && bluePowerToSoldiers.size() > 0) {
                    fightBattle(greenPowerToSoldiers, bluePowerToSoldiers, greenWinners, blueWinners);
                }
                returnWinnersToArmy(greenPowerToSoldiers, greenWinners);
                returnWinnersToArmy(bluePowerToSoldiers, blueWinners);
            }
            // Print results
            printResult(greenPowerToSoldiers, bluePowerToSoldiers);
            if (tests > 0)
                out.println();
        }
        out.flush();
    }

    private static TreeMap<Integer, Integer> buildArmy(int soldiers) throws NumberFormatException, IOException {
        TreeMap<Integer, Integer> army = new TreeMap<>();
        for (int i = 0; i < soldiers; i++) {
            int power = Integer.parseInt(in.readLine().trim());
            Integer count = army.get(power);
            if (count == null)
                count = 0;
            army.put(power, count + 1);
        }
        return army;
    }

    private static void fightBattle(
            TreeMap<Integer, Integer> greenPowerToSoldiers,
            TreeMap<Integer, Integer> bluePowerToSoldiers,
            List<Integer> greenWinners, List<Integer> blueWinners) {
        int greenSoldier = getSoldier(greenPowerToSoldiers);
        int blueSoldier = getSoldier(bluePowerToSoldiers);
        if (greenSoldier > blueSoldier)
            greenWinners.add(greenSoldier - blueSoldier);
        else if (blueSoldier > greenSoldier)
            blueWinners.add(blueSoldier - greenSoldier);
    }

    private static int getSoldier(TreeMap<Integer, Integer> powerToSoldiers) {
        Map.Entry<Integer, Integer> pair = powerToSoldiers.lastEntry();
        int power = pair.getKey();
        int soldiers = pair.getValue();
        if (soldiers == 1)
            powerToSoldiers.remove(power);
        else
            powerToSoldiers.put(power, soldiers - 1);
        return power;
    }

    private static void returnWinnersToArmy(
            TreeMap<Integer, Integer> powerToSoldiers, List<Integer> winners) {
        for (int winner : winners) {
            Integer soldiers = powerToSoldiers.get(winner);
            if (soldiers == null)
                soldiers = 0;
            powerToSoldiers.put(winner, soldiers + 1);
        }
    }

    private static void printResult(
            TreeMap<Integer, Integer> greenPowerToSoldiers,
            TreeMap<Integer, Integer> bluePowerToSoldiers) {
        if (greenPowerToSoldiers.size() == 0 && bluePowerToSoldiers.size() == 0)
            out.println("green and blue died");
        else if (bluePowerToSoldiers.size() == 0)
            printWinningArmy("green", greenPowerToSoldiers);
        else
            printWinningArmy("blue", bluePowerToSoldiers);
    }

    private static void printWinningArmy(String name, TreeMap<Integer, Integer> powerToSoldiers) {
        out.printf("%s wins\n", name);
        Map<Integer, Integer> descendingMap = powerToSoldiers.descendingMap();
        for (Map.Entry<Integer, Integer> pair : descendingMap.entrySet()) {
            int power = pair.getKey();
            int soldiers = pair.getValue();
            for (int i = 0; i < soldiers; i++) {
                out.println(power);
            }
        }
    }

}
