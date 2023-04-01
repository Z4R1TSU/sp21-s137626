package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> test1 = new AListNoResizing<>();
        BuggyAList<Integer> test2 = new BuggyAList<>();

        test1.addLast(4);
        test1.addLast(5);
        test1.addLast(6);
        test2.addLast(4);
        test2.addLast(5);
        test2.addLast(6);

        assertEquals(test1.size(), test2.size());

        assertEquals(test1.removeLast(), test2.removeLast());
        assertEquals(test1.removeLast(), test2.removeLast());
        assertEquals(test1.removeLast(), test2.removeLast());
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> test1 = new AListNoResizing<>();
        BuggyAList<Integer> test2 = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                test1.addLast(randVal);
                test2.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size1 = test1.size(), size2 = test2.size();
                assertEquals(size1, size2);
            }
            else if (test1.size() == 0) {
                continue;
            }
            else if (operationNumber == 2) {
                assertEquals(test1.getLast(), test2.getLast());
            }
            else if (operationNumber == 3) {
                assertEquals(test1.removeLast(), test2.removeLast());
            }
        }
    }
}
