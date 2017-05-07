package cp3.ch2_data_structures;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

// 10901 - C++ STL queue and deque (Java Queue and Deque), starred
// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1842
class UVA10901FerryLoadingIII {

    private static final int LEFT = 0;
    private static final int RIGHT = 1;

    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static PrintWriter out = new PrintWriter(System.out);

    public static void main(String args[]) throws NumberFormatException, IOException {
        int tests = Integer.parseInt(in.readLine().trim());
        while (tests-- > 0) {
            String[] params = in.readLine().trim().split("\\s+");
            int capacity = Integer.parseInt(params[0]);
            int crossingTime = Integer.parseInt(params[1]);
            int cars = Integer.parseInt(params[2]);
            int currentTime = 0;
            int currentBank = LEFT;
            List<Queue<Car>> queues = buildQueues(cars);
            int[] register = new int[cars];
            while (isCarWaiting(queues)) {
                if (isCarReadyInBank(queues, currentBank, currentTime)) {
                    moveCars(queues.get(currentBank), currentTime, crossingTime, capacity, register);
                    currentTime += crossingTime;
                    currentBank = getOppositeBank(currentBank);
                } else if (isCarReadyInBank(queues, getOppositeBank(currentBank), currentTime)) {
                    currentTime += crossingTime;
                    moveCars(queues.get(getOppositeBank(currentBank)), currentTime, crossingTime, capacity, register);
                    currentTime += crossingTime;
                } else {
                    currentTime = waitForCar(queues);
                }
            }
            printResults(register);
            if (tests != 0) 
                out.println();
        }
        out.flush();
    }

    private static void moveCars(Queue<Car> queue, int currentTime, int crossingTime, int capacity, int[] register) {
        int arrivalTime = currentTime + crossingTime;
        while (capacity > 0 && !queue.isEmpty() && queue.peek().arrivalTime <= currentTime) {
            Car car = queue.remove();
            register[car.arrivalOrder] = arrivalTime;
            capacity--;
        }
    }

    private static int getOppositeBank(int bank) {
        return bank ^ 1;
    }

    private static int waitForCar(List<Queue<Car>> queues) {
        if (queues.get(LEFT).isEmpty()) 
            return queues.get(RIGHT).peek().arrivalTime;
        else if (queues.get(RIGHT).isEmpty())
            return queues.get(LEFT).peek().arrivalTime;
        else
            return Math.min(queues.get(LEFT).peek().arrivalTime, queues.get(RIGHT).peek().arrivalTime);
    }

    private static boolean isCarReadyInBank(List<Queue<Car>> queues,
            int bank, int time) {
        return !queues.get(bank).isEmpty() && queues.get(bank).peek().arrivalTime <= time;
    }

    private static List<Queue<Car>> buildQueues(int cars) throws IOException {
        List<Queue<Car>> queues = new ArrayList<Queue<Car>>(2);
        queues.add(new ArrayDeque<Car>());
        queues.add(new ArrayDeque<Car>());
        String[] params;
        for (int i = 0; i < cars; i++) {
            params = in.readLine().trim().split("\\s+");
            int arrivalTime = Integer.parseInt(params[0]);
            Car car = new Car(i, arrivalTime);
            int bank = getBank(params[1]);
            queues.get(bank).add(car);
        }
        return queues;
    }

    private static int getBank(String name) {
        return "left".equals(name) ? LEFT : RIGHT;
    }

    private static boolean isCarWaiting(List<Queue<Car>> queues) {
        return queues.get(LEFT).size() > 0 || queues.get(RIGHT).size() > 0;
    }

    private static void printResults(int[] register) {
        for (int i = 0; i < register.length; i++) {
            out.println(register[i]);
        }
    }

    private static class Car {
        public int arrivalOrder;
        public int arrivalTime;
        
        public Car(int arrivalOrder, int arrivalTime) {
            this.arrivalOrder = arrivalOrder;
            this.arrivalTime = arrivalTime;
        }
    }
}
