package interval;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;

/**
 *  Testing strategy
 *  insert方法测试：
 *      test added interval and not added interval
 *  remove方法测试：
 *      test exist multiinterval and not exist multiinterval
 *  labels方法测试：
 *      test empty set and not empty set
 *  intervals方法测试：
 *      test exist multiinterval and not exist multiinterval
 */
public abstract class MultiIntervalSetTest {

    public abstract MultiIntervalSet<String> emptyInstance();

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test
    public void testInitialLabelsEmpty() {
        assertEquals("expected new interval set to have no intervals", Collections.emptySet(), emptyInstance().labels());
    }

    @Test(expected = Exception.class)
    public void insertExceptionTest() {
        MultiIntervalSet<String> multiIntervals = emptyInstance();

        assertEquals(false,multiIntervals.insert(-8, 10, "yuan"));
    }

    @Test
    public void insertTest() {
        MultiIntervalSet<String> multiIntervals = emptyInstance();

        assertEquals("{}", multiIntervals.toString());
        assertEquals(Collections.emptySet(), multiIntervals.labels());

        multiIntervals.insert(0, 10, "yuan");

        multiIntervals.insert(15, 20, "yuan");

        multiIntervals.insert(20, 30, "wang");
        assertEquals("{wang=[[20,30]],yuan=[[0,10],[15,20]]}", multiIntervals.toString());
        assertEquals(Set.of("yuan", "wang"), multiIntervals.labels());
    }

    @Test
    public void removeTest() throws Exception {
        MultiIntervalSet<String> multiIntervals = emptyInstance();

        assertEquals(false, multiIntervals.remove("yuan"));
        assertEquals(Collections.emptySet(), multiIntervals.labels());

        multiIntervals.insert(0, 10, "yuan");
        multiIntervals.insert(15, 20, "yuan");
        multiIntervals.insert(20, 30, "wang");

        assertEquals("{yuan=[[0,10],[15,20]],wang=[[20,30]]}", multiIntervals.toString());
        assertEquals(Set.of("yuan", "wang"), multiIntervals.labels());

        assertEquals(true, multiIntervals.remove("yuan"));
        assertEquals("{wang=[[20,30]]}", multiIntervals.toString());
        assertEquals(Set.of("wang"), multiIntervals.labels());
    }

    @Test
    public void labelsTest() throws Exception {
        MultiIntervalSet<String> multiIntervals = emptyInstance();

        multiIntervals.insert(0, 7, "yuan");
        assertEquals(Set.of("yuan"), multiIntervals.labels());

        multiIntervals.insert(5, 10, "yuan");
        multiIntervals.insert(20, 30, "wang");
        assertEquals(Set.of("yuan","wang"), multiIntervals.labels());
    }

    @Test
    public void intervalsTest() throws Exception {
        MultiIntervalSet<String> multiIntervals = emptyInstance();

        multiIntervals.insert(0, 10, "yuan");
        multiIntervals.insert(5, 20, "yuan");
        multiIntervals.insert(7, 8, "wang");

        assertEquals("{0=[0,10],1=[5,20]}", multiIntervals.intervals("yuan").toString());
        assertEquals("{0=[7,8]}", multiIntervals.intervals("wang").toString());
    }

}

