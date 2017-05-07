package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// 11286 - C++ STL map (Java TreeMap), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2261
class UVA11286Conformity {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(System.out);

    public static void main(String args[]) throws NumberFormatException, IOException {
        String line = in.readLine();
        while (line != null && !"0".equals(line.trim())) {
            int maxStudents = 0;
            int frosh = Integer.parseInt(line);
            Map<Courses, Integer> counts = new HashMap<>();
            for (int i = 0; i < frosh; i++) {
                Courses courses = new Courses(in.readLine());
                Integer count = counts.get(courses);
                if (count == null)
                    count = 0;
                count++;
                counts.put(courses, count);
                maxStudents = Math.max(maxStudents, count);
            }
            // There can be several combinations tied on popularity. Sum all
            // their students
            int students = 0;
            for (int count : counts.values())
                if (count == maxStudents)
                    students += count;
            out.println(students);
            line = in.readLine();
        }
        out.flush();
    }

    private static class Courses {
        private String[] courses;
        
        public Courses(String courses) {
            this.courses = courses.trim().split("\\s+");
            Arrays.sort(this.courses);
        }

        public int hashCode() {
            return Arrays.hashCode(courses);
        }
        
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof Courses))
                return false;
            Courses that = (Courses) o;
            return Arrays.equals(this.courses, that.courses);
        }
    }
}
