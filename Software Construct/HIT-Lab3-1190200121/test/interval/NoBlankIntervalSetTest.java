package interval;

import static org.junit.Assert.*;

import org.junit.Test;

public class NoBlankIntervalSetTest {

    @Test
    public void EmptyIntervalSetTest() throws Exception {
        NoBlankIntervalSet<String> dayi = new NoBlankIntervalSetImpl<>();

        assertEquals("{}", dayi.toString());

        dayi.checkNoBlank();
    }

    @Test
    public void NoBlankTest1() throws Exception {
        NoBlankIntervalSet<String> dayi1 = new NoBlankIntervalSetImpl<>();
        dayi1.insert(0, 8, "yuan");
        dayi1.insert(11, 16, "yuan");

        IntervalSet<String> interval = IntervalSet.empty();
        interval.insert(0, 8, "yuan");
        interval.insert(11, 16, "wang");
        NoBlankIntervalSet<String> dayi = new NoBlankIntervalSetImpl<>(interval);

        dayi.checkNoBlank();

    }

    @Test
    public void NoBlankTest2() throws Exception {
        NoBlankIntervalSet<String> dayi = new NoBlankIntervalSetImpl<>();
        dayi.insert(5, 10, "tang");

        dayi.checkNoBlank();
        dayi.remove("tang");
    }

}
