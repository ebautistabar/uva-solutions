package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

// 1203 - C++ STL priority_queue (Java PriorityQueue), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3644
class UVA01203Argus {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String args[]) throws NumberFormatException, IOException {
        Queue<Query> priorityQueue = new PriorityQueue<>();
        String line = in.readLine();
        while (line != null && !"#".equals(line.trim())) {
            StringTokenizer st = new StringTokenizer(line.trim());
            st.nextToken(); // ignore "Register"
            int id = Integer.parseInt(st.nextToken());
            int period = Integer.parseInt(st.nextToken());
            priorityQueue.add(new Query(id, period));
            line = in.readLine();
        }
        int queries = Integer.parseInt(in.readLine().trim());
        while (queries-- > 0) {
            Query query = priorityQueue.remove();
            query.execute();
            priorityQueue.add(query);
            out.println(query.getId());
        }
        out.flush();
    }

    private static class Query implements Comparable<Query>{
        private int id;
        private int period;
        private int nextExecutionTime;

        public Query(int id, int period) {
            this.id = id;
            this.period = period;
            this.nextExecutionTime = period;
        }

        public int getId() {
            return id;
        }

        public void execute() {
            nextExecutionTime += period;
        }

        @Override
        public int compareTo(Query o) {
            int cmp = Integer.compare(this.nextExecutionTime, o.nextExecutionTime);
            if (cmp == 0)
                return Integer.compare(this.id, o.id);
            else
                return cmp;
        }
    }
}
