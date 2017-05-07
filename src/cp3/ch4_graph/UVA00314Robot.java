package cp3.ch4_graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

// 314 - Single-Source Shortest Paths (SSSP) On Unweighted Graph: BFS, Harder, starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=250
// BFS, notes below
class UVA00314Robot {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    // north, east, south, west
    private static final int[] DR = {-1, 0, 1, 0};
    private static final int[] DC = {0, 1, 0, -1};
    // turn left, right
    private static final int[] TURN = {-1, 1};

    public static void main(String args[]) throws IOException {
        String line = getLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            int rows = Integer.parseInt(st.nextToken());
            int cols = Integer.parseInt(st.nextToken());
            if (rows == 0 && cols == 0) break;
            boolean[][] blocked = new boolean[rows][cols];
            for (int i = 0; i < rows; i++) {
                st = new StringTokenizer(getLine());
                for (int j = 0; j < cols; j++) {
                    blocked[i][j] = "1".equals(st.nextToken());
                }
            }
            st = new StringTokenizer(getLine());
            int startR = Integer.parseInt(st.nextToken());
            int startC = Integer.parseInt(st.nextToken());
            int goalR = Integer.parseInt(st.nextToken());
            int goalC = Integer.parseInt(st.nextToken());
            int direction = getDirection(st.nextToken());

            Map<Position, Position> parent = new HashMap<>();
            Position start = new Position(startR, startC, direction);
            Position goal = new Position(goalR, goalC, direction);
            Map<Position, Integer> distance = new HashMap<>();
            Queue<Position> q = new ArrayDeque<>();
            int distanceToGoal = -1;
            // When start or goal are not valid, don't insert in the queue
            // That way BFS won't start and result is -1
            if (start.isValid(blocked) && goal.isValid(blocked)) {
                if (start.equalCoordinates(goal)) {
                    // If we are already in the goal, set the distance != -1
                    // That way BFS won't start
                    distanceToGoal = 0;
                } else {
                    // Regular case
                    distance.put(start, 0);
                    q.add(start);
                }
            }
            while (!q.isEmpty() && distanceToGoal == -1) {
                Position current = q.remove();
                // commands GO 1-3
                for (int i = 1; i <= 3; i++) {
                    Position next = current.go(i);
                    if (!next.isValid(blocked)) {
                        // the robot can't jump. Thus as soon as we find an
                        //   invalid movement, don't bother to check the rest
                        break;
                    }
                    if (next.equalCoordinates(goal)) {
                        // don't care about direction in the goal
                        distanceToGoal = distance.get(current) + 1;
                        parent.put(goal, current);
                        break;
                    }
                    if (distance.get(next) == null) {
                        distance.put(next, distance.get(current) + 1);
                        q.add(next);
                        parent.put(next, current);
                    }
                }
                // command TURN left, right
                for (int i = 0; i < TURN.length; i++) {
                    Position next = current.turn(TURN[i]);
                    if (distance.get(next) == null) {
                        distance.put(next, distance.get(current) + 1);
                        q.add(next);
                        parent.put(next, current);
                    }
                }
            }

            out.println(distanceToGoal);
            /*out.println("...................");
            for (Position p = goal; p != null; p = parent.get(p)) {
                out.println(p);
            }*/

            line = getLine();
        }
        out.close();
    }

    private static int getDirection(String orientation) {
        switch(orientation) {
        case "north":
            return 0;
        case "east":
            return 1;
        case "south":
            return 2;
        case "west":
            return 3;
        default:
            return -1;
        }
    }

    private static String getLine() throws IOException {
        String line = in.readLine();
        while ("".equals(line)) { // dealing with blank lines
            line = in.readLine();
        }
        return line;
    }


    private static class Position {
        int r;
        int c;
        int d;

        public Position(int r, int c, int d) {
            this.r = r;
            this.c = c;
            this.d = d;
        }

        public Position turn(int direction) {
            int nd = (d + direction) % DR.length;
            if (nd < 0) nd += DR.length;
            return new Position(r, c, nd);
        }

        public Position go(int steps) {
            return new Position(r + DR[d] * steps, c + DC[d] * steps, d);
        }

        public boolean isValid(boolean[][] blocked) {
            // The robot is a square which occupies 4 cells. We track the bottom
            //   right cell. Thus we must stop 1 row and column earlier to stay
            //   in bounds.
            if (r < 1 || r >= blocked.length || c < 1
                    || c >= blocked[r].length) {
                return false;
            }
            return !blocked[r][c] && !blocked[r][c - 1] && !blocked[r - 1][c]
                    && !blocked[r - 1][c - 1];
        }

        public boolean equalCoordinates(Position p) {
            return r == p.r && c == p.c;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof Position)) return false;
            Position p = (Position) o;
            return r == p.r && c == p.c && d == p.d;
        }

        @Override
        public int hashCode() {
            int hash = 17;
            hash = 31 * hash + r;
            hash = 31 * hash + c;
            hash = 31 * hash + d;
            return hash;
        }

        @Override
        public String toString() {
            return String.format("[%d,%d,%d]", r, c, d);
        }
    }
}
/*
BFS
each state is row,col,direction

the robot goes between squares, occupies 4 squares.
let's track the southeast cell that it occupies.
then, the max col is col-2 instead of col-1
and the max row is row-2 instead of row-1
when checking if it touches an obstacle, we must check the
  4 squares occupied by the robot

gotchas:
- the statement mentions the northwest, but if you read carefully it says
that it's the cell at the northwest of which the robot is located, i.e. the robot
is at the northwest of the cell, in other words, the cell is at the southeast
of the robot
- the robot can't jump, so when transitioning to the different states, make sure
to check the validity of the transition, instead of the validity of the resulting
state

*/
