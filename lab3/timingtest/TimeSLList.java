package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        AList<Integer> Ns = new AList<Integer>();
        AList<Double> times = new AList<Double> ();
        AList<Integer> opCounts = new AList<Integer>();
        SLList<Integer> test = new SLList<Integer>();
        int startTime = 1000, maxTime = 128_000, opCnt = 10_000;
        for (int i = startTime; i <= maxTime; i *= 2) {
            for (int j = 0; j < i; j += 1) {
                test.addLast(0);
            }
            Stopwatch sw = new Stopwatch();
            for (int j = 0; j < opCnt; j += 1) {
                test.getLast();
            }
            double currTime = sw.elapsedTime();
            Ns.addLast(i);
            times.addLast(currTime);
            opCounts.addLast(opCnt);
        }
        printTimingTable(Ns, times, opCounts);
    }

}
