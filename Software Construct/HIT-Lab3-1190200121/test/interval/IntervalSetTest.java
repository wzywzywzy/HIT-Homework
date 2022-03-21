package interval;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;
/**
 *  Testing strategy
 *  insert方法测试：
 *      test added interval and not added interval
 *  remove方法测试：
 *      test added interval and not added interval
 *  labels方法测试：
 *      test empty set and not empty set
 *  getStart方法测试：
 *      test added interval and not added interval
 *  getEnd方法测试：
 *      test added interval and not added interval
 */

public abstract class IntervalSetTest {

    public abstract IntervalSet<String> emptyInstance();

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test
    public void testInitialLabelsEmpty() {
        assertEquals("expected new interval set to have no intervals",
                Collections.emptySet(), emptyInstance().labels());
    }

}
