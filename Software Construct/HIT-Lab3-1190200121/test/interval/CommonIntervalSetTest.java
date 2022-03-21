package interval;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;
/*
 * testing strategy:
 * test whether the format of the string conforms to our expectation
 */
public class CommonIntervalSetTest extends IntervalSetTest {
    @Override public IntervalSet<String> emptyInstance() {
        return new CommonIntervalSet<>();
    }

    @Test
    public void toStringTest(){
        IntervalSet<String> intervals = emptyInstance();

        assertEquals("{}", intervals.toString());

        intervals.insert(0, 10, "yuan");
        intervals.insert(14, 15, "wang");
        intervals.insert(8, 10, "guo");
        assertEquals("{yuan=[0,10],guo=[8,10],wang=[14,15]}", intervals.toString());
    }

    @Test
    public void labelsTest(){
        IntervalSet<String> intervals = emptyInstance();

        assertEquals(Collections.emptySet(), intervals.labels());

        intervals.insert(0, 10, "yuan");
        assertEquals(Set.of("yuan"), intervals.labels());
    }

    @Test
    public void insertExceptionTest(){
        IntervalSet<String> intervals = emptyInstance();

        assertEquals(false,intervals.insert(-8, 6, "yuan"));
    }

    @Test
    public void insertTest(){
        IntervalSet<String> intervals = emptyInstance();

        assertEquals("{}", intervals.toString());
        assertEquals(Collections.emptySet(), intervals.labels());

        assertEquals(true, intervals.insert(0, 1, "yuan"));
        assertEquals("{yuan=[0,1]}", intervals.toString());
        assertEquals(Set.of("yuan"), intervals.labels());

        assertEquals(false, intervals.insert(1, 2, "yuan"));

        assertEquals(true, intervals.insert(2, 3, "wang"));
        assertEquals(true, "{yuan=[0,1],wang=[2,3]}".equals(intervals.toString()) || "{wang=[2,3],yuan=[0,1]}".equals(intervals.toString()));
    }

    @Test
    public void getStartTest() throws Exception {
        IntervalSet<String> intervals = emptyInstance();

        assertEquals("{}", intervals.toString());
        assertEquals(Collections.emptySet(), intervals.labels());

        intervals.insert(0, 1, "yuan");
        intervals.insert(2, 3, "wang");

        assertEquals(true, "{yuan=[0,1],wang=[2,3]}".equals(intervals.toString()) || "{wang=[2,3],yuan=[0,1]}".equals(intervals.toString()));
        assertEquals(Set.of("yuan", "wang"), intervals.labels());
    }

    @Test(expected = Exception.class)
    public void getStartExceptionTest() throws Exception {
        IntervalSet<String> intervals = emptyInstance();

        assertEquals("{}", intervals.toString());
        assertEquals(Collections.emptySet(), intervals.labels());

        intervals.getStart("yuan");
    }

    @Test
    public void removeTest() {
        IntervalSet<String> intervals = emptyInstance();

        assertEquals("{}", intervals.toString());
        assertEquals(Collections.emptySet(), intervals.labels());

        assertEquals(false, intervals.remove("yuan"));
        assertEquals("{}", intervals.toString());
        assertEquals(Collections.emptySet(), intervals.labels());

        intervals.insert(0, 1, "yuan");
        intervals.insert(2, 3, "wang");

        assertEquals(true, intervals.remove("yuan"));
        assertEquals("{wang=[2,3]}", intervals.toString());
        assertEquals(Set.of("wang"), intervals.labels());

        assertEquals(false, intervals.remove("yuan"));
        assertEquals("{wang=[2,3]}", intervals.toString());
        assertEquals(Set.of("wang"), intervals.labels());

        assertEquals(true, intervals.remove("wang"));
        assertEquals("{}", intervals.toString());
        assertEquals(Collections.emptySet(), intervals.labels());
    }

    @Test
    public void getEndTest() {
        IntervalSet<String> intervals = emptyInstance();

        assertEquals("{}", intervals.toString());
        assertEquals(Collections.emptySet(), intervals.labels());

        intervals.insert(0, 1, "yuan");
        intervals.insert(2, 3, "wang");

        assertEquals(true, "{yuan=[0,1],wang=[2,3]}".equals(intervals.toString()) || "{wang=[2,3],yuan=[0,1]}".equals(intervals.toString()));
        assertEquals(Set.of("yuan", "wang"), intervals.labels());
    }

    @Test(expected = Exception.class)
    public void getEndExceptionTest() throws Exception {
        IntervalSet<String> intervals = emptyInstance();

        assertEquals("{}", intervals.toString());
        assertEquals(Collections.emptySet(), intervals.labels());

        intervals.getEnd("yuan");
    }

    @Test
    public void intervalTest() throws Exception {
        Interval<String> interval = new Interval<>(0, 1, "yuan");

        assertEquals(0, interval.getStart());
        assertEquals(1, interval.getEnd());
        assertEquals("yuan", interval.getLabel());

        assertEquals("yuan=[0,1]", interval.toString());
    }

}
