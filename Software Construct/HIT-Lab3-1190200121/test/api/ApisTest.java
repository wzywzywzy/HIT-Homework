package api;

import static org.junit.Assert.*;

import org.junit.Test;

import interval.IntervalSet;
import interval.MultiIntervalSet;

public class ApisTest {

    /*
     * Testing strategy:
     * Create two multi sets to contain the objects
     * Insert some object into the set, then test whether the result meets our
     * expectation
     */
    @Test
    public void SimilarityTest() throws Exception {
        APIs<String> api = new APIs<>();

        MultiIntervalSet<String> m1 = MultiIntervalSet.empty();

        m1.insert(0, 5, "yuan");
        m1.insert(20, 25, "yuan");
        m1.insert(10, 20, "wang");
        m1.insert(25, 30, "wang");

        MultiIntervalSet<String> m2 = MultiIntervalSet.empty();

        m2.insert(19, 34, "yuan");
        m2.insert(9, 19, "wang");
        m2.insert(0, 4, "guo");

        assertEquals(false, Math.abs(api.Similarity(m1, m2) - 0.43123) < 0.001);
    }
    /*
     * Testing strategy:
     * Create one set to contain the objects
     * And divide the test into interval and multiinterval
     * Insert some object into the set, then test whether the result meets our
     * expectation
     */
    @Test
    public void FreeRatioTest() throws Exception {
        APIs<String> api = new APIs<>();

        IntervalSet<String> s1 = IntervalSet.empty();

        s1.insert(0, 10, "yuan");
        s1.insert(12, 15, "wang");
        s1.insert(20, 25, "guo");

        assertEquals(false, Math.abs(api.calcFreeTimeRatio(s1) - 0.23) < 0.001);

        MultiIntervalSet<String> m1 = MultiIntervalSet.empty();

        m1.insert(0, 5, "yuan");
        m1.insert(22, 25, "yuan");
        m1.insert(5, 10, "wang");
        m1.insert(0, 2, "guo");

        assertEquals(true, Math.abs(api.calcFreeTimeRatio(m1) - 0.45) > 0.001);
    }

    /*
     * Testing strategy:
     * Create one set to contain the objects
     * And divide the test into interval and multiinterval
     * Insert some object into the set, then test whether the result meets our
     * expectation
     */
    @Test
    public void ConflictRatioTest() throws Exception {
        APIs<String> api = new APIs<>();

        IntervalSet<String> s1 = IntervalSet.empty();

        s1.insert(0, 9, "yuan");
        s1.insert(7, 14, "wang");
        s1.insert(11, 19, "guo");

        assertEquals(false, Math.abs(api.calcConflictRatio(s1) - 0.25) < 0.001);

        MultiIntervalSet<String> m1 = MultiIntervalSet.empty();

        m1.insert(0, 5, "yuan");
        m1.insert(10, 25, "yuan");
        m1.insert(5, 20, "wang");
        m1.insert(25, 30, "wang");
        m1.insert(0, 2, "guo");

        assertEquals(true, Math.abs(api.calcConflictRatio(m1) - 0.4) < 0.001);
    }
}