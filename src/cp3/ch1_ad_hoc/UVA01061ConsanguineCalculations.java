package cp3.ch1_ad_hoc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

// 1061 - Interesting Real Life Problems, Harder, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3502
class UVA01061ConsanguineCalculations {

    private static Map<String, Character[]> aboToAllele = new HashMap<>();
    private static Map<Character, String[]> alleleToAbo = new HashMap<>();
    private static String[] order = {"O-", "O+", "AB-", "AB+", "B-", "B+", "A-", "A+"};

    static {
        // Possible alleles for each ABO blood type
        aboToAllele.put("A", new Character[]{'A', 'O'});
        aboToAllele.put("B", new Character[]{'B', 'O'});
        aboToAllele.put("AB", new Character[]{'A', 'B'});
        aboToAllele.put("O", new Character[]{'O'});
        // Possible ABO blood types for each allele
        alleleToAbo.put('A', new String[]{"A", "AB"});
        alleleToAbo.put('B', new String[]{"B", "AB"});
        alleleToAbo.put('O', new String[]{"A", "B", "O"});
    }

    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String line = in.readLine();
        int i = 1;
        while (line != null && line.indexOf('E') == -1) {
            String[] types = line.trim().split("\\s+");
            if (types.length == 3) {
                if (types[0].equals("?")) {
                    types[0] = getParentType(types[1], types[2]);
                } else if (types[1].equals("?")) {
                    types[1] = getParentType(types[0], types[2]);
                } else if (types[2].equals("?")) {
                    types[2] = getChildType(types[0], types[1]);
                }
                printResult(out, i++, types);
            }
            line = in.readLine();
        }
        out.close();
    }

    private static String getParentType(String parent, String child) {
        Set<String> types = new TreeSet<>();
        Character[] unknown = new Character[]{'A', 'B', 'O'};
        Character[] aboParent = aboToAllele.get(parent.substring(0, parent.length() - 1));
        char rhParent = parent.charAt(parent.length() - 1);
        char rhChild = child.charAt(child.length() - 1);
        for (int i = 0; i < unknown.length; i++) {
            for (int j = 0; j < aboParent.length; j++) {
                String abo = null;
                char u = unknown[i];
                char p = aboParent[j];
                char first = u < p ? u : p;
                char second = u < p ? p : u;
                if (first == 'A' && second == 'B') {
                    abo = "AB";
                } else {
                    abo = String.valueOf(first);
                }
                if (abo.equals(child.substring(0, child.length() - 1))) {
                    String[] aboTypesForAllele = alleleToAbo.get(u);
                    for (int k = 0; k < aboTypesForAllele.length; k++) {
                        types.add(aboTypesForAllele[k] + "+");
                        if (rhChild == '-' || rhParent == '+') {
                            types.add(aboTypesForAllele[k] + "-");
                        }
                    }
                }
            }
        }
        return toString(types);
    }

    private static String getChildType(String mother, String father) {
        Set<String> types = new TreeSet<String>();
        Character[] aboMother = aboToAllele.get(mother.substring(0, mother.length() - 1));
        Character[] aboFather = aboToAllele.get(father.substring(0, father.length() - 1));
        char rhMother = mother.charAt(mother.length() - 1);
        char rhFather = father.charAt(father.length() - 1);
        for (int i = 0; i < aboMother.length; i++) {
            for (int j = 0; j < aboFather.length; j++) {
                StringBuilder type = new StringBuilder();
                char m = aboMother[i];
                char f = aboFather[j];
                char first = m < f ? m : f;
                char second = m < f ? f : m;
                if (first == 'A' && second == 'B') {
                    type.append("AB");
                } else {
                    type.append(first);
                }
                type.append('-');
                types.add(type.toString());
                if (rhMother == '+' || rhFather == '+') {
                    type.deleteCharAt(type.length() - 1);
                    type.append('+');
                    types.add(type.toString());
                }
            }
        }
        return toString(types);
    }

    private static String toString(Set<String> types) {
        if (types.size() < 2) {
            for (String type : types) {
                return type;
            }
            return "IMPOSSIBLE";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append('{');
            for (String type : order) {
                if (types.contains(type)) {
                    sb.append(type);
                    sb.append(", ");
                }
            }
            sb.setLength(sb.length() - 2);
            sb.append('}');
            return sb.toString();
        }
    }

    private static void printResult(PrintWriter out, int testcase,
            String[] types) {
        out.print("Case ");
        out.print(testcase);
        out.print(": ");
        out.print(types[0]);
        out.print(" ");
        out.print(types[1]);
        out.print(" ");
        out.println(types[2]);
    }
}
