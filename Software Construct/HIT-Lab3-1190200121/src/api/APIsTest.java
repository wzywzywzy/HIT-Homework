package api;

import static org.junit.Assert.*;

import org.junit.Test;

import interval.IntervalSet;
import interval.MultiIntervalSet;

public class APIsTest {

    @Test
    public void SimilarityTest() throws Exception {
        APIs<String> api = new APIs<>();

        MultiIntervalSet<String> m1 = MultiIntervalSet.empty();

        m1.insert(0, 5, "A");
        m1.insert(20, 25, "A");
        m1.insert(10, 20, "B");
        m1.insert(25, 30, "B");

        MultiIntervalSet<String> m2 = MultiIntervalSet.empty();

        m2.insert(20, 35, "A");
        m2.insert(10, 20, "B");
        m2.insert(0, 5, "C");

//		System.out.println(api.Similarity(m1, m2));

        assertEquals(true, Math.abs(api.Similarity(m1, m2) - 0.42857) < 0.001);
    }

    @Test
    public void ConflictRatioTest() throws Exception {
        APIs<String> api = new APIs<>();

        IntervalSet<String> s1 = IntervalSet.empty();

        s1.insert(0, 10, "A");
        s1.insert(8, 15, "B");
        s1.insert(12, 20, "C");

//		System.out.println(api.calcConflictRatio(s1));

        assertEquals(true, Math.abs(api.calcConflictRatio(s1) - 0.25) < 0.001);

        MultiIntervalSet<String> m1 = MultiIntervalSet.empty();

        m1.insert(0, 5, "A");
        m1.insert(10, 25, "A");
        m1.insert(5, 20, "B");
        m1.insert(25, 30, "B");
        m1.insert(0, 2, "C");

//		System.out.println(api.calcConflictRatio(m1));

        assertEquals(true, Math.abs(api.calcConflictRatio(m1) - 0.4) < 0.001);
    }

    @Test
    public void FreeRatioTest() throws Exception {
        APIs<String> api = new APIs<>();

        IntervalSet<String> s1 = IntervalSet.empty();

        s1.insert(0, 10, "A");
        s1.insert(12, 15, "B");
        s1.insert(20, 25, "C");

//		System.out.println(api.calcFreeTimeRatio(s1));

        assertEquals(true, Math.abs(api.calcFreeTimeRatio(s1) - 0.28) < 0.001);

        MultiIntervalSet<String> m1 = MultiIntervalSet.empty();

        m1.insert(0, 5, "A");
        m1.insert(22, 25, "A");
        m1.insert(5, 10, "B");
        m1.insert(0, 2, "C");

//		System.out.println(api.calcFreeTimeRatio(m1));

        assertEquals(true, Math.abs(api.calcFreeTimeRatio(m1) - 0.48) < 0.001);
    }
}
