package interval;

import static org.junit.Assert.*;

import org.junit.Test;

public class NoOverlapIntervalSetTest {

    @Test
    public void EmptyIntervalSetTest() throws Exception {
        NonOverlapIntervalSet<String> nois = new NonOverlapIntervalSetImpl<>();

        assertEquals("{}", nois.toString());

        nois.checkNoOverlap("ENABLE");
    }


    @Test
    public void NoOverlapTest() throws Exception {
        MultiIntervalSet<String> multiInterval = MultiIntervalSet.empty();
        multiInterval.insert(0, 8, "yuan");
        multiInterval.insert(11, 13, "wang");
        multiInterval.insert(12, 17, "yuan");

        NonOverlapIntervalSet<String> nois = new NonOverlapIntervalSetImpl<>(multiInterval);

        assertEquals(true,nois.checkNoOverlap("ENABLE"));
        assertEquals(false,nois.checkNoOverlap("DISABLE"));
    }
}
