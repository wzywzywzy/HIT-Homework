package interval;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;

public class CommonMultiIntervalSetTest extends MultiIntervalSetTest {

    @Override
    public MultiIntervalSet<String> emptyInstance() {
        return new CommonMultiIntervalSet<>();
    }

    @Test
    public void constructorTest() throws Exception {
        IntervalSet<String> intervals = IntervalSet.empty();

        intervals.insert(4, 17, "yuan");
        intervals.insert(17, 20, "wang");

        MultiIntervalSet<String> multiIntervals = new CommonMultiIntervalSet<>(intervals);

        assertEquals(Set.of("yuan","wang"), multiIntervals.labels());
        assertEquals("{0=[4,17]}", multiIntervals.intervals("yuan").toString());
    }


    @Test
    public void labelsTest() throws Exception {
        MultiIntervalSet<String> multiIntervals = emptyInstance();


        multiIntervals.insert(5, 10, "yuan");
        assertEquals(Set.of("yuan"), multiIntervals.labels());

        multiIntervals.insert(2, 3, "wang");
        assertEquals(Set.of("yuan","wang"), multiIntervals.labels());
    }

    @Test
    public void intervalsTest() throws Exception {
        MultiIntervalSet<String> multiIntervals = emptyInstance();

        assertEquals("{}", multiIntervals.intervals("yuan").toString());

        multiIntervals.insert(0, 8, "yuan");
        multiIntervals.insert(15, 16, "yuan");
        multiIntervals.insert(20, 30, "wang");

        assertEquals("{0=[0,8],1=[15,16]}", multiIntervals.intervals("yuan").toString());
    }

    @Test
    public void insertExceptionTest(){
        MultiIntervalSet<String> multiIntervals = emptyInstance();

        assertEquals(false,multiIntervals.insert(-9, 10, "yuan"));
    }

    @Test
    public void insertTest(){
        MultiIntervalSet<String> multiIntervals = emptyInstance();

        multiIntervals.insert(0, 10, "yuan");
        assertEquals("{yuan=[[0,10]]}", multiIntervals.toString());
        assertEquals(Set.of("yuan"), multiIntervals.labels());


        multiIntervals.insert(20, 30, "wang");
        assertEquals(Set.of("yuan", "wang"), multiIntervals.labels());
    }

    @Test
    public void removeTest() throws Exception {
        MultiIntervalSet<String> multiIntervals = emptyInstance();

        assertEquals("{}", multiIntervals.toString());
        assertEquals(Collections.emptySet(), multiIntervals.labels());

        assertEquals(false, multiIntervals.remove("yuan"));
        assertEquals("{}", multiIntervals.toString());
        assertEquals(Collections.emptySet(), multiIntervals.labels());

        multiIntervals.insert(20, 30, "wang");
        multiIntervals.insert(0, 10, "yuan");
        multiIntervals.insert(15, 20, "yuan");


        assertEquals(Set.of("yuan", "wang"), multiIntervals.labels());

        assertEquals(true, multiIntervals.remove("yuan"));
        assertEquals("{wang=[[20,30]]}", multiIntervals.toString());
        assertEquals(Set.of("wang"), multiIntervals.labels());

        assertEquals(true, multiIntervals.remove("wang"));
        assertEquals("{}", multiIntervals.toString());
        assertEquals(Collections.emptySet(), multiIntervals.labels());
    }

    @Test
    public void multiIntervalTest() throws Exception {
        MultiInterval<String> multiInterval = new MultiInterval<>("yuan");

        assertEquals(Collections.emptyList(), multiInterval.getStart());
        assertEquals(Collections.emptyList(), multiInterval.getEnd());
        assertEquals("yuan", multiInterval.getLabel());
        assertEquals("yuan=[]", multiInterval.toString());

        multiInterval.add(0, 9);
        assertEquals("[0]", multiInterval.getStart().toString());
        assertEquals("[9]", multiInterval.getEnd().toString());
        assertEquals("yuan", multiInterval.getLabel());
        assertEquals("yuan=[[0,9]]", multiInterval.toString());
    }
}
