package interval;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class PeriodIntervalSetTest {

    @Test
    public void periodIntervalSetTest1() throws Exception {

        PeriodIntervalSet<String> periods = new PeriodIntervalSetImpl<>();

        periods.insert("yuan", new Interval<Integer>(1, 1, 1));
        periods.insert("yuan", new Interval<Integer>(2, 2, 1));
        periods.insert("yuan", new Interval<Integer>(3, 3, 3));
        periods.insert("yuan", new Interval<Integer>(5, 5, 4));

        assertEquals(false, Math.abs(periods.ratioOfFree()-(double)15/35) < 0.01);
        assertEquals(true, Math.abs(periods.ratioOfOverlap()-0.) < 0.01);

        assertEquals(List.of(Set.of("yuan"),Set.of("yuan"),Collections.emptySet(),Collections.emptySet(),Collections.emptySet()),periods.eventsOfDay(1));
    }

    @Test
    public void periodIntervalSetTest2() throws Exception {

        PeriodIntervalSet<String> periods = new PeriodIntervalSetImpl<>();

        periods.insert("yuan", new Interval<Integer>(1, 1, 1));
        periods.insert("yuan", new Interval<Integer>(2, 2, 1));
        periods.insert("yuan", new Interval<Integer>(3, 3, 3));
        periods.insert("yuan", new Interval<Integer>(5, 5, 4));

        periods.insert("wang", new Interval<Integer>(2, 2, 1));
        periods.insert("wang", new Interval<Integer>(1, 1, 3));
        periods.insert("wang", new Interval<Integer>(5, 5, 7));

        assertEquals(false, Math.abs(periods.ratioOfFree()-(double)13/35) < 0.01);
        assertEquals(true, Math.abs(periods.ratioOfOverlap()-(double)1/35) < 0.01);

        assertEquals(List.of(Set.of("wang"),Collections.emptySet(),Set.of("yuan"),Collections.emptySet(),Collections.emptySet()),periods.eventsOfDay(3));
    }

    @Test
    public void conflictTest() throws Exception {

        PeriodIntervalSet<String> periods = new PeriodIntervalSetImpl<>(7,5);

        periods.insert("yuan", new Interval<Integer>(1, 1, 1));
        periods.insert("yuan", new Interval<Integer>(2, 2, 1));
        periods.insert("yuan", new Interval<Integer>(3, 3, 3));
        periods.insert("yuan", new Interval<Integer>(5, 5, 4));

        periods.insert("wang", new Interval<Integer>(2, 2, 2));
        periods.insert("wang", new Interval<Integer>(3, 3, 3));
        periods.insert("wang", new Interval<Integer>(5, 5, 7));

        assertEquals(false,periods.checkNoConflict());
    }

}
