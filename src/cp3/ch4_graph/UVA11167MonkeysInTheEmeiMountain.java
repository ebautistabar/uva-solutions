package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

// 11167 - Standard Max Flow Problem (Edmond Karp's), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2108
// Network flow, notes below
class UVA11167MonkeysInTheEmeiMountain {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    private static final int INF = (int) 1e9;
    private static final int MAX_TIME = 50_001;
    private static final int SOURCE = 0;
    private static final int SINK = 1;
    private static final int MONKEY_OFFSET = 2;

    public static void main(String args[]) throws IOException {
        int test = 1;
        String line = getLine();
        while (line != null && !"0".equals(line.trim())) {
            StringTokenizer st = new StringTokenizer(line);
            int monkeys = parseInt(st.nextToken());
            int simultaneousSipsPerTimeUnit = parseInt(st.nextToken());
            int totalSips = 0;
            int[][] monkeyHabits = new int[monkeys][3];
            boolean[] timeBoundary = new boolean[MAX_TIME];
            int intervalStart = Integer.MAX_VALUE; // track the earliest interval time
            for (int i = 0; i < monkeys; i++) {
                st = new StringTokenizer(getLine());
                int sips = parseInt(st.nextToken());
                int start = parseInt(st.nextToken());
                int end = parseInt(st.nextToken());
                monkeyHabits[i][0] = sips;
                monkeyHabits[i][1] = start;
                monkeyHabits[i][2] = end;
                timeBoundary[start] = true;
                timeBoundary[end] = true;
                totalSips += sips;
                intervalStart = Math.min(intervalStart, start);
            }

            // Split intervals, as they may overlap
            Map<Integer, Integer> timeToIntervalId = new HashMap<>();
            List<Interval> intervals = new ArrayList<>();
            for (int time = intervalStart + 1; time < MAX_TIME; time++) {
                // each time we find a boundary, we close the current
                //  interval and open a new one
                if (timeBoundary[time]) {
                    // map intervals to node ids; as there cannot be
                    //  overlapping intervals, we identify the interval
                    //  just by its start time
                    timeToIntervalId.put(intervalStart, intervals.size());
                    // store interval for later use
                    intervals.add(new Interval(intervalStart, time));
                    // start new interval
                    intervalStart = time;
                }
            }

            // Create the residual graph
            int nodes = 2 + monkeys + intervals.size();
            int timeOffset = MONKEY_OFFSET + monkeys;
            int[][] residual = new int[nodes][nodes];
            for (int i = 0; i < intervals.size(); i++) {
                // how many monkeys can drink at the same time during this interval?
                Interval interval = intervals.get(i);
                residual[timeOffset + i][SINK] = simultaneousSipsPerTimeUnit * interval.sips;
            }
            for (int monkey = 0; monkey < monkeys; monkey++) {
                int sips = monkeyHabits[monkey][0];
                int start = monkeyHabits[monkey][1];
                int end = monkeyHabits[monkey][2];
                // how many sips does this monkey need overall?
                residual[SOURCE][MONKEY_OFFSET + monkey] = sips;
                // how many sips could the monkey have during each time interval?
                for (int j = start; j < end; j++) {
                    // each boundary marks the start of a new interval
                    if (timeBoundary[j]) {
                        int intervalId = timeToIntervalId.get(j);
                        Interval interval = intervals.get(intervalId);
                        residual[MONKEY_OFFSET + monkey][timeOffset + intervalId] = interval.sips;
                    }
                }
            }

            // Max flow through Edmond-Karp's
            int maxFlow = 0;
            int[] previous = new int[residual.length];
            while (hasAugmentingPath(SOURCE, SINK, residual, previous)) {
                int flow = INF;
                // find smallest residual capacity along the path
                for (int v = SINK; v != SOURCE; v = previous[v]) {
                    flow = Math.min(flow, residual[previous[v]][v]);
                }
                // increase flow along the path
                for (int v = SINK; v != SOURCE; v = previous[v]) {
                    residual[previous[v]][v] -= flow;
                    residual[v][previous[v]] += flow;
                }
                maxFlow += flow;
            }

            if (maxFlow == totalSips) {
                int[] sipsTaken = new int[intervals.size()];
                out.printf("Case %d: Yes%n", test);
                for (int monkey = 0; monkey < monkeys; monkey++) {
                    int pendingSips = monkeyHabits[monkey][0];
                    int start = monkeyHabits[monkey][1];
                    int end = monkeyHabits[monkey][2];
                    int previousIntervalFrom = -1; // -1 represents that we haven't started an interval yet
                    int previousIntervalTo = -1;
                    int sippingIntervals = 0;
                    StringBuilder sb = new StringBuilder();
                    for (int time = start; time < end; time++) {
                        // each boundary marks the start of a new interval
                        if (timeBoundary[time]) {
                            int intervalId = timeToIntervalId.get(time);
                            // positive flow means the monkey took a sip during this interval
                            int sipsDuringInterval = residual[timeOffset + intervalId][MONKEY_OFFSET + monkey];
                            if (sipsDuringInterval > 0) {
                                Interval interval = intervals.get(intervalId);
                                // we split the intervals to get the max flow, but when
                                //  printing we must merge adjacent intervals, so let's
                                //  keep track of the previous one to see if it can be
                                //  extended
                                // (1) See below
                                int sipsLeft = sipsTaken[intervalId] + sipsDuringInterval - interval.sips;
                                if (sipsLeft > 0) {
                                    if (previousIntervalFrom == -1) {
                                        previousIntervalFrom = interval.from;
                                    } else if (interval.from != previousIntervalTo) {
                                        // the previous interval cannot be extended, so print it
                                        sb.append(new Interval(previousIntervalFrom, previousIntervalTo));
                                        sb.append(' ');
                                        sippingIntervals++;
                                        previousIntervalFrom = interval.from;
                                    }
                                    previousIntervalTo = interval.from + sipsLeft;
                                }
                                // (2) See below
                                if (previousIntervalFrom == -1) {
                                    previousIntervalFrom = interval.from + sipsTaken[intervalId];
                                } else if (interval.from + sipsTaken[intervalId] != previousIntervalTo) {
                                    // the previous interval cannot be extended, so print it
                                    sb.append(new Interval(previousIntervalFrom, previousIntervalTo));
                                    sb.append(' ');
                                    sippingIntervals++;
                                    previousIntervalFrom = interval.from + sipsTaken[intervalId];
                                }
                                // maybe the monkey hasn't been drinking the whole interval;
                                //   this min() takes care of that scenario
                                previousIntervalTo = Math.min(interval.to, interval.from + sipsTaken[intervalId] + sipsDuringInterval);
                                // update sips taken by all monkeys during this interval
                                if (sipsLeft > 0) {
                                    sipsTaken[intervalId] = sipsLeft;
                                } else {
                                    sipsTaken[intervalId] = (sipsTaken[intervalId] + sipsDuringInterval) % interval.sips;
                                }
                                pendingSips -= sipsDuringInterval;
                                if (pendingSips == 0) {
                                    // this monkey took enough sips; close last interval
                                    sb.append(new Interval(previousIntervalFrom, previousIntervalTo));
                                    sippingIntervals++;
                                    // go to next monkey
                                    break;
                                }
                            }
                        }
                    }
                    out.printf("%d %s%n", sippingIntervals, sb);
                }
            } else {
                out.printf("Case %d: No%n", test);
            }
            test++;
            line = getLine();
        }
        out.close();
    }

    private static boolean hasAugmentingPath(int source, int sink, int[][] residual, int[] previous) {
        Arrays.fill(previous, -1);
        Queue<Integer> q = new ArrayDeque<>();
        q.add(source);
        while (!q.isEmpty()) {
            int current = q.remove();
            for (int i = 0; i < residual.length; i++) {
                if (residual[current][i] > 0 && previous[i] == -1) {
                    previous[i] = current;
                    if (i == sink) return true;
                    q.add(i);
                }
            }
        }
        return false;
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while (line != null && "".equals(line.trim())) // dealing with blank lines
            line = in.readLine();
        return line;
    }
    private static int parseInt(String text) {
        return Integer.parseInt(text.trim());
    }


    private static class Interval {
        int from;
        int to;
        int sips;
        public Interval(int from, int to) {
            this.from = from;
            this.to= to;
            // e.g. [4, 7) allows for 3 sips at times 4, 5 and 6
            this.sips = to - from;
        }
        public String toString() {
            return String.format("(%d,%d)", from, to);
        }
    }
}
/*
Bipartite matching problem with monkeys and time units. The capacities are:
- the amount of drinks they need
- the limit of monkeys that can drink at the same time
Success if maxflow == total number of drinks

Graph:
From source to monkey, drinks needed by monkey
From time to sink, limit of monkeys at the same time
From monkey to time i (with i >= a && i < b), 1

Valid range for times is [0, 50_000]. If, among all the monkeys, we need to consider the
whole range then we will have more than 50_000 nodes in the graph.
50_000 * 50_000 = 2.5 * 1e9 = 2.5 billion nodes ~ 2^30 nodes * 4B = 2^32B = 4GB
which is a bit too much.

We can use an adj. list instead, but it still could be too much and it will definitely
be too slow.

Twist:
Instead of having 1 node per time unit, 1 node will represent an interval. Intervals
can overlap. Before we build the graph we must split the intervals whenever there's
an overlap. Example: [3, 6) and [5, 8) will become [3, 5), [5, 6) and [6, 8). The first
monkey node will be connected to the first two intervals, and the second monkey will
be connected to the last two intervals.

The trickiest part is generating the output. We must print the exact intervals when
each monkey drank, but we cannot print them as we used them in the graph. That's
just an internal representation. Instead we must merge them. But we also need to
consider that in a given time unit there's a limit of monkeys that can be drinking.
The maxflow restricts the flow that goes through each interval, but it doesn't
restrict the flow that goes through each individual time unit. We must track
how many monkeys drink on each time unit when printing, in order to generate
valid output.

Two ways to think about it: one tied to the topic of the problem, and the other
graphical.
- each monkey knows the drinking schedule of the previous monkeys that drank in a
particular interval; monkeys prefer to drink as soon as possible inside their
interval but they prefer to drink with as few other monkeys as possible, i.e.
they favor the earliest time that has the smallest number of other monkeys (2).
If by doing so they cannot drink all the flow alloted to that interval (1), then
they will drink the rest of the flow at the earliest time of the interval
that has 1 more monkey. The earliest time that holds that property is guaranteed
to be the first time unit of the interval.
- in a graphical way, we can see the interval as a wall of length equal to its
number of time units (or sips). Each time unit is a brick and we must build the
wall by setting rows of bricks from left to right (2). If a monkey drinks 3 sips,
we set 3 bricks. The next monkey drinks 2 sips, and we put 2 more bricks following
the previous bricks. If we complete the row of bricks and there are more bricks left (1),
we continue putting bricks on top starting at the left.

Reference:
https://abitofcs.blogspot.com.es/2014/12/uva-11167-monkeys-in-emei-mountain.html
*/
